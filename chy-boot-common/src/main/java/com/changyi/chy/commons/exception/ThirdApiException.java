package com.changyi.chy.commons.exception;


import com.changyi.chy.commons.api.IResultCode;


/**
 * 第三个api例外
 *
 * @author Henry.yu
 */
public class ThirdApiException extends BaseException {

    public ThirdApiException(final Exception cause) {
        super(cause);
    }

    public ThirdApiException(IResultCode errorCode) {
        super(errorCode);
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
