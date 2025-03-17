package com.chy.boot.service.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chy.boot.rest.core.entity.Demo;

import java.util.List;

/**
 * 示例表(Demo)表服务接口
 *
 * @author YuRuizhi
 * @since 2021-01-14 14:34:26
 * @update 2023/03/01 升级到MyBatis-Plus
 */
public interface DemoService extends IService<Demo> {

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Demo> queryAllByLimit(int offset, int limit);

} 