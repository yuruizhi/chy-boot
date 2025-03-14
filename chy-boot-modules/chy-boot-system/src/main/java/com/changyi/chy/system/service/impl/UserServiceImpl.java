package com.changyi.chy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changyi.chy.common.api.R;
import com.changyi.chy.common.api.ResultCode;
import com.changyi.chy.persistence.request.PageRequest;
import com.changyi.chy.persistence.result.PageResult;
import com.changyi.chy.persistence.util.PageUtils;
import com.changyi.chy.persistence.util.QueryBuilder;
import com.changyi.chy.system.entity.User;
import com.changyi.chy.system.mapper.UserMapper;
import com.changyi.chy.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 用户服务实现类
 *
 * @author YuRuizhi
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public PageResult<User> page(PageRequest request) {
        // 构建查询条件
        LambdaQueryWrapper<User> wrapper = QueryBuilder.lambdaQuery();
        
        // 设置分页参数
        Page<User> page = PageUtils.convertToPage(request);
        
        // 执行分页查询
        page = page(page, wrapper);
        
        // 转换为分页结果
        return PageUtils.convertToPageResult(page);
    }

    @Override
    public User getByUsername(String username) {
        if (!StringUtils.hasText(username)) {
            return null;
        }
        
        LambdaQueryWrapper<User> wrapper = QueryBuilder.lambdaQuery();
        wrapper.eq(User::getUsername, username);
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<User> createUser(User user) {
        // 检查用户名是否已存在
        User existingUser = getByUsername(user.getUsername());
        if (existingUser != null) {
            return R.fail(ResultCode.DATA_ALREADY_EXIST, "用户名已存在");
        }
        
        // 加密密码
        if (StringUtils.hasText(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        
        // 设置默认状态
        if (user.getStatus() == null) {
            user.setStatus(1);
        }
        
        // 保存用户
        boolean success = save(user);
        
        return success ? R.data(user) : R.fail("创建用户失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> updateUser(User user) {
        if (user == null || !StringUtils.hasText(user.getId())) {
            return R.fail(ResultCode.PARAM_MISS, "用户ID不能为空");
        }
        
        // 检查用户是否存在
        User existingUser = getById(user.getId());
        if (existingUser == null) {
            return R.fail(ResultCode.DATA_NOT_EXIST, "用户不存在");
        }
        
        // 如果修改了用户名，需要检查是否与其他用户重复
        if (StringUtils.hasText(user.getUsername()) 
                && !user.getUsername().equals(existingUser.getUsername())) {
            User existingByUsername = getByUsername(user.getUsername());
            if (existingByUsername != null && !existingByUsername.getId().equals(user.getId())) {
                return R.fail(ResultCode.DATA_ALREADY_EXIST, "用户名已存在");
            }
        }
        
        // 不更新密码字段
        user.setPassword(null);
        
        // 更新用户
        boolean success = updateById(user);
        
        return R.status(success);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> deleteUser(String id) {
        if (!StringUtils.hasText(id)) {
            return R.fail(ResultCode.PARAM_MISS, "用户ID不能为空");
        }
        
        // 检查用户是否存在
        User existingUser = getById(id);
        if (existingUser == null) {
            return R.fail(ResultCode.DATA_NOT_EXIST, "用户不存在");
        }
        
        // 删除用户
        boolean success = removeById(id);
        
        return R.status(success);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> changeStatus(String id, Integer status) {
        if (!StringUtils.hasText(id)) {
            return R.fail(ResultCode.PARAM_MISS, "用户ID不能为空");
        }
        
        if (status == null) {
            return R.fail(ResultCode.PARAM_MISS, "状态不能为空");
        }
        
        // 检查用户是否存在
        User existingUser = getById(id);
        if (existingUser == null) {
            return R.fail(ResultCode.DATA_NOT_EXIST, "用户不存在");
        }
        
        // 更新状态
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        
        boolean success = updateById(user);
        
        return R.status(success);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> resetPassword(String id, String password) {
        if (!StringUtils.hasText(id)) {
            return R.fail(ResultCode.PARAM_MISS, "用户ID不能为空");
        }
        
        if (!StringUtils.hasText(password)) {
            return R.fail(ResultCode.PARAM_MISS, "密码不能为空");
        }
        
        // 检查用户是否存在
        User existingUser = getById(id);
        if (existingUser == null) {
            return R.fail(ResultCode.DATA_NOT_EXIST, "用户不存在");
        }
        
        // 更新密码
        User user = new User();
        user.setId(id);
        user.setPassword(passwordEncoder.encode(password));
        
        boolean success = updateById(user);
        
        return R.status(success);
    }
} 