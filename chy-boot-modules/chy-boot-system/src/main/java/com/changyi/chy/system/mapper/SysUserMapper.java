package com.changyi.chy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.changyi.chy.system.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户表(SysUser)数据库访问层
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SysUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 通过用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    SysUser findByUsername(@Param("username") String username);
} 