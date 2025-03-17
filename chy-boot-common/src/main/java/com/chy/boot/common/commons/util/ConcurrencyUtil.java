package com.chy.boot.common.commons.util;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * JDK 21结构化并发工具类
 * <p>
 * 展示JDK 21的结构化并发(Structured Concurrency)特性
 * 利用虚拟线程和StructuredTaskScope进行高效并发处理
 * </p>
 *
 * @author YuRuizhi
 * @since 2024-03-17
 */
@Slf4j
public class ConcurrencyUtil {

    /**
     * 并行执行多个任务并等待所有任务完成
     * 使用StructuredTaskScope.ShutdownOnFailure策略，任一任务失败立即终止其他任务
     *
     * @param tasks 任务列表
     * @param <T> 任务结果类型
     * @return 任务结果列表
     * @throws Exception 如果任何任务执行失败
     */
    public static <T> List<T> executeAllOrFail(Collection<Supplier<T>> tasks) throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            // 提交所有任务 - 将Supplier转换为Callable
            List<StructuredTaskScope.Subtask<T>> subtasks = tasks.stream()
                    .map(supplier -> scope.fork(() -> supplier.get()))
                    .collect(Collectors.toList());
            
            // 等待所有任务完成或有任务失败
            scope.join();
            
            // 如果有任务失败，抛出异常
            scope.throwIfFailed(e -> new RuntimeException("任务执行失败", e));
            
            // 收集所有成功任务的结果
            return subtasks.stream()
                    .map(StructuredTaskScope.Subtask::get)
                    .collect(Collectors.toList());
        }
    }

    /**
     * 并行执行多个任务，使用超时控制，并且采用first-result策略
     * 使用StructuredTaskScope.ShutdownOnSuccess策略，第一个成功完成的任务会导致其他任务被取消
     *
     * @param tasks 任务列表
     * @param timeout 超时时间
     * @param <T> 任务结果类型
     * @return 第一个成功完成的任务结果
     * @throws Exception 如果所有任务都失败或超时
     */
    public static <T> T executeAnySuccess(Collection<Supplier<T>> tasks, Duration timeout) throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<T>()) {
            // 提交所有任务 - 将Supplier转换为Callable
            tasks.forEach(supplier -> scope.fork(() -> supplier.get()));
            
            // 等待第一个成功的任务或超时
            try {
                scope.joinUntil(java.time.Instant.now().plus(timeout));
            } catch (TimeoutException e) {
                log.warn("任务执行超时: {}", timeout);
                throw new RuntimeException("任务执行超时", e);
            }
            
            // 返回第一个成功的结果
            return scope.result();
        }
    }

    /**
     * 并行处理集合中的每个元素
     *
     * @param items 待处理元素集合
     * @param processor 处理函数
     * @param <T> 元素类型
     * @param <R> 结果类型
     * @return 处理结果列表
     * @throws Exception 如果任何处理失败
     */
    public static <T, R> List<R> processItems(Collection<T> items, Function<T, R> processor) throws Exception {
        Collection<Supplier<R>> tasks = items.stream()
                .map(item -> (Supplier<R>) () -> processor.apply(item))
                .collect(Collectors.toList());
        
        return executeAllOrFail(tasks);
    }
} 