package com.changyi.chy.demo.service.impl;

import com.changyi.chy.demo.entity.Demo;
import com.changyi.chy.demo.mapper.DemoMapper;
import com.changyi.chy.demo.request.ReqDemo;
import com.changyi.chy.demo.service.IDemoService;
import com.changyi.chy.demo.response.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * demo
 *
 * @author Henry.Yu
 * @since 2020.5.8
 */
@Service
@Slf4j
public class DemoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements IDemoService {




    @Override
    public RespDemo get(ReqDemo params){
        RespDemo demo = new RespDemo();
        demo.setId("11");
        return demo;
    }


}
