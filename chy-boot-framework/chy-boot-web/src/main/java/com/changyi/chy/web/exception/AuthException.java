package com.changyi.chy.web.exception;

/**
 * 认证异常
 * 
 * @author YuRuizhi
 */
public class AuthException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }
} 