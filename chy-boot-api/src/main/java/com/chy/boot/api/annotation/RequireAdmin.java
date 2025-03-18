package com.chy.boot.api.annotation;

import java.lang.annotation.*;

/**
 * 管理员权限注解
 * 标记只有管理后台才能访问的方法或接口
 *
 * @author YuRuizhi
 * @date 2024/3/18
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireAdmin {
    
    /**
     * 权限描述，用于说明该方法需要什么权限
     */
    String value() default "";
    
} 