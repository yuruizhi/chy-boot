package com.changyi.chy.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changyi.chy.commons.component.cache.MultilevelCacheService;
import com.changyi.chy.demo.entity.User;
import com.changyi.chy.demo.mapper.UserMapper;
import com.changyi.chy.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户表(User)服务实现类
 *
 * @author YuRuizhi
 */
@Slf4j
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    @Autowired
    private MultilevelCacheService cacheService;

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    @Cacheable(value = "mediumTerm", key = "'userList:' + #offset + ':' + #limit")
    public List<User> queryAllByLimit(int offset, int limit) {
        log.debug("查询用户列表，offset={}, limit={}", offset, limit);
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
        String cacheKey = "user:username:" + username;
        
        // 使用多级缓存服务获取用户
        return cacheService.get("userCache", cacheKey, () -> {
            log.debug("从数据库查询用户, username={}", username);
            return baseMapper.findByUsername(username);
        });
    }
    
    /**
     * 保存用户
     *
     * @param user 用户实体
     * @return 是否成功
     */
    @Override
    @CachePut(value = "userCache", key = "'user:id:' + #result.id", condition = "#result != null")
    public boolean save(User user) {
        log.debug("保存用户，username={}", user.getUsername());
        boolean result = super.save(user);
        
        // 手动清除可能存在的用户名缓存
        if (result) {
            cacheService.evict("userCache", "user:username:" + user.getUsername());
        }
        
        return result;
    }
    
    /**
     * 更新用户
     *
     * @param user 用户实体
     * @return 是否成功
     */
    @Override
    @CachePut(value = "userCache", key = "'user:id:' + #user.id", condition = "#result == true")
    public boolean updateById(User user) {
        log.debug("更新用户，id={}", user.getId());
        boolean result = super.updateById(user);
        
        // 更新成功后，清除用户名相关缓存
        if (result) {
            cacheService.evict("userCache", "user:username:" + user.getUsername());
        }
        
        return result;
    }
    
    /**
     * 删除用户
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    @CacheEvict(value = "userCache", key = "'user:id:' + #id")
    public boolean removeById(java.io.Serializable id) {
        log.debug("删除用户，id={}", id);
        
        // 先查询用户，以便删除后清除用户名缓存
        User user = getById(id);
        boolean result = super.removeById(id);
        
        // 清除用户名缓存
        if (result && user != null) {
            cacheService.evict("userCache", "user:username:" + user.getUsername());
            log.debug("清除用户缓存，username={}", user.getUsername());
        }
        
        return result;
    }
} 