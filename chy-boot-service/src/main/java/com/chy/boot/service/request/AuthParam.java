package com.chy.boot.service.request;

import lombok.Data;

/**
 * 认证请求参数
 *
 * @auther Henry.Yu
 * @date 2023/09/06
 */
@Data
public class AuthParam {

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;
} 