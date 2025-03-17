package com.chy.boot.rest.controller;

import com.chy.boot.common.commons.api.R;
import com.chy.boot.common.commons.idempotent.annotation.Idempotent;
import com.chy.boot.common.commons.idempotent.service.IdempotentTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 幂等性控制器
 * 提供幂等性Token接口
 *
 * @author YuRuizhi
 */
@Tag(name = "幂等性接口")
@RestController
@RequestMapping("/api/idempotent")
public class IdempotentController {

    @Autowired
    private IdempotentTokenService idempotentTokenService;

    /**
     * 获取幂等性Token
     *
     * @return Token
     */
    @Operation(summary = "获取幂等性Token")
    @GetMapping("/token")
    public R<Map<String, String>> getToken() {
        String token = idempotentTokenService.createToken("api", 5, TimeUnit.MINUTES);
        Map<String, String> result = new HashMap<>();
        result.put("token", token);
        return R.data(result);
    }

    /**
     * 幂等性测试接口
     *
     * @return 结果
     */
    @Operation(summary = "幂等性测试接口")
    @Idempotent(prefix = "test", timeout = 5, timeUnit = TimeUnit.MINUTES)
    @PostMapping("/test")
    public R<Map<String, Object>> test(@RequestBody(required = false) Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("timestamp", System.currentTimeMillis());
        if (params != null) {
            result.put("params", params);
        }
        return R.data(result);
    }
} 