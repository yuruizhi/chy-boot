package com.changyi.chy.common.auth.service;

import com.changyi.chy.common.auth.entity.User;

/**
 * 用户服务接口
 * 定义用户信息的基本操作，由system模块实现
 */
public interface UserService {
    
    /**
     * 通过用户名获取用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    User getUserByUsername(String username);
    
    /**
     * 通过用户ID获取用户
     *
     * @param userId 用户ID
     * @return 用户对象
     */
    User getUserById(String userId);
    
    /**
     * 校验用户密码
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户对象，校验失败返回null
     */
    User validateUser(String username, String password);
} 