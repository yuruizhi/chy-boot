package org.chy.boot.api.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 统一API响应结果封装
 *
 * @param <T> 数据类型
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private int code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 时间戳
     */
    private long timestamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();

    /**
     * 成功返回结果
     *
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> R<T> ok() {
        return new R<T>()
                .setCode(ResultCode.SUCCESS.getCode())
                .setMsg(ResultCode.SUCCESS.getMsg());
    }

    /**
     * 成功返回结果
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 响应结果
     */
    public static <T> R<T> ok(T data) {
        return new R<T>()
                .setCode(ResultCode.SUCCESS.getCode())
                .setMsg(ResultCode.SUCCESS.getMsg())
                .setData(data);
    }

    /**
     * 成功返回结果
     *
     * @param data 数据
     * @param msg  消息
     * @param <T>  数据类型
     * @return 响应结果
     */
    public static <T> R<T> ok(T data, String msg) {
        return new R<T>()
                .setCode(ResultCode.SUCCESS.getCode())
                .setMsg(msg)
                .setData(data);
    }

    /**
     * 失败返回结果
     *
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> R<T> error() {
        return new R<T>()
                .setCode(ResultCode.FAILURE.getCode())
                .setMsg(ResultCode.FAILURE.getMsg());
    }

    /**
     * 失败返回结果
     *
     * @param msg 消息
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> R<T> error(String msg) {
        return new R<T>()
                .setCode(ResultCode.FAILURE.getCode())
                .setMsg(msg);
    }

    /**
     * 失败返回结果
     *
     * @param code 状态码
     * @param msg  消息
     * @param <T>  数据类型
     * @return 响应结果
     */
    public static <T> R<T> error(int code, String msg) {
        return new R<T>()
                .setCode(code)
                .setMsg(msg);
    }

    /**
     * 失败返回结果
     *
     * @param resultCode 业务状态码
     * @param <T>        数据类型
     * @return 响应结果
     */
    public static <T> R<T> error(IResultCode resultCode) {
        return new R<T>()
                .setCode(resultCode.getCode())
                .setMsg(resultCode.getMsg());
    }

    /**
     * 判断是否成功
     *
     * @return 是否成功
     */
    public boolean isSuccess() {
        return this.code == ResultCode.SUCCESS.getCode();
    }
} 