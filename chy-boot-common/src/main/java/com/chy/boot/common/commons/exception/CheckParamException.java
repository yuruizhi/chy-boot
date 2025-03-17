package com.chy.boot.common.commons.exception;

import com.chy.boot.common.commons.api.IResultCode;
import com.chy.boot.common.commons.component.validate.Valid;

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
