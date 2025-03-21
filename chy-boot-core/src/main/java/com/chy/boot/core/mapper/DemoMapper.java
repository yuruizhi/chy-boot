package com.chy.boot.core.mapper;

import com.chy.boot.core.entity.Demo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述: Demo 数据访问层接口
 * @author Henry.Yu
 * @date 2023/09/05
 */
@Mapper
public interface DemoMapper {

    /**
     * 新增
     * @param demo 实体对象
     * @return 影响行数
     */
    int insert(Demo demo);

    /**
     * 根据ID删除
     * @param id 主键ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 更新
     * @param demo 实体对象
     * @return 影响行数
     */
    int update(Demo demo);

    /**
     * 根据ID查询
     * @param id 主键ID
     * @return Demo实体
     */
    Demo selectById(@Param("id") Long id);

    /**
     * 查询所有记录
     * @return 实体列表
     */
    List<Demo> selectAll();

    /**
     * 根据名称模糊查询
     *
     * @param name 名称关键词
     * @return 实体列表
     */
    List<Demo> selectByNameLike(@Param("name") String name);

    /**
     * 根据状态查询
     *
     * @param status 状态值
     * @return 实体列表
     */
    List<Demo> selectByStatus(@Param("status") Integer status);
}