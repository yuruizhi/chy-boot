package com.chy.boot.common.commons.interceptor;


import com.chy.boot.common.commons.annotation.RequireAdmin;
import com.chy.boot.common.commons.exception.ClientTypeException;
import com.chy.boot.common.commons.utils.ClientTypeUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 管理员权限拦截器
 * 用于拦截和验证带有RequireAdmin注解的请求
 *
 * @author YuRuizhi
 * @date 2024/3/18
 */
@Slf4j
public class AdminRequireInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 如果不是HandlerMethod类型，直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        
        // 检查方法上是否有RequireAdmin注解
        RequireAdmin methodAnnotation = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), RequireAdmin.class);
        if (methodAnnotation != null) {
            checkAdminAccess(request, methodAnnotation);
            return true;
        }
        
        // 检查类上是否有RequireAdmin注解
        RequireAdmin classAnnotation = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), RequireAdmin.class);
        if (classAnnotation != null) {
            checkAdminAccess(request, classAnnotation);
            return true;
        }
        
        // 没有相关注解，直接放行
        return true;
    }
    
    /**
     * 检查是否为管理员访问
     *
     * @param request 请求
     * @param annotation 注解
     */
    private void checkAdminAccess(HttpServletRequest request, RequireAdmin annotation) {
        if (!ClientTypeUtils.isAdmin()) {
            String msg = "当前接口仅限管理后台访问";
            if (!"".equals(annotation.value())) {
                msg = annotation.value();
            }
            log.warn("非管理员客户端访问限制接口: {}, 客户端类型: {}", request.getRequestURI(), ClientTypeUtils.getClientType());
            throw new ClientTypeException(msg);
        }
    }
} 