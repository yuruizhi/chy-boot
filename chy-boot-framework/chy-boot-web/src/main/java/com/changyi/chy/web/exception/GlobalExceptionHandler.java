package com.changyi.chy.web.exception;

import com.changyi.chy.common.api.R;
import com.changyi.chy.common.api.ResultCode;
import com.changyi.chy.commons.idempotent.exception.IdempotentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 *
 * @author YuRuizhi
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public R<?> handleBusinessException(BusinessException ex) {
        log.error("业务异常:", ex);
        return R.fail(ResultCode.FAILURE, ex.getMessage());
    }
    
    /**
     * 处理第三方API异常
     */
    @ExceptionHandler(ThirdApiException.class)
    public R<?> handleThirdApiException(ThirdApiException ex) {
        log.error("第三方API异常:", ex);
        return R.fail(ResultCode.FAILURE, ex.getMessage());
    }
    
    /**
     * 处理认证异常
     */
    @ExceptionHandler(AuthException.class)
    public R<?> handleAuthException(AuthException ex) {
        log.error("认证异常:", ex);
        return R.fail(ResultCode.UN_AUTHORIZED.getCode(), ResultCode.UN_AUTHORIZED.getMessage());
    }
    
    /**
     * 处理服务熔断异常
     */
    @ExceptionHandler(ServiceFusingException.class)
    public R<?> handleServiceFusingException(ServiceFusingException ex) {
        log.error("服务熔断异常:", ex);
        if (ex.getErrorCode().getCode() == 504) {
            return R.fail(ResultCode.GATEWAY_TIMEOUT_ERROR, ServiceFusingException.getData());
        } else {
            return R.fail(ResultCode.SERVICE_UNAVAILABLE_ERROR, ServiceFusingException.getData());
        }
    }
    
    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(CheckParamException.class)
    public R<?> handleCheckParamException(CheckParamException ex) {
        log.error("参数校验异常:", ex);
        return R.fail(ResultCode.PARAM_VALID_ERROR, ex.getData());
    }

    /**
     * 处理JSON解析异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("JSON解析异常:", ex);
        return R.fail(ResultCode.PARAM_TYPE_ERROR.getCode(), ResultCode.PARAM_TYPE_ERROR.getMessage());
    }
    
    /**
     * 处理参数校验异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Void> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.warn("参数校验失败: {}", errors);
        return R.fail(400, "参数校验失败");
    }

    /**
     * 处理绑定异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public R<Void> handleBindException(BindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.warn("参数绑定失败: {}", errors);
        return R.fail(400, "参数绑定失败");
    }

    /**
     * 处理幂等性异常
     */
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ExceptionHandler(IdempotentException.class)
    public R<Void> handleIdempotentException(IdempotentException ex) {
        log.warn("幂等性校验失败: {}", ex.getMessage());
        return R.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * 处理通用异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public R<Void> handleException(Exception ex) {
        log.error("系统异常", ex);
        return R.fail(500, "系统异常，请联系管理员");
    }
}
