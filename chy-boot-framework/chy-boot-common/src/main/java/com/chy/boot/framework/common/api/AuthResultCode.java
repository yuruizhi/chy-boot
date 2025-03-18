package com.chy.boot.framework.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证结果代码
 *
 * @author YuRuizhi
 * @date 2021/01/18
 */
@Getter
@AllArgsConstructor
public enum AuthResultCode implements IResultCode {

    /**
     * 令牌未找到
     */
    TOKEN_NOT_FOUND(401, "无token"),
    /**
     * jwt解码错误
     */
    JWT_DECODE_ERROR(401, "token解码错误"),
    /**
     * jwt验证异常
     */
    JWT_VERIFICATION_ERROR(401, "token验证错误");

    private final int code;
    private final String message;
}
