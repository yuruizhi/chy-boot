package com.changyi.chy.commons.idempotent.aspect;

import com.changyi.chy.commons.idempotent.annotation.Idempotent;
import com.changyi.chy.commons.idempotent.exception.IdempotentException;
import com.changyi.chy.commons.idempotent.service.IdempotentTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

/**
 * 幂等性切面
 * 处理幂等性注解的AOP切面
 *
 * @author YuRuizhi
 */
@Slf4j
@Aspect
@Component
public class IdempotentAspect {

    @Autowired
    private IdempotentTokenService idempotentTokenService;

    /**
     * 环绕通知处理幂等性
     *
     * @param joinPoint 连接点
     * @return 方法执行结果
     * @throws Throwable 异常
     */
    @Around("@annotation(com.changyi.chy.commons.idempotent.annotation.Idempotent)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        
        // 获取幂等性注解
        Idempotent idempotent = method.getAnnotation(Idempotent.class);
        if (idempotent == null) {
            // 如果没有注解，直接执行
            return joinPoint.proceed();
        }
        
        // 获取请求
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            log.warn("无法获取请求上下文，跳过幂等性检查");
            return joinPoint.proceed();
        }
        
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        
        // 检查是否仅对POST请求生效
        if (idempotent.onlyPost() && !"POST".equalsIgnoreCase(request.getMethod())) {
            return joinPoint.proceed();
        }
        
        // 从请求头获取Token
        String token = request.getHeader(idempotent.headerName());
        if (!StringUtils.hasText(token)) {
            throw new IdempotentException("幂等性校验失败，缺少Token");
        }
        
        // 验证Token
        boolean isValid = idempotentTokenService.validateToken(idempotent.prefix(), token);
        if (!isValid) {
            throw new IdempotentException("幂等性校验失败，Token无效或已过期");
        }
        
        try {
            // 执行原方法
            Object result = joinPoint.proceed();
            
            // 如果设置了执行后删除，则删除Token
            if (idempotent.deleteOnExecution()) {
                idempotentTokenService.deleteToken(idempotent.prefix(), token);
                log.debug("幂等性Token已删除: {}", token);
            }
            
            return result;
        } catch (Exception e) {
            // 如果执行过程中出错，不删除Token，允许重试
            log.error("幂等方法执行出错: {}", e.getMessage());
            throw e;
        }
    }
} 