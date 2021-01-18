package com.changyi.chy.commons.exception;


import cn.hutool.core.util.StrUtil;
import com.changyi.chy.commons.api.R;
import com.changyi.chy.commons.api.ResultCode;
import com.changyi.chy.commons.context.ContextNotInitException;
import com.changyi.chy.commons.jackson.JsonUtil;
import com.changyi.chy.commons.util.ArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 全局统一异常处理.
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(value = DnConsoleException.class)
    @ResponseBody
    public R DnConsoleExceptionHandler(DnConsoleException ex) {
        logger.error("业务:", ex);
        return R.fail(ResultCode.FAILURE, ex.getMessage());

    }


    @ExceptionHandler(value = ThirdApiException.class)
    @ResponseBody
    public R ThirdApiExceptionHandler(ThirdApiException ex) {
        logger.error("第三方业务:", ex);
        return R.fail(ResultCode.THIRD_API_FAILED, ex.getMessage());

    }

    @ExceptionHandler(value = AuthException.class)
    @ResponseBody
    public R authExceptionHandler(AuthException ex) {
        logger.error("未授权:", ex);
        return R.fail(ResultCode.UN_AUTHORIZED.getCode(), ResultCode.UN_AUTHORIZED.getMessage());
    }

    @ExceptionHandler(value = ServiceFusingException.class)
    @ResponseBody
    public R fusingExceptionHandler(ServiceFusingException fx) {
        logger.error("服务熔断，不可用:", fx);
        if (fx.getErrorCode().getCode() == 504) {
            return R.fail(ResultCode.GATEWAY_TIMEOUT_ERROR, ServiceFusingException.getData());
        } else {
            return R.fail(ResultCode.SERVICE_UNAVAILABLE_ERROR, ServiceFusingException.getData());
        }
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public R ExceptionHandler(Exception ex) {
        logger.error("异常:", ex);
        logger.error("异常message:{}", ex.getMessage());
        return R.fail(ResultCode.FAILURE);

    }

    @ExceptionHandler(value = CheckParamException.class)
    @ResponseBody
    public R CheckParamExceptionHandler(CheckParamException ex) {
        logger.error("CheckParam异常:", ex);
        return R.fail(ResultCode.PARAM_VALID_ERROR, ex.getData());

    }

    /**
     * 参数验证异常处理
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<Map<String, String>> resErrorList = new ArrayList<>();
        List<FieldError> fieldErrorList = e.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrorList) {
            Map<String, String> fieldMap = new HashMap<>();
            fieldMap.put("field", fieldError.getField());
            fieldMap.put("message", fieldError.getDefaultMessage());
            String value = String.valueOf(fieldError.getRejectedValue());
            if (StrUtil.isNotBlank(value)) {
                fieldMap.put("value", value);
            }
            resErrorList.add(fieldMap);
        }
        if (!ArrayUtil.isNullOrEmpty(resErrorList)) {
            logger.error("参数错误「{}」", JsonUtil.object2Json(resErrorList));
        }
        return R.fail(ResultCode.PARAM_VALID_ERROR, resErrorList);
    }

    /**
     * 请求参数语法错误
     *
     * @param ex
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public R httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        logger.error("Json语法错误异常:", ex);
        return R.fail(ResultCode.PARAM_TYPE_ERROR.getCode(), ResultCode.PARAM_TYPE_ERROR.getMessage());
    }


    /**
     * 上下文异常
     *
     * @param ex
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = ContextNotInitException.class)
    @ResponseBody
    public R contextNotInitExceptionHandler(ContextNotInitException ex) {
        logger.error("上下文异常:", ex);
        DnConsoleException causeException = (DnConsoleException) ex.getCause();
        if (null != causeException) {
            return R.fail(HttpServletResponse.SC_BAD_REQUEST, causeException.getMessage());
        }
        return R.fail(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
    }

}
