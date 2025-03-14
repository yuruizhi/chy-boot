package com.changyi.chy.web.exception;

/**
 * 第三方API异常
 * 
 * @author YuRuizhi
 */
public class ThirdApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ThirdApiException(String message) {
        super(message);
    }

    public ThirdApiException(String message, Throwable cause) {
        super(message, cause);
    }
} 