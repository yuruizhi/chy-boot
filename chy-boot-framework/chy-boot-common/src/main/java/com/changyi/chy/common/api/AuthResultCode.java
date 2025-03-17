package com.changyi.chy.common.api;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证相关业务代码枚举
 * @author YuRuizhi
 */
@Getter
@AllArgsConstructor
public enum AuthResultCode implements IResultCode {
    /**
     * 未登录
     */
    NOT_LOGIN(401, "未登录"),
    /**
     * 登录失败
     */
    LOGIN_FAIL(401, "登录失败"),
    /**
     * 登录成功
     */
    LOGIN_SUCCESS(200, "登录成功"),
    /**
     * 退出成功
     */
    LOGOUT_SUCCESS(200, "退出成功"),
    /**
     * 无权限
     */
    NO_PERMISSION(403, "无权限"),
    /**
     * 令牌过期
     */
    TOKEN_EXPIRED(401, "令牌过期"),
    /**
     * 令牌无效
     */
    TOKEN_INVALID(401, "令牌无效");

    /**
     * 状态码
     */
    private final int code;
    /**
     * 消息
     */
    private final String message;
} 