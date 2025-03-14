package com.changyi.chy.commons.exception;

import com.changyi.chy.commons.api.IResultCode;
import com.changyi.chy.commons.component.validate.Valid;

import java.util.List;


public class CheckParamException extends BaseException {

    private List<Valid> data;

    public CheckParamException(final Exception cause) {
        super(cause);
    }

    public CheckParamException(IResultCode msg) {
        super(msg);
    }

    public CheckParamException(IResultCode errorCode, List<Valid> data) {
        super(errorCode);
        this.data = data;
    }

    public CheckParamException(String msg, List<Valid> data) {
        super(msg);
        this.data = data;
    }

    public CheckParamException(String msg) {
        super(msg);
    }

    public List<Valid> getData() {
        return data;
    }

    public CheckParamException(Throwable ex) {
        super(ex);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
