package com.changyi.chy.commons.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线城池配置类.
 *
 * @author Henry.Yu
 * @date 2020.3.23
 */
@Slf4j
@Configuration
@EnableAsync
public class ExecutorConfig {

    /**
     * Set the ThreadPoolExecutor's core pool size
     */
    private final int CORE_POOL_SIZE = 5;

    /**
     * Set the ThreadPoolExecutor's maximum pool size
     */
    private final int MAX_POOL_SIZE = 200;

    /**
     * Set the capacity for the ThreadPoolExecutor's BlockingQueue.
     */
    private final int QUEUE_CAPACITY = 200;

    @Bean("callBackTaskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("callBackTaskExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    @Bean
    public Executor eventReportExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(CORE_POOL_SIZE);
        //配置最大线程数
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        //配置队列大小
        executor.setQueueCapacity(QUEUE_CAPACITY);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("event-report-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }
}
