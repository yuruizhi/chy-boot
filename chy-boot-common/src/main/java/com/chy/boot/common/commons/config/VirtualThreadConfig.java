package com.chy.boot.common.commons.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 虚拟线程配置
 * <p>
 * JDK 21提供的虚拟线程使得可以有效地处理大量并发任务
 * 虚拟线程是轻量级线程，与平台线程不同，它们不会直接映射到操作系统线程
 * </p>
 *
 * @author YuRuizhi
 * @since 2024-03-17
 */
@Configuration
@EnableAsync
@ConditionalOnProperty(name = "app.virtual-threads.enabled", havingValue = "true", matchIfMissing = true)
public class VirtualThreadConfig {

    /**
     * 配置Tomcat使用虚拟线程处理请求
     */
    @Bean
    public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
        return protocolHandler -> {
            protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        };
    }

    /**
     * 配置全局异步任务使用虚拟线程
     */
    @Bean
    public AsyncTaskExecutor applicationTaskExecutor() {
        return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
    }
    
    /**
     * 配置IO密集型任务的专用执行器
     */
    @Bean(name = "ioTaskExecutor")
    public Executor ioTaskExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
    
    /**
     * 配置Spring定时任务使用虚拟线程
     */
    @Bean(name = "taskScheduler")
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(Runtime.getRuntime().availableProcessors());
        scheduler.setThreadNamePrefix("scheduled-task-");
        return scheduler;
    }
} 