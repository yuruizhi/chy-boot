package com.changyi.chy.commons.exception;


import com.changyi.chy.commons.api.IResultCode;

/**
 * UC自定义异常
 *
 * @author gaoyaqiu
 * @date 2016/5/24
 */
public class DnConsoleException extends BaseException {

    public DnConsoleException(final Exception cause) {
        super(cause);
    }

    public DnConsoleException(IResultCode msg) {
        super(msg);
    }

    public DnConsoleException(String msg) {
        super(msg);
    }

    public DnConsoleException(Throwable ex) {
        super(ex);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
