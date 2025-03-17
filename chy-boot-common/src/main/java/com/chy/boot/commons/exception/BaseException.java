package com.chy.boot.commons.exception;


import com.chy.boot.commons.api.IResultCode;

/**
 * 异常基类.
 *
 * @author zhaochurui
 * @date 2020.1.12
 */
public class BaseException extends RuntimeException {

    private IResultCode errorCode;

    public BaseException(IResultCode resultCode) {
        super(resultCode.getMessage());
        this.errorCode = resultCode;
    }

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(Throwable exception) {
        super(exception);
    }

    public BaseException(String msg, Throwable ex) {
        super(msg, ex);
    }

    public IResultCode getErrorCode() {
        return errorCode;
    }
}
