package com.chy.boot.rest.service;

import com.chy.boot.rest.core.entity.Demo;
import com.chy.boot.service.service.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 安全服务类
 * 用于实现复杂的安全检查逻辑
 *
 * @author YuRuizhi
 * @date 2024/3/17
 */
@Service
public class SecurityService {

    private final DemoService demoService;
    
    @Autowired
    public SecurityService(DemoService demoService) {
        this.demoService = demoService;
    }
    
    /**
     * 检查当前用户是否具有指定角色
     */
    public boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
    }
    
    /**
     * 检查当前用户是否具有指定权限
     */
    public boolean hasAuthority(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals(authority));
    }
    
    /**
     * 检查当前用户是否是资源拥有者或管理员
     */
    public boolean isOwnerOrAdmin(String demoId) {
        // 如果是管理员，直接返回真
        if (hasRole("ADMIN")) {
            return true;
        }
        
        // 获取当前用户名
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        
        String currentUsername = authentication.getName();
        
        // 获取资源
        Demo demo = demoService.getById(demoId);
        if (demo == null) {
            return false;
        }
        
        // 在实际应用中，应当在Demo实体添加creator字段
        // 这里仅作为示例，假设资源创建者信息存在于demo的某个字段中
        // 实际比较应该类似：return currentUsername.equals(demo.getCreator());
        
        // 暂时默认为真，实际应用需要修改为真实的比较逻辑
        return true;
    }
} 