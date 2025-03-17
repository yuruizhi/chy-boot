package com.chy.boot.common.commons.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * UserDetailsService实现类
 * 用于根据用户名加载用户信息
 * 这里为了示例，使用内存中的用户数据
 * 实际应用中应该从数据库加载
 *
 * @author YuRuizhi
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final Map<String, UserDetails> users = new HashMap<>();

    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        // 添加测试用户
        users.put("admin", User.builder()
                .username("admin")
                .password(passwordEncoder.encode("123456"))
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .build());
        users.put("user", User.builder()
                .username("user")
                .password(passwordEncoder.encode("123456"))
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .build());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = users.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }
        return user;
    }
} 