package com.changyi.chy.demo.service.impl;

import com.changyi.chy.commons.component.cache.GlobalSettings;
import com.changyi.chy.commons.platform.callback.CallBackService;
import com.changyi.chy.demo.entity.Demo;
import com.changyi.chy.demo.mapper.DemoMapper;
import com.changyi.chy.demo.request.ReqDemo;
import com.changyi.chy.demo.service.IDemoService;
import com.changyi.chy.demo.response.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * demo
 *
 * @author Henry.Yu
 * @since 2020.5.8
 */
@Service
@Slf4j
public class DemoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements IDemoService {


    @Autowired
    private DemoMapper mapper;

    @Autowired
    private CallBackService callBackService;


    //同步业务
    private void CallBack(Map<String, Object> param){
        try {
            String url = GlobalSettings.Keys.MEMBER_CALL_BACK_URL.get().getSetValue();
            Map<String, Object> callBackParam = new HashMap<>();
            callBackParam.put("source","javaSource");
            callBackParam.put("data", param);
            callBackService.callbackBusiness(url, callBackParam);
        }catch (Exception e){
            log.error("会员数据同步业务异常e:{}", e);
        }

    }




    @Override
    public RepDemo get(ReqDemo params){

        return new RepDemo();
    }


}
