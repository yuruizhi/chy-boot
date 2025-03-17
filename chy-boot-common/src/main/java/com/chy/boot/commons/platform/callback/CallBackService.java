package com.chy.boot.commons.platform.callback;

import com.chy.boot.commons.component.cache.GlobalSettings;
import com.chy.boot.commons.constant.CommonConstant;
import com.chy.boot.commons.jackson.JsonUtil;
import com.chy.boot.commons.util.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 回调业务
 *
 *@author YuRuizhi
 *@date 2020/2/11
 */
@Slf4j
@Component
public class CallBackService {

    protected String logPrefix = "回调业务";

    private String error = "callbackBusiness_error";

    /**
     * 回调业务系统
     * @param callbackUrl	 回调地址
     * @param callbackParams	 回调参数
     */
    @Async("callBackTaskExecutor")
    public void callbackBusiness(String callbackUrl, Map<String, Object> callbackParams){
        // 最大重试次数
        int maxRetryTimes = Integer.parseInt(GlobalSettings.Keys.MAX_RETRY_TIMES.get().getSetValue());

        // 重试间隔ms
        int retrySleepMs = Integer.parseInt(GlobalSettings.Keys.RETRY_SLEEP_MILLISECOND.get().getSetValue());

        // 回调通知,并且处理回调通知结果(重试次数+回调状态)
        int retryCount = callbackHandle(callbackUrl, callbackParams, maxRetryTimes, retrySleepMs);

        // 重试次数, 如果次数大于retryCount表示回调失败
        if (retryCount < maxRetryTimes) {
            log.info(CommonConstant.CallbackStatus.CALLBACKSUCCESS.getDesc());
        }else {
            log.warn(CommonConstant.CallbackStatus.CALLBACKFAILD.getDesc());
        }
    }


    /**
     * 回调处理,包含重试机制
     *
     * @param callbackUrl    回调地址
     * @param callbackParams 回调参数
     * @param maxRetryTimes 重试次数
     * @param retrySleepMs  重试间隔
     * @return 重试次数, 如果次数大于retryCount标示回调失败
     */
    private int callbackHandle(String callbackUrl, Map<String, Object> callbackParams, final int maxRetryTimes, int retrySleepMs){

        int retryTimes = 0;

        do {

            retryTimes++;

            try {

                log.info("回调url:{}, 回调业务参数:{}" , callbackUrl, JsonUtil.object2EscapeJson(callbackParams));

                String result = OkHttpUtil.postJson(callbackUrl, JsonUtil.object2Json(callbackParams));


                log.info("{}, 回调业务系统url:{}, 第{}次, result: {}", logPrefix,callbackUrl, retryTimes, JsonUtil.jsonStr2EscapeStr(result));
                BusinessError businessError = JsonUtil.parse(result, BusinessError.class);
                // 成功返回重试次数
                if(CommonConstant.CallbackBusinessResult.SUCCESS.getDesc().equalsIgnoreCase(businessError.status)){
                    log.info("{}, 回调业务系统成功, 第{}次", logPrefix, retryTimes);
                    return  retryTimes;
                }

                log.warn("{}, 回调业务系统失败(第{}次), 等待{}ms后重试", logPrefix, retryTimes, retrySleepMs);
                if(retryTimes >= maxRetryTimes){
                    log.error(error);
                }
            }catch (Exception e){

                log.warn("{}, 回调业务系统异常(第{}次), 等待{}ms后重试", logPrefix, retryTimes, retrySleepMs, e);
                if(retryTimes >= maxRetryTimes){
                    log.error(error);
                }
            }

            try {
                Thread.sleep(retrySleepMs);
            } catch (InterruptedException ie) {
                log.warn("{}, 回调处理线程异常", logPrefix, ie);
            }

        }while (retryTimes < maxRetryTimes);

        // 返回失败标记
        return maxRetryTimes+1;
    }
}
