package com.changyi.chy.common.auth.entity;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 认证请求参数
 */
@Data
public class AuthRequest {
    
    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    @Size(min = 3, message = "账号长度不能小于3")
    private String username;
    
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 3, message = "密码长度不能小于3")
    private String password;
} 