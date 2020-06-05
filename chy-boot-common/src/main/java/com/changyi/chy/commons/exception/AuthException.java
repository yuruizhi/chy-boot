package com.changyi.chy.commons.exception;


public class AuthException extends BaseException {


    public AuthException(String code, String msg) {
        super(code, msg);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
