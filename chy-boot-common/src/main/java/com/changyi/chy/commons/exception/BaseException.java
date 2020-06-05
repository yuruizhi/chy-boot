package com.changyi.chy.commons.exception;


import com.changyi.chy.commons.component.constant.IMessageEnum;
import com.changyi.chy.commons.context.ExecuteContext;
import org.slf4j.Logger;

/**
 * 异常基类.
 *
 * @author zhaochurui
 * @date 2020.1.12
 */
public class BaseException extends RuntimeException {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(BaseException.class);

    private String code;
    private IMessageEnum error;

    public BaseException(String code, String msg) {
        super(msg);
        this.code = code;

        logger.error("异常详情:channel:{}, code:{}, msg:{}", ExecuteContext.getContext().getChannel(), code, msg);
    }

    public BaseException(String msg) {
        super(msg);

        logger.error("异常详情:channel:{}, msg:{}", ExecuteContext.getContext().getChannel(), msg);
    }

    public BaseException(String code, String msg, Throwable ex) {
        super(msg, ex);
        this.code = code;
        // logger.error(LogUtil.message("异常详情", code, msg));
    }

    public BaseException(IMessageEnum msg) {
        super(msg == null ? "" : msg.getDesc());
        this.error = msg;
        this.code = msg == null ? "" : msg.getValue();
    }

    public BaseException(IMessageEnum msg, Throwable ex) {
        super(msg == null ? "" : msg.getDesc(), ex);
        this.error = msg;
        this.code = msg == null ? "" : msg.getValue();
    }

    public BaseException(Throwable exception) {
        super(exception);
    }

    public String getCode() {
        return code;
    }
}
