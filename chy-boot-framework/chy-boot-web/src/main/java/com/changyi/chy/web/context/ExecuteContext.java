package com.changyi.chy.web.context;


import com.changyi.chy.web.getter.IContextInfoGetter;
import com.changyi.chy.common.support.DateUtil;
import com.changyi.chy.common.support.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 执行上下文环境
 *
 * @author three
 * @date 2016/5/19
 */
public class ExecuteContext {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 系统 rest api 前缀
     */
    public static final String SYSTEM_API_PREFIX = "s";
    /**
     * 人员基础 rest api 前缀
     */
    public static final String MEMBER_BASE_API_PREFIX = "b";

    /**
     * 频道标识
     */
    private String channel;

    /**
     * 频道标识-类型
     */
    private String channelType;

    /**
     * 品牌标识
     */
    private String brand;


    /**
     * 请求id
     */
    private String requestId;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 上下文获取器
     */
    private IContextInfoGetter contextInfoGetter;

    /**
     * 请求id,支持多线程
     */
    private static final Map<String, String> threadRequestId = new HashMap<>();
    private static final ThreadLocal<ExecuteContext> LOCAL = new ThreadLocal<ExecuteContext>() {
        @Override
        protected ExecuteContext initialValue() {
            return new ExecuteContext();
        }
    };

    /**
     * 设置请求id
     *
     * @param requestId
     */
    private void setRequestId(String requestId) {

        this.requestId = requestId;
        threadRequestId.put(Thread.currentThread().getName(), this.requestId);
    }

    /**
     * 获取请求id
     *
     * @return
     */
    public String getRequestId() {

        return requestId;
    }

    /**
     * get context.
     *
     * @return context
     */
    public static ExecuteContext getContext() {

        return LOCAL.get();
    }

    /**
     * get context.
     *
     * @return context
     */
    public static ExecuteContext getContext(String requestId) {

        LOCAL.get().setRequestId(requestId);
        return LOCAL.get();
    }

    /**
     * remove context.
     */
    public static void removeContext() {

        // threadRequestId.remove(Thread.currentThread().getName());
        LOCAL.remove();
    }

    public ExecuteContext setContextInfoGetter(IContextInfoGetter contextInfoGetter) {

        this.contextInfoGetter = contextInfoGetter;
        return this;
    }

    /**
     * 设置渠道标识
     *
     * @param channel
     */
    public ExecuteContext setChannel(String channel) {

        this.channel = channel;
        return this;
    }

    /**
     * 获取频道标识
     *
     * @return
     */
    public String getChannel() {

        return channel;
    }


    /**
     * 获取频道标识 类型
     *
     * @return
     */
    public String getChannelType() {

        // 如果上下文为空, 未初始化, 则
        if (null == this.contextInfoGetter) {
            throw new ContextNotInitException(ContextCodeDefined.CONTEXT_INIT_ERROR);
        }
//
//        if (this.channelType == null) {
//            try {
//                this.channelType = contextInfoGetter.getChannelType(ExecuteContext.getContext().getChannel());
//            } catch (Exception e) {
//                throw new ContextNotInitException(e);
//            }
//            if (this.channelType == null) {
//                throw new ContextNotInitException(ContextCodeDefined.NOT_FIND_EN);
//            }
//        }

        return channelType;
    }

    /**
     * 设置品牌标识
     *
     * @param brand
     */
    public ExecuteContext setBrand(String brand) {

        this.brand = brand;
        return this;
    }

    /**
     * 获取品牌标识
     *
     * @return
     */
    public String getBrand() {

        return brand;
    }


    /**
     * 获取请求ID
     *
     * @param threadName 线程名称
     * @return
     */
    public static String getRequestId(String threadName) {

        return threadRequestId.get(threadName);
    }

    /**
     * 防止实例化
     */
    private ExecuteContext() {
        setRequestId(UUIDGenerator.generate());
        setStartTime(DateUtil.getCurrentTimeMills());
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getStartTime() {
        return startTime;
    }


}
