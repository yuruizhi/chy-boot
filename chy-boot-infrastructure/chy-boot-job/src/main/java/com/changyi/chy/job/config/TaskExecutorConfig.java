package com.changyi.chy.job.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 任务执行器配置
 *
 * @author YuRuizhi
 */
@Slf4j
@Configuration
@EnableAsync
public class TaskExecutorConfig implements AsyncConfigurer {

    /**
     * 配置默认的异步任务执行器
     *
     * @return 异步任务执行器
     */
    @Override
    @Bean("taskExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        
        // 核心线程数：线程池维护线程的最少数量
        executor.setCorePoolSize(5);
        // 最大线程数：线程池维护线程的最大数量
        executor.setMaxPoolSize(20);
        // 队列容量：缓存队列
        executor.setQueueCapacity(100);
        // 线程名前缀
        executor.setThreadNamePrefix("async-task-");
        // 线程空闲时间：允许的空闲时间（单位：秒）
        executor.setKeepAliveSeconds(60);
        // 拒绝策略：任务太多时的拒绝处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        
        // 执行初始化
        executor.initialize();
        
        log.info("创建异步任务执行器: corePoolSize={}, maxPoolSize={}, queueCapacity={}",
                executor.getCorePoolSize(), executor.getMaxPoolSize(), executor.getQueueCapacity());
        
        return executor;
    }

    /**
     * 异步任务异常处理器
     *
     * @return 异常处理器
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            log.error("====> 异步任务执行异常 - 方法[{}]，参数[{}]", method.getName(), objects);
            log.error("====> 异常信息", throwable);
        };
    }
} 