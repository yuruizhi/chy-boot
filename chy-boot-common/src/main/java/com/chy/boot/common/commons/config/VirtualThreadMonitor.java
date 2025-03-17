package com.chy.boot.common.commons.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

/**
 * 虚拟线程监控组件
 * <p>
 * 用于监控Java 21虚拟线程的执行情况
 * </p>
 *
 * @author YuRuizhi
 * @since 2024-03-17
 */
@Component
@Slf4j
@ConditionalOnProperty(name = "app.virtual-threads.monitoring.enabled", havingValue = "true", matchIfMissing = false)
public class VirtualThreadMonitor {

    private final AtomicLong activeVirtualThreads = new AtomicLong(0);
    private final AtomicLong completedVirtualThreadTasks = new AtomicLong(0);
    private final AtomicLong failedVirtualThreadTasks = new AtomicLong(0);
    
    @PostConstruct
    public void init() {
        log.info("虚拟线程监控组件已初始化");
        
        // 每30秒记录一次虚拟线程统计信息
        Thread.startVirtualThread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(30000);
                    logVirtualThreadStats();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }
    
    /**
     * 执行任务并跟踪虚拟线程统计信息
     */
    public <T> T trackVirtualThreadExecution(Supplier<T> task, String taskName) {
        long startTime = System.currentTimeMillis();
        activeVirtualThreads.incrementAndGet();
        
        try {
            T result = task.get();
            completedVirtualThreadTasks.incrementAndGet();
            
            long executionTime = System.currentTimeMillis() - startTime;
            log.debug("虚拟线程任务 [{}] 执行完成，耗时: {}ms", taskName, executionTime);
            
            return result;
        } catch (Exception e) {
            failedVirtualThreadTasks.incrementAndGet();
            log.error("虚拟线程任务 [{}] 执行失败", taskName, e);
            throw e;
        } finally {
            activeVirtualThreads.decrementAndGet();
        }
    }
    
    /**
     * 执行Runnable任务并跟踪虚拟线程统计信息
     */
    public void trackVirtualThreadExecution(Runnable task, String taskName) {
        trackVirtualThreadExecution(() -> {
            task.run();
            return null;
        }, taskName);
    }
    
    /**
     * 获取活跃虚拟线程数量
     */
    public long getActiveVirtualThreadCount() {
        return activeVirtualThreads.get();
    }
    
    /**
     * 获取已完成的虚拟线程任务数
     */
    public long getCompletedVirtualThreadTaskCount() {
        return completedVirtualThreadTasks.get();
    }
    
    /**
     * 获取失败的虚拟线程任务数
     */
    public long getFailedVirtualThreadTaskCount() {
        return failedVirtualThreadTasks.get();
    }
    
    /**
     * 记录虚拟线程统计信息
     */
    private void logVirtualThreadStats() {
        long totalJvmThreads = Thread.getAllStackTraces().keySet().stream()
                .filter(Thread::isVirtual)
                .count();
        
        log.info("虚拟线程统计 - JVM总虚拟线程: {}, 活跃任务: {}, 已完成: {}, 失败: {}", 
                totalJvmThreads, 
                activeVirtualThreads.get(),
                completedVirtualThreadTasks.get(),
                failedVirtualThreadTasks.get());
    }
} 