package com.changyi.chy.business.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.changyi.chy.business.demo.entity.Demo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 示例(Demo)数据库访问层
 *
 * @author YuRuizhi
 */
public interface DemoMapper extends BaseMapper<Demo> {

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Demo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);
} 