package com.changyi.chy.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.changyi.chy.demo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表(User)数据库访问层
 *
 * @author YuRuizhi
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<User> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 通过用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    User findByUsername(@Param("username") String username);
} 