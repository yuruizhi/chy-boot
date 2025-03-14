package com.changyi.chy.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    
    private final String code;
    
    public BusinessException(String message) {
        this("500", message);
    }
    
    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }
} 