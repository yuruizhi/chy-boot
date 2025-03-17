package com.changyi.chy.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.changyi.chy.system.entity.SysUser;

import java.util.List;

/**
 * 系统用户服务接口
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SysUser> queryAllByLimit(int offset, int limit);

    /**
     * 通过用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    SysUser findByUsername(String username);
    
    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 是否成功
     */
    boolean updatePassword(String userId, String oldPassword, String newPassword);
} 