package com.changyi.chy.security.auth.controller;

import com.changyi.chy.common.api.R;
import com.changyi.chy.common.auth.entity.AuthRequest;
import com.changyi.chy.common.auth.entity.AuthResponse;
import com.changyi.chy.common.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "认证管理", description = "用户认证、获取token、刷新token等操作")
public class JwtController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthService authService;

    @Operation(summary = "获取token", description = "使用用户名密码获取认证token")
    @PostMapping("/token")
    public R<AuthResponse> getToken(@RequestBody @Validated AuthRequest authRequest) {
        logger.info("用户登录: {}", authRequest.getUsername());
        return R.data(authService.getToken(authRequest.getUsername(), authRequest.getPassword()));
    }

    @Operation(summary = "刷新token", description = "使用刷新token获取新的访问token")
    @PostMapping("/refresh")
    public R<AuthResponse> refreshToken(@RequestParam String refreshToken) {
        return R.data(authService.refreshToken(refreshToken));
    }

    @Operation(summary = "验证token", description = "验证token是否有效")
    @GetMapping("/validate")
    public R<Boolean> validateToken(@RequestParam String token) {
        return R.data(authService.validateToken(token));
    }
}
