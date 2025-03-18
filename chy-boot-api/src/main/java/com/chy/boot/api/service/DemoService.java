package com.chy.boot.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chy.boot.api.dto.request.CreateDemoDTO;
import com.chy.boot.api.dto.request.UpdateDemoDTO;
import com.chy.boot.api.vo.DemoVO;

import java.util.List;

/**
 * Demo服务接口
 * 定义所有Demo相关的业务操作
 *
 * @author YuRuizhi
 * @since 2024/03/18
 */
public interface DemoService {

    /**
     * 创建Demo
     *
     * @param createDTO 创建参数
     * @return 创建后的Demo视图对象
     */
    DemoVO create(CreateDemoDTO createDTO);
    
    /**
     * 更新Demo
     *
     * @param updateDTO 更新参数
     * @return 更新后的Demo视图对象
     */
    DemoVO update(UpdateDemoDTO updateDTO);
    
    /**
     * 根据ID获取Demo
     *
     * @param id Demo ID
     * @return Demo视图对象
     */
    DemoVO getById(String id);
    
    /**
     * 删除Demo
     *
     * @param id Demo ID
     * @return 是否成功
     */
    boolean removeById(String id);
    
    /**
     * 分页查询Demo
     *
     * @param page 页码
     * @param size 每页大小
     * @param name 名称过滤（可选）
     * @param code 代码过滤（可选）
     * @param category 分类过滤（可选）
     * @return 分页数据
     */
    IPage<DemoVO> page(int page, int size, String name, String code, String category);
    
    /**
     * 检查代码是否存在
     *
     * @param code 代码
     * @return 是否存在
     */
    boolean existsByCode(String code);
    
    /**
     * 更新状态
     *
     * @param id Demo ID
     * @param status 状态
     * @return 是否成功
     */
    boolean updateStatus(String id, Integer status);
    
    /**
     * 根据分类获取列表
     *
     * @param category 分类
     * @return Demo列表
     */
    List<DemoVO> listByCategory(String category);
} 