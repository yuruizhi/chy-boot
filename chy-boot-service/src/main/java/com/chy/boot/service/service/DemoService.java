package com.chy.boot.service.service;

import com.chy.boot.common.api.R;
import com.chy.boot.core.entity.Demo;
import com.chy.boot.service.request.ReqDemo;
import com.chy.boot.service.response.RespDemo;

import java.util.List;

/**
 * Demo业务服务接口
 *
 * @author Henry.Yu
 * @date 2023/09/05
 */
public interface DemoService {

    /**
     * 创建Demo
     *
     * @param demo demo对象
     * @return 创建结果
     */
    R<Demo> createDemo(Demo demo);

    /**
     * 更新Demo
     *
     * @param demo demo对象
     * @return 更新结果
     */
    R<Demo> updateDemo(Demo demo);

    /**
     * 根据ID查询Demo
     *
     * @param id 主键ID
     * @return Demo对象
     */
    R<Demo> getDemoById(Long id);

    /**
     * 根据ID删除Demo
     *
     * @param id 主键ID
     * @return 删除结果
     */
    R<Boolean> deleteDemo(Long id);

    /**
     * 查询Demo列表
     *
     * @param name 名称（可选）
     * @param page 页码
     * @param size 每页数量
     * @return Demo列表
     */
    R<List<Demo>> listDemos(String name, Integer page, Integer size);

    /**
     * 查询所有Demo
     *
     * @return Demo列表
     */
    R<List<Demo>> listAllDemos();

    /**
     * 根据状态查询Demo
     *
     * @param status 状态
     * @return Demo列表
     */
    R<List<Demo>> listDemosByStatus(Integer status);

    /**
     * 处理示例请求
     *
     * @param reqDemo 请求参数
     * @return 处理结果
     */
    R<RespDemo> processRequest(ReqDemo reqDemo);

    /**
     * 获取一条示例数据
     *
     * @return 示例数据
     */
    RespDemo getOneHitokoto();
}