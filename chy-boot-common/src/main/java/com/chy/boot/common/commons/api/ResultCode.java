package com.chy.boot.common.commons.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 业务代码枚举.
 *
 * @author zhaochurui
 * @date 2020.1.9
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
    UN_AUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, "access_token错误"),


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
     * 第三方接口错误
     */
    THIRD_API_FAILED(99001, "第三方接口错误"),


    /**
     * 图片验证码不正确
     */
    CHAPTCHA_FAILED(99002, "图片验证码不正确"),

    /**
     * 回调失败
     */
    CALLBACK_FAILED(HttpServletResponse.SC_BAD_REQUEST, "回调错误"),

    /**
     * 请求参数类型错误
     */
    PARAM_TYPE_ERROR(HttpServletResponse.SC_BAD_REQUEST, "请求参数类型错误"),

    /**
     * 请求参数绑定错误
     */
    PARAM_BIND_ERROR(HttpServletResponse.SC_BAD_REQUEST, "请求参数绑定错误"),

    /**
     * 参数校验失败
     */
    PARAM_VALID_ERROR(HttpServletResponse.SC_BAD_REQUEST, "参数校验失败"),

    /**
     * 数据不存在
     */
    DATA_NOT_EXIST(HttpServletResponse.SC_BAD_REQUEST, "数据不存在"),

    /**
     * url里brand品牌信息不合法
     */
    BRAND_NOT_EXIST(HttpServletResponse.SC_BAD_REQUEST, "url里brand品牌信息不合法"),

    /**
     * 会员数据不存在
     */
    MEMBER_DATA_NOT_EXIST(HttpServletResponse.SC_BAD_REQUEST, "数据不存在"),

    /**
     * 群脉会员创建失败
     */
    QM_MEMBER_DATA_CREATE_FAIL(HttpServletResponse.SC_BAD_REQUEST, "群脉会员创建失败"),


    /**
     * 查询群脉会员失败
     */
    QM_MEMBER_DATA_GET_FAIL(HttpServletResponse.SC_BAD_REQUEST, "查询群脉会员失败"),

    /**
     * 群脉会员更新失败
     */
    QM_MEMBER_DATA_UPDATE_FAIL(HttpServletResponse.SC_BAD_REQUEST, "群脉会员更新失败"),

    /**
     * 创建数据失败
     */
    DB_CREAT_DATA_FAIL(HttpServletResponse.SC_BAD_REQUEST, "创建数据失败"),

    /**
     * 更新数据失败
     */
    DB_UPDATE_DATA_FAIL(HttpServletResponse.SC_BAD_REQUEST, "更新数据失败"),


    /**
     * 逻辑删除数据失败
     */
    DB_DELETE_DATA_FAIL(HttpServletResponse.SC_BAD_REQUEST, "逻辑删除数据失败"),

    /**
     * 图片URL参数错误
     */
    POO_IMG_URL_PARAM_ERROR(HttpServletResponse.SC_BAD_REQUEST, "图片URL参数错误"),

    /**
     * 图片大小不能大于1MB
     */
    POO_IMG_SIZE_PARAM_ERROR(HttpServletResponse.SC_BAD_REQUEST, "图片不能大于1MB"),

    /**
     * 图片外型未识别出结果
     */
    POO_IMG_SHAPE_ERROR(HttpServletResponse.SC_BAD_REQUEST, "图片外型未识别出结果"),
    ;

    /**
     * code编码
     */
    final int code;
    /**
     * 中文信息描述
     */
    final String message;

}

