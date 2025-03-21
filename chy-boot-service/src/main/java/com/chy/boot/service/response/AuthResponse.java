package com.chy.boot.service.response;

import lombok.Data;

/**
 * 认证响应结果
 *
 * @auther Henry.Yu
 * @date 2023/09/06
 */
@Data
public class AuthResponse {

    /**
     * 访问令牌
     */
    private String token;

    /**
     * 过期时间（时间戳）
     */
    private Long expireTime;
} 