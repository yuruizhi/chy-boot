package com.chy.boot.common.commons.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 异步任务配置
 * <p>
 * 使用JDK 21的虚拟线程作为异步任务的执行器，极大提高并发处理能力
 * </p>
 *
 * @author YuRuizhi
 * @date 2024/3/17
 */
@Slf4j
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    /**
     * 配置默认的异步任务执行器
     * 使用虚拟线程替代传统的线程池，无需设置线程数量上限
     */
    @Override
    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() {
        log.info("初始化虚拟线程异步任务执行器");
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    /**
     * 配置异步任务异常处理器
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }

    /**
     * 自定义异步任务异常处理器
     */
    static class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        @Override
        public void handleUncaughtException(Throwable ex, Method method, Object... params) {
            log.error("异步任务执行异常: 方法: [{}], 参数: [{}], 异常: [{}]",
                    method.getName(),
                    params,
                    ex.getMessage());
            log.error("异步任务异常详情", ex);
        }
    }
} 