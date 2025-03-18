package com.chy.boot.common.commons.exception;

/**
 * 客户端类型异常
 * 在客户端类型不符合要求时抛出
 *
 * @author YuRuizhi
 * @date 2024/3/18
 */
public class ClientTypeException extends RuntimeException {
    
    public ClientTypeException(String message) {
        super(message);
    }
    
    public ClientTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}