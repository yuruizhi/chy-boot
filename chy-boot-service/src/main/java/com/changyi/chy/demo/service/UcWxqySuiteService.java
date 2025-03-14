package com.changyi.chy.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.changyi.chy.demo.entity.UcWxqySuite;

import java.util.List;

/**
 * 套件主表(UcWxqySuite)表服务接口
 *
 * @author ZhangHao
 * @since 2021-01-14 14:34:26
 * @update 2023/03/01 升级到MyBatis-Plus
 */
public interface UcWxqySuiteService extends IService<UcWxqySuite> {

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<UcWxqySuite> queryAllByLimit(int offset, int limit);

}