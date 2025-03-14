package com.changyi.chy.common.exception;

import com.changyi.chy.common.api.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BusinessException.class)
    public ApiResult<?> handleBusinessException(BusinessException e) {
        log.error("业务异常", e);
        return ApiResult.fail(e.getCode(), e.getMessage());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult<?> handleValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        log.error("参数校验异常：{}", fieldError != null ? fieldError.getDefaultMessage() : e.getMessage());
        return ApiResult.fail("参数错误: " + (fieldError != null ? fieldError.getDefaultMessage() : ""));
    }
    
    @ExceptionHandler(BindException.class)
    public ApiResult<?> handleBindException(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        log.error("参数绑定异常：{}", fieldError != null ? fieldError.getDefaultMessage() : e.getMessage());
        return ApiResult.fail("参数错误: " + (fieldError != null ? fieldError.getDefaultMessage() : ""));
    }
    
    @ExceptionHandler(Exception.class)
    public ApiResult<?> handleException(Exception e) {
        log.error("系统异常", e);
        return ApiResult.fail("系统繁忙，请稍后再试");
    }
} 