package com.changyi.chy.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.changyi.chy.demo.entity.User;

import java.util.List;

/**
 * 用户表(User)服务接口
 *
 * @author YuRuizhi
 */
public interface UserService extends IService<User> {

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<User> queryAllByLimit(int offset, int limit);

    /**
     * 通过用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    User findByUsername(String username);
} 