package com.changyi.chy.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changyi.chy.commons.component.cache.RedisUtil;
import com.changyi.chy.demo.entity.User;
import com.changyi.chy.demo.mapper.UserMapper;
import com.changyi.chy.demo.service.UserService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 用户表(User)服务实现类
 *
 * @author YuRuizhi
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    @Resource
    private RedisUtil redisUtil;

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<User> queryAllByLimit(int offset, int limit) {
        return baseMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 通过用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    @Override
    public User findByUsername(String username) {
        // 先从缓存获取
        String cacheKey = "user:username:" + username;
        User user = (User) redisUtil.get(cacheKey);
        if (user != null) {
            return user;
        }
        
        // 缓存未命中，从数据库查询
        user = baseMapper.findByUsername(username);
        if (user != null) {
            // 将结果放入缓存，设置30分钟过期
            redisUtil.set(cacheKey, user, 30 * 60L);
        }
        
        return user;
    }
} 