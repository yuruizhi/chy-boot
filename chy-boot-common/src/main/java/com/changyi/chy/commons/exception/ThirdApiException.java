package com.changyi.chy.commons.exception;


import com.changyi.chy.commons.component.constant.IMessageEnum;


public class ThirdApiException extends BaseException {

    public ThirdApiException(final Exception cause) {
        super(cause);
    }

    public ThirdApiException(IMessageEnum msg) {
        super(msg);
    }

    public ThirdApiException(String code, String msg) {
        super(code, msg);
    }

    public ThirdApiException(String msg) {
        super(msg);
    }

    public ThirdApiException(Throwable ex) {
        super(ex);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
