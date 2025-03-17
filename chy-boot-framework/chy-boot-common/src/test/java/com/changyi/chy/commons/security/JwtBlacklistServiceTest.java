package com.changyi.chy.commons.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * JWT黑名单服务测试
 * 
 * @author YuRuizhi
 */
@DisplayName("JwtBlacklistService单元测试")
class JwtBlacklistServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;
    
    @Mock
    private ValueOperations<String, Object> valueOperations;
    
    @Mock
    private JwtUtils jwtUtils;
    
    @InjectMocks
    private JwtBlacklistService blacklistService;
    
    private static final String TEST_TOKEN = "test.jwt.token";
    private static final String BLACKLIST_KEY = "jwt:blacklist:test.jwt.token";
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }
    
    @Test
    @DisplayName("测试将令牌加入黑名单")
    void testAddToBlacklist() {
        // 准备测试数据
        long expirationTime = System.currentTimeMillis() + 3600000; // 当前时间加一小时
        
        // 配置Mock行为
        when(jwtUtils.getExpirationTimeFromToken(anyString())).thenReturn(expirationTime);
        when(valueOperations.set(anyString(), any(), anyLong(), any(TimeUnit.class))).thenReturn(true);
        
        // 执行测试
        boolean result = blacklistService.addToBlacklist(TEST_TOKEN);
        
        // 验证结果
        assertTrue(result);
        verify(valueOperations).set(eq(BLACKLIST_KEY), eq(true), anyLong(), eq(TimeUnit.MILLISECONDS));
    }
    
    @Test
    @DisplayName("测试检查令牌是否在黑名单中 - 在黑名单中")
    void testIsBlacklisted_True() {
        // 配置Mock行为
        when(valueOperations.get(anyString())).thenReturn(true);
        
        // 执行测试
        boolean result = blacklistService.isBlacklisted(TEST_TOKEN);
        
        // 验证结果
        assertTrue(result);
        verify(valueOperations).get(BLACKLIST_KEY);
    }
    
    @Test
    @DisplayName("测试检查令牌是否在黑名单中 - 不在黑名单中")
    void testIsBlacklisted_False() {
        // 配置Mock行为
        when(valueOperations.get(anyString())).thenReturn(null);
        
        // 执行测试
        boolean result = blacklistService.isBlacklisted(TEST_TOKEN);
        
        // 验证结果
        assertFalse(result);
        verify(valueOperations).get(BLACKLIST_KEY);
    }
} 