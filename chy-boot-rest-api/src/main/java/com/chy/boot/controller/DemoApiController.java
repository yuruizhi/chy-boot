package com.chy.boot.controller;

import com.chy.boot.common.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 演示API控制器
 *
 * @auther Henry.Yu
 * @date 2025/03/21
 */
@Slf4j
@RestController
@RequestMapping("/api/simple")
public class DemoApiController {

    /**
     * 简单的hello接口
     */
    @GetMapping("/hello")
    public R<String> hello() {
        log.info("调用了hello接口");
        return R.data("Hello World!");
    }

    /**
     * 返回系统信息
     */
    @GetMapping("/info")
    public R<String> info() {
        log.info("调用了info接口");
        return R.data("系统版本: v1.0.0");
    }

    /**
     * 回显输入信息
     */
    @PostMapping("/echo")
    public R<String> echo(@RequestBody(required = false) String message) {
        log.info("调用了echo接口, 参数: {}", message);
        return R.data("您发送的消息是: " + message);
    }
} 