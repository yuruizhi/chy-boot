package com.changyi.chy.web.exception;

import com.changyi.chy.common.api.IResultCode;

/**
 * 基础异常类
 *
 * @author YuRuizhi
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    private final IResultCode resultCode;

    public BaseException(String message) {
        super(message);
        this.resultCode = null;
    }

    public BaseException(Throwable cause) {
        super(cause);
        this.resultCode = null;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.resultCode = null;
    }
    
    public BaseException(IResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }
    
    public BaseException(IResultCode resultCode, Throwable cause) {
        super(resultCode.getMessage(), cause);
        this.resultCode = resultCode;
    }
    
    public IResultCode getResultCode() {
        return resultCode;
    }
} 