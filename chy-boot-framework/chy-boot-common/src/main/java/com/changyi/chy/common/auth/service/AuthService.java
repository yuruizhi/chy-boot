package com.changyi.chy.common.auth.service;

import com.changyi.chy.common.auth.entity.AuthResponse;
import com.changyi.chy.common.exception.AuthException;

/**
 * 认证服务接口
 * 用于处理用户认证相关的业务逻辑
 */
public interface AuthService {
    
    /**
     * 获取认证token
     *
     * @param username 用户名
     * @param password 密码
     * @return 认证响应结果
     * @throws AuthException 认证异常
     */
    AuthResponse getToken(String username, String password) throws AuthException;
    
    /**
     * 刷新token
     *
     * @param refreshToken 刷新token
     * @return 认证响应结果
     * @throws AuthException 认证异常
     */
    AuthResponse refreshToken(String refreshToken) throws AuthException;
    
    /**
     * 验证token
     *
     * @param token 访问token
     * @return 验证结果，true为有效，false为无效
     */
    boolean validateToken(String token);
    
    /**
     * 获取token中的用户ID
     *
     * @param token 访问token
     * @return 用户ID
     */
    String getUserId(String token);
} 