package com.changyi.chy.business.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.changyi.chy.business.demo.entity.Demo;
import com.changyi.chy.common.api.R;
import com.changyi.chy.persistence.result.PageResult;
import com.changyi.chy.persistence.request.PageRequest;

import java.util.List;

/**
 * 示例服务接口
 *
 * @author YuRuizhi
 */
public interface DemoService extends IService<Demo> {

    /**
     * 分页查询
     *
     * @param request 分页参数
     * @return 分页结果
     */
    PageResult<Demo> page(PageRequest request);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Demo> queryAllByLimit(int offset, int limit);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return 示例对象
     */
    Demo getById(String id);

    /**
     * 新增数据
     *
     * @param demo 示例对象
     * @return 操作结果
     */
    R<Demo> insert(Demo demo);

    /**
     * 修改数据
     *
     * @param demo 示例对象
     * @return 操作结果
     */
    R<Boolean> update(Demo demo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 操作结果
     */
    R<Boolean> deleteById(String id);
} 