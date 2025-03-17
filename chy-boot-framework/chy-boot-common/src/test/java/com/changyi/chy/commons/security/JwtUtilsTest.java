package com.changyi.chy.commons.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JwtUtils单元测试
 * 
 * @author YuRuizhi
 */
@DisplayName("JwtUtils单元测试")
class JwtUtilsTest {

    private JwtUtils jwtUtils;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
        // 设置密钥和过期时间
        ReflectionTestUtils.setField(jwtUtils, "secret", "testSecretKeyForJwtUtilsTestingPurposeOnly");
        ReflectionTestUtils.setField(jwtUtils, "expiration", 3600000L); // 1小时
        ReflectionTestUtils.setField(jwtUtils, "refreshExpiration", 86400000L); // 24小时

        // 创建测试用户
        userDetails = User.builder()
                .username("testuser")
                .password("password")
                .authorities(Collections.emptyList())
                .build();
    }

    @Test
    @DisplayName("测试生成访问令牌")
    void testGenerateToken() {
        String token = jwtUtils.generateToken(userDetails);
        
        assertNotNull(token);
        assertTrue(token.length() > 20);
    }

    @Test
    @DisplayName("测试生成刷新令牌")
    void testGenerateRefreshToken() {
        String refreshToken = jwtUtils.generateRefreshToken(userDetails);
        
        assertNotNull(refreshToken);
        assertTrue(refreshToken.length() > 20);
    }

    @Test
    @DisplayName("测试验证令牌")
    void testValidateToken() {
        String token = jwtUtils.generateToken(userDetails);
        
        boolean isValid = jwtUtils.validateToken(token);
        
        assertTrue(isValid);
    }

    @Test
    @DisplayName("测试从令牌获取用户名")
    void testGetUsernameFromToken() {
        String token = jwtUtils.generateToken(userDetails);
        
        String username = jwtUtils.getUsernameFromToken(token);
        
        assertEquals("testuser", username);
    }

    @Test
    @DisplayName("测试无效令牌验证")
    void testInvalidToken() {
        String invalidToken = "invalid.token.string";
        
        boolean isValid = jwtUtils.validateToken(invalidToken);
        
        assertFalse(isValid);
    }

    @Test
    @DisplayName("测试从无效令牌获取用户名")
    void testGetUsernameFromInvalidToken() {
        String invalidToken = "invalid.token.string";
        
        String username = jwtUtils.getUsernameFromToken(invalidToken);
        
        assertNull(username);
    }
} 