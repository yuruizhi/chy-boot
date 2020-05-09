package com.changyi.chy.commons.context;


import com.changyi.chy.commons.component.comstant.IMessageEnum;
import com.changyi.chy.commons.exception.BaseException;

/**
 * 上下文初始化自定义异常类.
 *
 * @author three
 * @date 2016/5/19
 */
public class ContextNotInitException extends BaseException {

    public ContextNotInitException() {
        super("context not init", "上下文环境没有做必要的初始化");
    }

    public ContextNotInitException(String info) {
        super("context info get error", info);
    }

    public ContextNotInitException(Exception e) {
        super(e);
    }

	public ContextNotInitException(IMessageEnum msg) {
		super(msg);
	}

    public ContextNotInitException(String code, String msg) {
        super(code, msg);
    }

    public ContextNotInitException(Throwable ex) {
        super(ex);
    }
}
