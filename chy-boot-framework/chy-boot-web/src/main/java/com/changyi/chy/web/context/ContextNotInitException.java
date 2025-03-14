package com.changyi.chy.commons.context;


import com.changyi.chy.commons.api.IResultCode;
import com.changyi.chy.commons.exception.BaseException;

/**
 * 上下文初始化自定义异常类.
 *
 * @author three
 * @date 2016/5/19
 */
public class ContextNotInitException extends BaseException {

    public ContextNotInitException(String info) {
        super(info);
    }

    public ContextNotInitException(Exception e) {
        super(e);
    }

	public ContextNotInitException(IResultCode errorCode) {
		super(errorCode);
	}

    public ContextNotInitException(Throwable ex) {
        super(ex);
    }
}
