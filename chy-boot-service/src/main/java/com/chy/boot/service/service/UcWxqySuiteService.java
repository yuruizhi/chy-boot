package com.chy.boot.service.service;

import com.chy.boot.core.entity.UcWxqySuite;

import java.util.List;

/**
 * 套件主表(UcWxqySuite)表服务接口
 *
 * @author ZhangHao
 * @since 2021-01-14 14:34:26
 */
public interface UcWxqySuiteService {

    /**
     * 通过ID查询单条数据
     *
     * @param qysSuiteid 主键
     * @return 实例对象
     */
    UcWxqySuite queryById(String qysSuiteid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<UcWxqySuite> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param ucWxqySuite 实例对象
     * @return 实例对象
     */
    UcWxqySuite insert(UcWxqySuite ucWxqySuite);

    /**
     * 修改数据
     *
     * @param ucWxqySuite 实例对象
     * @return 实例对象
     */
    UcWxqySuite update(UcWxqySuite ucWxqySuite);

    /**
     * 通过主键删除数据
     *
     * @param qysSuiteid 主键
     * @return 是否成功
     */
    boolean deleteById(String qysSuiteid);

}