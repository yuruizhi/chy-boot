package com.chy.boot.rest.controller;

import com.chy.boot.commons.api.R;
import com.chy.boot.commons.security.JwtBlacklistService;
import com.chy.boot.commons.security.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.HashMap;
import java.util.Map;

/**
 * 身份认证控制器
 * 提供登录、刷新令牌等接口
 *
 * @author YuRuizhi
 */
@Tag(name = "身份认证")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final JwtBlacklistService blacklistService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, JwtBlacklistService blacklistService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.blacklistService = blacklistService;
    }

    /**
     * 登录请求
     */
    public static class LoginRequest {
        @NotBlank(message = "用户名不能为空")
        private String username;

        @NotBlank(message = "密码不能为空")
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    /**
     * 登录
     *
     * @param loginRequest 登录请求
     * @return 登录结果
     */
    @Operation(summary = "登录")
    @PostMapping("/login")
    public R<Map<String, String>> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String accessToken = jwtUtils.generateToken(userDetails);
        String refreshToken = jwtUtils.generateRefreshToken(userDetails);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        tokens.put("token_type", "Bearer");

        return R.data(tokens);
    }

    /**
     * 刷新令牌请求
     */
    public static class RefreshTokenRequest {
        @NotBlank(message = "刷新令牌不能为空")
        private String refreshToken;

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }

    /**
     * 刷新令牌
     *
     * @param request 刷新令牌请求
     * @return 刷新结果
     */
    @Operation(summary = "刷新令牌")
    @PostMapping("/refresh")
    public R<Map<String, String>> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        if (!jwtUtils.validateToken(request.getRefreshToken())) {
            return R.fail(401, "刷新令牌无效或已过期");
        }

        String username = jwtUtils.getUsernameFromToken(request.getRefreshToken());
        if (username == null) {
            return R.fail(401, "无法从刷新令牌中获取用户信息");
        }

        // 这里简化处理，实际应该从用户服务获取用户详情
        org.springframework.security.core.userdetails.User userDetails = 
                new org.springframework.security.core.userdetails.User(
                        username, "", java.util.Collections.emptyList());

        String newAccessToken = jwtUtils.generateToken(userDetails);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", newAccessToken);
        tokens.put("refresh_token", request.getRefreshToken());
        tokens.put("token_type", "Bearer");

        return R.data(tokens);
    }
    
    /**
     * 登出
     *
     * @param request HTTP请求
     * @return 登出结果
     */
    @Operation(summary = "登出")
    @PostMapping("/logout")
    public R<Void> logout(HttpServletRequest request) {
        String jwt = extractTokenFromRequest(request);
        if (StringUtils.hasText(jwt)) {
            // 将当前令牌加入黑名单
            blacklistService.addToBlacklist(jwt);
            
            // 清除安全上下文
            SecurityContextHolder.clearContext();
        }
        return R.success("登出成功");
    }
    
    /**
     * 从请求中提取令牌
     *
     * @param request HTTP请求
     * @return 令牌
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
} 