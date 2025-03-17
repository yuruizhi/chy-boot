package com.chy.boot.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chy.boot.demo.entity.UcWxqySuite;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 套件主表(UcWxqySuite)表数据库访问层
 *
 * @author YuRuizhi
 * @since 2021-01-14 14:34:24
 * @update 2023/03/01 升级到MyBatis-Plus
 */
public interface UcWxqySuiteDao extends BaseMapper<UcWxqySuite> {

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<UcWxqySuite> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param ucWxqySuite 实例对象
     * @return 对象列表
     */
    List<UcWxqySuite> queryAll(UcWxqySuite ucWxqySuite);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<UcWxqySuite> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<UcWxqySuite> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<UcWxqySuite> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<UcWxqySuite> entities);

}