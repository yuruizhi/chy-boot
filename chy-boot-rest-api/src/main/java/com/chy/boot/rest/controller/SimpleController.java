package com.chy.boot.rest.controller;

import com.chy.boot.common.api.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述: 简单示例控制器
 *
 * @author Henry.Yu
 * @date 2023/09/05
 */
@Slf4j
@RestController
@RequestMapping("/api/simple")
@Tag(name = "简单示例接口", description = "提供基本的REST API示例")
public class SimpleController {

    /**
     * 简单的问候接口
     */
    @GetMapping("/hello")
    @Operation(summary = "问候接口", description = "返回一个简单的问候消息")
    public R<String> hello() {
        log.info("访问hello接口");
        return R.data("Hello, Spring Boot 3!");
    }

    /**
     * 获取系统信息
     */
    @GetMapping("/info")
    @Operation(summary = "系统信息", description = "返回系统相关信息")
    public R<Map<String, Object>> info() {
        log.info("访问info接口");
        Map<String, Object> info = new HashMap<>();
        info.put("app", "chy-boot");
        info.put("version", "1.0.0");
        info.put("java", System.getProperty("java.version"));
        info.put("os", System.getProperty("os.name"));
        return R.data(info);
    }

    /**
     * 回显测试
     */
    @GetMapping("/echo")
    @Operation(summary = "回显测试", description = "回显输入的参数")
    public R<String> echo(@RequestParam(required = false, defaultValue = "Hello World") String message) {
        log.info("访问echo接口，参数: {}", message);
        return R.data("Echo: " + message);
    }
} 