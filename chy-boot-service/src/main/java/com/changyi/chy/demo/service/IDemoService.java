package com.changyi.chy.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.changyi.chy.demo.entity.Demo;
import com.changyi.chy.demo.request.ReqDemo;
import com.changyi.chy.demo.response.RepDemo;

/**
 * <p>
 * 会员基础信息表 服务类
 * </p>
 *
 * @author wuwh
 * @since 2020-01-15
 */
public interface IDemoService extends IService<Demo> {

    /**
     * 获取会员详细信息
     *
     * @param
     * @return
     */
    RepDemo get(ReqDemo req);
}
