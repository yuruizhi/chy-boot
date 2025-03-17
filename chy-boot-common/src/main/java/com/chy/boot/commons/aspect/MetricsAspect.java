package com.chy.boot.commons.aspect;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 度量指标切面
 * 统计方法调用情况
 *
 * @author YuRuizhi
 */
@Slf4j
@Aspect
@Component
public class MetricsAspect {

    @Autowired
    private MeterRegistry meterRegistry;

    /**
     * 控制器切点
     */
    @Pointcut("execution(* com.changyi.chy..controller.*.*(..))")
    public void controllerPointcut() {}

    /**
     * 服务切点
     */
    @Pointcut("execution(* com.changyi.chy..service.*.*(..))")
    public void servicePointcut() {}

    /**
     * 控制器方法监控
     */
    @Around("controllerPointcut()")
    public Object monitorControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return monitorMethod(joinPoint, "controller");
    }

    /**
     * 服务方法监控
     */
    @Around("servicePointcut()")
    public Object monitorServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return monitorMethod(joinPoint, "service");
    }

    /**
     * 监控方法执行
     *
     * @param joinPoint 连接点
     * @param type 类型（controller或service）
     * @return 方法执行结果
     * @throws Throwable 如果方法执行出错
     */
    private Object monitorMethod(ProceedingJoinPoint joinPoint, String type) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String methodName = method.getName();
        String className = method.getDeclaringClass().getSimpleName();
        String fullMethodName = className + "." + methodName;
        
        // 计时器
        Timer timer = Timer.builder("method.timer")
                .tag("type", type)
                .tag("class", className)
                .tag("method", methodName)
                .description("Method execution time")
                .register(meterRegistry);
        
        // 计数器
        Counter counter = Counter.builder("method.calls")
                .tag("type", type)
                .tag("class", className)
                .tag("method", methodName)
                .description("Method call count")
                .register(meterRegistry);
        
        // 错误计数器
        Counter errorCounter = Counter.builder("method.errors")
                .tag("type", type)
                .tag("class", className)
                .tag("method", methodName)
                .description("Method error count")
                .register(meterRegistry);
        
        // 记录开始时间
        long startTime = System.nanoTime();
        
        try {
            // 执行方法
            Object result = joinPoint.proceed();
            
            // 增加调用计数
            counter.increment();
            
            // 记录执行时间
            long executionTime = System.nanoTime() - startTime;
            timer.record(executionTime, TimeUnit.NANOSECONDS);
            
            // 记录调用日志
            if (log.isDebugEnabled()) {
                log.debug("Method {} executed in {} ms", fullMethodName, executionTime / 1_000_000.0);
            }
            
            return result;
        } catch (Throwable t) {
            // 增加错误计数
            errorCounter.increment();
            
            // 记录错误日志
            log.error("Error executing method {}: {}", fullMethodName, t.getMessage());
            
            throw t;
        }
    }
} 