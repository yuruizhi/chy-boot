package com.changyi.chy.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 业务代码枚举
 *
 * @author YuRuizhi
 */
@Getter
@AllArgsConstructor
public enum ResultCode implements IResultCode {

    /**
     * 操作成功
     */
    SUCCESS(HttpServletResponse.SC_OK, "操作成功"),

    /**
     * 业务异常
     */
    FAILURE(HttpServletResponse.SC_BAD_REQUEST, "业务异常"),

    /**
     * 401 请求未授权
     */
    UN_AUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, "token错误或已过期"),

    /**
     * 404 没找到请求
     */
    NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, "404 没找到请求"),

    /**
     * 消息不能读取
     */
    MSG_NOT_READABLE(HttpServletResponse.SC_BAD_REQUEST, "消息不能读取"),

    /**
     * 不支持当前请求方法
     */
    METHOD_NOT_SUPPORTED(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "不支持当前请求方法"),

    /**
     * 不支持当前媒体类型
     */
    MEDIA_TYPE_NOT_SUPPORTED(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "不支持当前媒体类型"),

    /**
     * 请求被拒绝
     */
    REQ_REJECT(HttpServletResponse.SC_FORBIDDEN, "请求被拒绝"),

    /**
     * 服务器异常
     */
    INTERNAL_SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器异常"),

    /**
     * 服务不可用异常
     */
    SERVICE_UNAVAILABLE_ERROR(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "服务不可用"),

    /**
     * 网关超时异常
     */
    GATEWAY_TIMEOUT_ERROR(HttpServletResponse.SC_GATEWAY_TIMEOUT, "网关超时"),

    /**
     * 缺少必要的请求参数
     */
    PARAM_MISS(HttpServletResponse.SC_BAD_REQUEST, "缺少必要的请求参数"),

    /**
     * 验证未通过
     */
    VERIFY_FAILED(HttpServletResponse.SC_BAD_REQUEST, "验证未通过"),
    
    /**
     * 参数错误
     */
    PARAM_ERROR(HttpServletResponse.SC_BAD_REQUEST, "参数错误"),

    /**
     * 参数类型错误
     */
    PARAM_TYPE_ERROR(HttpServletResponse.SC_BAD_REQUEST, "参数类型错误"),

    /**
     * 重复操作
     */
    REPEAT_OPERATION(HttpServletResponse.SC_BAD_REQUEST, "重复操作"),

    /**
     * 数据不存在
     */
    DATA_NOT_EXIST(HttpServletResponse.SC_BAD_REQUEST, "数据不存在"),

    /**
     * 数据已存在
     */
    DATA_ALREADY_EXIST(HttpServletResponse.SC_BAD_REQUEST, "数据已存在");

    /**
     * 状态码
     */
    private final int code;
    
    /**
     * 消息
     */
    private final String message;
} 