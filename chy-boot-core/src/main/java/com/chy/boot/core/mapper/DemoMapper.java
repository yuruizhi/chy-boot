package com.chy.boot.core.mapper;

import com.chy.boot.core.entity.Demo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 套件主表(Demo)表数据库访问层
 *
 * @author ZhangHao
 * @since 2021-01-14 14:34:24
 */
public interface DemoMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param qysSuiteid 主键
     * @return 实例对象
     */
    Demo queryById(String qysSuiteid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Demo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param demo 实例对象
     * @return 对象列表
     */
    List<Demo> queryAll(Demo demo);

    /**
     * 新增数据
     *
     * @param demo 实例对象
     * @return 影响行数
     */
    int insert(Demo demo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Demo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Demo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Demo> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<Demo> entities);

    /**
     * 修改数据
     *
     * @param demo 实例对象
     * @return 影响行数
     */
    int update(Demo demo);

    /**
     * 通过主键删除数据
     *
     * @param qysSuiteid 主键
     * @return 影响行数
     */
    int deleteById(String qysSuiteid);

}