package com.changyi.chy.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.changyi.chy.demo.entity.Demo;
import com.changyi.chy.demo.request.ReqDemo;
import com.changyi.chy.demo.response.RespDemo;

/**
 * Demo
 *
 * @author Henry.Yu
 * @since 2020.1.15
 */
public interface IDemoService extends IService<Demo> {

    RespDemo get(ReqDemo req);
}
