package com.chy.boot.commons.idempotent.exception;

/**
 * 幂等性异常
 * 当接口幂等性校验失败时抛出
 *
 * @author YuRuizhi
 */
public class IdempotentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private final int code;

    /**
     * 构造函数
     *
     * @param message 错误消息
     */
    public IdempotentException(String message) {
        this(4001, message);
    }

    /**
     * 构造函数
     *
     * @param code 错误码
     * @param message 错误消息
     */
    public IdempotentException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    public int getCode() {
        return code;
    }
} 