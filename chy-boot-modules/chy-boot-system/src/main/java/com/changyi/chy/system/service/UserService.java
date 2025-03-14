package com.changyi.chy.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.changyi.chy.common.api.R;
import com.changyi.chy.persistence.request.PageRequest;
import com.changyi.chy.persistence.result.PageResult;
import com.changyi.chy.system.entity.User;

/**
 * 用户服务接口
 *
 * @author YuRuizhi
 */
public interface UserService extends IService<User> {

    /**
     * 分页查询用户列表
     *
     * @param request 分页请求
     * @return 分页结果
     */
    PageResult<User> page(PageRequest request);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    User getByUsername(String username);

    /**
     * 创建用户
     *
     * @param user 用户信息
     * @return 操作结果
     */
    R<User> createUser(User user);

    /**
     * 更新用户
     *
     * @param user 用户信息
     * @return 操作结果
     */
    R<Boolean> updateUser(User user);

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 操作结果
     */
    R<Boolean> deleteUser(String id);

    /**
     * 修改用户状态
     *
     * @param id     用户ID
     * @param status 状态
     * @return 操作结果
     */
    R<Boolean> changeStatus(String id, Integer status);

    /**
     * 重置密码
     *
     * @param id       用户ID
     * @param password 新密码
     * @return 操作结果
     */
    R<Boolean> resetPassword(String id, String password);
} 