package com.changyi.chy.common.exception;

/**
 * 认证异常
 */
public class AuthException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private int code = 401;
    
    public AuthException(String message) {
        super(message);
    }
    
    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public AuthException(int code, String message) {
        super(message);
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
} 