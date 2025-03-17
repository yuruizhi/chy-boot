package com.chy.boot.service.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chy.boot.rest.core.entity.Demo;
import com.chy.boot.rest.dto.DemoQueryParam;

import java.util.List;

/**
 * 示例表(Demo)表服务接口
 *
 * @author YuRuizhi
 * @since 2021-01-14 14:34:26
 * @update 2023/03/01 升级到MyBatis-Plus
 * @update 2024/06/14 优化接口设计，增加缓存和业务方法
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
    
    /**
     * 根据ID获取实体，优先从缓存获取
     * 
     * @param id 实体ID
     * @return 实体对象
     */
    Demo getByIdWithCache(String id);
    
    /**
     * 保存实体，并清除相关缓存
     * 
     * @param demo 实体对象
     * @return 是否成功
     */
    boolean saveWithCache(Demo demo);
    
    /**
     * 更新实体，并清除相关缓存
     * 
     * @param demo 实体对象
     * @return 是否成功
     */
    boolean updateByIdWithCache(Demo demo);
    
    /**
     * 删除实体，并清除相关缓存
     * 
     * @param id 实体ID
     * @return 是否成功
     */
    boolean removeByIdWithCache(String id);
    
    /**
     * 检查标识码是否已存在
     * 
     * @param code 标识码
     * @return 是否存在
     */
    boolean existsByCode(String code);
    
    /**
     * 通过查询参数进行分页查询
     * 
     * @param page 分页参数
     * @param param 查询参数
     * @return 分页结果
     */
    IPage<Demo> getPage(Page<Demo> page, DemoQueryParam param);
    
    /**
     * 更新状态
     * 
     * @param id 实体ID
     * @param status 新状态
     * @return 是否成功
     */
    boolean updateStatus(String id, Integer status);
    
    /**
     * 批量保存或更新，处理缓存
     * 
     * @param demoList 实体列表
     * @return 是否成功
     */
    boolean saveBatchWithCache(List<Demo> demoList);
    
    /**
     * 缓存预热
     * 
     * @param limit 预热数量
     */
    void preloadCache(int limit);
} 