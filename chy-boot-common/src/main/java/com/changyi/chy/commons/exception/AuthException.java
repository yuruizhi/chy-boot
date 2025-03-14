package com.changyi.chy.commons.exception;


import com.changyi.chy.commons.api.AuthResultCode;

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
