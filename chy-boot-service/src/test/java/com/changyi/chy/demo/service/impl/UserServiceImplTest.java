package com.changyi.chy.demo.service.impl;

import com.changyi.chy.commons.component.cache.RedisUtil;
import com.changyi.chy.demo.entity.User;
import com.changyi.chy.demo.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 用户服务实现类测试
 * @author YuRuizhi
 */
@DisplayName("UserServiceImpl 单元测试")
class UserServiceImplTest {

    @Mock
    private UserMapper baseMapper;

    @Mock
    private RedisUtil redisUtil;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("测试分页查询")
    void testQueryAllByLimit() {
        // 准备测试数据
        List<User> expectedList = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("admin");
        user1.setNickname("管理员");
        
        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user");
        user2.setNickname("普通用户");
        
        expectedList.add(user1);
        expectedList.add(user2);
        
        // 配置Mock行为
        when(baseMapper.queryAllByLimit(anyInt(), anyInt())).thenReturn(expectedList);
        
        // 执行测试方法
        List<User> result = userService.queryAllByLimit(0, 10);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("admin", result.get(0).getUsername());
        assertEquals("管理员", result.get(0).getNickname());
        assertEquals("user", result.get(1).getUsername());
        
        // 验证方法调用
        verify(baseMapper).queryAllByLimit(0, 10);
    }

    @Test
    @DisplayName("测试通过用户名查询用户 - 缓存命中")
    void testFindByUsername_CacheHit() {
        // 准备测试数据
        String username = "admin";
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setUsername(username);
        expectedUser.setNickname("管理员");
        
        // 配置Mock行为
        when(redisUtil.get(anyString())).thenReturn(expectedUser);
        
        // 执行测试方法
        User result = userService.findByUsername(username);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals("管理员", result.getNickname());
    }

    @Test
    @DisplayName("测试通过用户名查询用户 - 缓存未命中")
    void testFindByUsername_CacheMiss() {
        // 准备测试数据
        String username = "admin";
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setUsername(username);
        expectedUser.setNickname("管理员");
        
        // 配置Mock行为
        when(redisUtil.get(anyString())).thenReturn(null);
        when(baseMapper.findByUsername(anyString())).thenReturn(expectedUser);
        
        // 执行测试方法
        User result = userService.findByUsername(username);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals("管理员", result.getNickname());
        
        // 验证方法调用
        verify(baseMapper).findByUsername(username);
        verify(redisUtil).set(anyString(), any(User.class), anyLong());
    }
} 