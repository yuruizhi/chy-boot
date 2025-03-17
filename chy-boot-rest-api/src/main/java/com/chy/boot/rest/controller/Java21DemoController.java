package com.chy.boot.rest.controller;

import com.chy.boot.common.commons.api.R;
import com.chy.boot.common.commons.util.ConcurrencyUtil;
import com.chy.boot.common.commons.util.PatternMatchUtil;
import com.chy.boot.rest.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * Java 21新特性演示控制器
 *
 * @author YuRuizhi
 * @since 2024-03-17
 */
@Slf4j
@Tag(name = "Java 21特性", description = "展示JDK 21的新特性")
@RestController
@RequestMapping("api/v1/java21")
public class Java21DemoController {
    
    /**
     * 演示Record类型
     */
    @Operation(summary = "演示Record类型", description = "展示JDK 21的Record类型特性")
    @GetMapping("/record")
    public R<UserDTO> demoRecord() {
        UserDTO user = new UserDTO(
                "1001",
                "zhangsan",
                "zhangsan@example.com",
                new String[]{"ROLE_USER", "ROLE_ADMIN"},
                LocalDateTime.now()
        );
        
        log.info("创建Record类型用户: {}", user);
        return R.data(user);
    }
    
    /**
     * 演示模式匹配
     */
    @Operation(summary = "演示模式匹配", description = "展示JDK 21的模式匹配特性")
    @PostMapping("/pattern-matching")
    public R<String> demoPatternMatching(
            @Parameter(description = "任意类型值") @RequestBody Object value) {
        
        String result = PatternMatchUtil.formatValue(value);
        log.info("模式匹配格式化结果: {}", result);
        
        return R.data(result, "模式匹配处理成功");
    }
    
    /**
     * 演示字符串模板
     */
    @Operation(summary = "演示字符串处理", description = "展示JDK 21的字符串处理特性")
    @GetMapping("/string-template")
    public R<String> demoStringTemplate(
            @Parameter(description = "名称") @RequestParam String name,
            @Parameter(description = "年龄") @RequestParam int age) {
        
        // 使用Java文本块功能，这是Java 15中正式发布的特性
        String message = """
            用户信息:
            - 名称: %s
            - 年龄: %d
            - 是否成年: %s
            - 当前时间: %s
            """.formatted(
                name,
                age,
                age >= 18 ? "是" : "否",
                LocalDateTime.now()
            );
        
        log.info("字符串处理结果: {}", message);
        return R.data(message, "字符串处理成功");
    }
    
    /**
     * 演示虚拟线程和结构化并发
     */
    @Operation(summary = "演示虚拟线程和结构化并发", description = "展示JDK 21的虚拟线程和结构化并发特性")
    @GetMapping("/virtual-threads")
    public R<List<String>> demoVirtualThreads() throws Exception {
        List<Integer> ids = List.of(1, 2, 3, 4, 5);
        
        // 使用虚拟线程并行处理多个任务
        List<String> results = ConcurrencyUtil.processItems(ids, id -> {
            // 模拟耗时操作
            try {
                Thread.sleep(200);
                log.info("处理ID: {} 在线程: {}", id, Thread.currentThread());
                return "处理结果-" + id;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("处理中断", e);
            }
        });
        
        return R.data(results, "虚拟线程处理成功");
    }
    
    /**
     * 演示异步处理
     */
    @Operation(summary = "演示异步处理", description = "展示基于JDK 21虚拟线程的异步处理")
    @GetMapping("/async")
    public R<String> demoAsync() {
        // 启动10个异步任务
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            CompletableFuture.runAsync(() -> {
                log.info("异步任务 {} 开始在线程: {}", taskId, Thread.currentThread());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                log.info("异步任务 {} 完成", taskId);
            });
        }
        
        return R.success("异步任务已提交");
    }
    
    /**
     * 演示第一个成功任务
     */
    @Operation(summary = "演示任务竞争", description = "展示JDK 21的StructuredTaskScope ShutdownOnSuccess特性")
    @GetMapping("/first-success")
    public R<String> demoFirstSuccess() throws Exception {
        List<Supplier<String>> tasks = List.of(
            () -> {
                sleep(300);
                return "服务A的结果";
            },
            () -> {
                sleep(500);
                return "服务B的结果";
            },
            () -> {
                sleep(200);
                return "服务C的结果 - 最快";
            }
        );
        
        // 获取最快成功的任务结果
        String result = ConcurrencyUtil.executeAnySuccess(tasks, Duration.ofSeconds(1));
        return R.data(result, "获取最快成功的任务");
    }
    
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
} 