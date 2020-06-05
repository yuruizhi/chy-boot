package com.changyi.chy.commons.exception;

import com.changyi.chy.commons.component.constant.IMessageEnum;
import com.changyi.chy.commons.component.validate.Valid;
import java.util.List;


public class CheckParamException extends BaseException {

    private List<Valid> data;

    public CheckParamException(final Exception cause) {
        super(cause);
    }

    public CheckParamException(IMessageEnum msg) {
        super(msg);
    }

    public CheckParamException(String code, String msg) {
        super(code, msg);
    }

    public CheckParamException(String code, String msg, List<Valid> data) {
        super(code, msg);
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
