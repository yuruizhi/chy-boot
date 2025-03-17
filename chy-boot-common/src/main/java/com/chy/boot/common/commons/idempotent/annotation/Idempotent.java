package com.chy.boot.common.commons.idempotent.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 接口幂等性注解
 * 标记需要进行幂等性控制的方法
 *
 * @author YuRuizhi
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

    /**
     * 幂等性的超时时间，默认5秒
     */
    long timeout() default 5;

    /**
     * 时间单位，默认秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 幂等性Key的前缀
     */
    String prefix() default "idempotent";

    /**
     * 是否仅对POST请求进行幂等性控制
     */
    boolean onlyPost() default true;

    /**
     * 幂等性Token的请求头名称
     */
    String headerName() default "Idempotent-Token";

    /**
     * 是否在第一次请求成功后删除Token
     * true: 第一次请求成功后删除Token，之后的相同Token请求都会失败
     * false: 在超时时间内，相同Token的请求会被认为是幂等的，不会重复执行
     */
    boolean deleteOnExecution() default true;
} 