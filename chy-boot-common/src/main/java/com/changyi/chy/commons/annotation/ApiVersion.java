package com.changyi.chy.commons.annotation;

import java.lang.annotation.*;

/**
 * 自定义版本号标记注解.
 *
 * @author Henry.yu
 * @date 2020.6.30
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {

    /**
     * 标识版本号，从1开始
     */
    int value() default 1;

}
