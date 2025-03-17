package com.chy.boot.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * 用户DTO（使用JDK 21 Record特性）
 * <p>
 * Record是JDK 21支持的不可变数据传输对象，自动提供了:
 * - 构造函数
 * - getter方法
 * - equals/hashCode方法
 * - toString方法
 * </p>
 *
 * @author YuRuizhi
 * @since 2024-03-17
 */
@Schema(description = "用户信息DTO")
public record UserDTO(
        @Schema(description = "用户ID")
        String id,
        
        @Schema(description = "用户名")
        String username,
        
        @Schema(description = "邮箱")
        String email,
        
        @Schema(description = "角色列表")
        String[] roles,
        
        @Schema(description = "创建时间")
        LocalDateTime createTime
) {
    /**
     * 创建一个简化版的用户DTO（仅包含ID和用户名）
     */
    public static UserDTO simple(String id, String username) {
        return new UserDTO(id, username, null, null, null);
    }
    
    /**
     * Record也支持紧凑构造函数
     */
    public UserDTO {
        // 可以在这里添加参数验证逻辑
        if (id != null && id.isEmpty()) {
            throw new IllegalArgumentException("ID不能为空字符串");
        }
        
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
    }
} 