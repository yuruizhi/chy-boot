package com.changyi.chy.common.api;

import com.changyi.chy.common.constant.CommonConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import jakarta.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Optional;

/**
 * 统一API响应结果封装
 *
 * @author YuRuizhi
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Schema(description = "统一响应结果")
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "状态码")
    private int code;

    @Schema(description = "返回消息")
    private String msg;

    @Schema(description = "请求ID")
    private String requestId;

    @Schema(description = "承载数据")
    private T data;

    private R(IResultCode resultCode) {
        this(resultCode, null, resultCode.getMessage());
    }

    private R(IResultCode resultCode, String msg) {
        this(resultCode, null, msg);
    }

    private R(IResultCode resultCode, T data) {
        this(resultCode, data, resultCode.getMessage());
    }

    private R(IResultCode resultCode, T data, String msg) {
        this(resultCode.getCode(), data, msg);
    }

    private R(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        // 请求ID可以通过线程上下文获取，在异常处理等拦截器中设置
        // 暂时设置为null
        this.requestId = null;
    }

    /**
     * 判断返回是否为成功
     *
     * @param result Result
     * @return 是否成功
     */
    public static boolean isSuccess(@Nullable R<?> result) {
        return Optional.ofNullable(result)
                .map(x -> ResultCode.SUCCESS.getCode() == x.code)
                .orElse(Boolean.FALSE);
    }

    /**
     * 判断返回是否为失败
     *
     * @param result Result
     * @return 是否失败
     */
    public static boolean isNotSuccess(@Nullable R<?> result) {
        return !R.isSuccess(result);
    }

    /**
     * 返回带数据的成功结果
     *
     * @param data 数据
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> R<T> data(T data) {
        return data(data, CommonConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    /**
     * 返回带数据和消息的成功结果
     *
     * @param data 数据
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> R<T> data(T data, String msg) {
        return data(HttpServletResponse.SC_OK, data, msg);
    }

    /**
     * 返回自定义状态码、数据和消息的结果
     *
     * @param code 状态码
     * @param data 数据
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> R<T> data(int code, T data, String msg) {
        return new R<>(code, data, data == null ? CommonConstant.DEFAULT_NULL_MESSAGE : msg);
    }

    /**
     * 返回带消息的成功结果
     *
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return R
     */
    public static <T> R<T> success(String msg) {
        return new R<>(ResultCode.SUCCESS, msg);
    }

    /**
     * 返回默认成功结果
     *
     * @param <T> T 泛型标记
     * @return R
     */
    public static <T> R<T> success() {
        return new R<>(ResultCode.SUCCESS);
    }

    /**
     * 返回指定业务代码的成功结果
     *
     * @param resultCode 业务代码
     * @param <T>        T 泛型标记
     * @return R
     */
    public static <T> R<T> success(IResultCode resultCode) {
        return new R<>(resultCode);
    }

    /**
     * 返回指定业务代码和消息的成功结果
     *
     * @param resultCode 业务代码
     * @param msg        消息
     * @param <T>        T 泛型标记
     * @return R
     */
    public static <T> R<T> success(IResultCode resultCode, String msg) {
        return new R<>(resultCode, msg);
    }

    /**
     * 返回带消息的失败结果
     *
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return R
     */
    public static <T> R<T> fail(String msg) {
        return new R<>(ResultCode.FAILURE, msg);
    }

    /**
     * 返回自定义状态码和消息的失败结果
     *
     * @param code 状态码
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> R<T> fail(int code, String msg) {
        return new R<>(code, null, msg);
    }

    /**
     * 返回指定业务代码的失败结果
     *
     * @param resultCode 业务代码
     * @param <T>        T 泛型标记
     * @return R
     */
    public static <T> R<T> fail(IResultCode resultCode) {
        return new R<>(resultCode);
    }

    /**
     * 返回指定业务代码和消息的失败结果
     *
     * @param resultCode 业务代码
     * @param msg        消息
     * @param <T>        T 泛型标记
     * @return R
     */
    public static <T> R<T> fail(IResultCode resultCode, String msg) {
        return new R<>(resultCode, msg);
    }

    /**
     * 返回带结果的失败结果
     *
     * @param resultCode 业务代码
     * @param data       数据
     * @param <T>        T 泛型标记
     * @return R
     */
    public static <T> R<T> fail(IResultCode resultCode, T data) {
        return new R<>(resultCode, data);
    }

    /**
     * 根据状态返回成功或失败结果
     *
     * @param flag 状态
     * @return R
     */
    public static <T> R<T> status(boolean flag) {
        return flag ? success(CommonConstant.DEFAULT_SUCCESS_MESSAGE) : fail(CommonConstant.DEFAULT_FAILURE_MESSAGE);
    }
} 