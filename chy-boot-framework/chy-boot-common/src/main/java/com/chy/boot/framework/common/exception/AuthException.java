package com.chy.boot.common.commons.exception;


import com.chy.boot.common.commons.api.AuthResultCode;

/**
 * 身份验证异常
 *
 * @author YuRuizhi
 * @date 2021/01/18
 */
public class AuthException extends BaseException {

    public AuthException(AuthResultCode errorCode) {
        super(errorCode);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
