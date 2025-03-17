package com.chy.boot.common.commons.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 * 用于生成和验证JWT令牌
 *
 * @author YuRuizhi
 */
@Component
public class JwtUtils {

    /**
     * 密钥
     */
    @Value("${jwt.secret:chybootsecretkey}")
    private String secret;

    /**
     * 过期时间（毫秒）
     */
    @Value("${jwt.expiration:86400000}")
    private long expiration;

    /**
     * 刷新令牌过期时间（毫秒）
     */
    @Value("${jwt.refresh-expiration:604800000}")
    private long refreshExpiration;

    /**
     * 生成令牌
     *
     * @param userDetails 用户详情
     * @return 令牌
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * 生成刷新令牌
     *
     * @param userDetails 用户详情
     * @return 刷新令牌
     */
    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createRefreshToken(claims, userDetails.getUsername());
    }

    /**
     * 创建令牌
     *
     * @param claims 数据声明
     * @param subject 主题
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .claim("type", "access")
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * 创建刷新令牌
     *
     * @param claims 数据声明
     * @param subject 主题
     * @return 刷新令牌
     */
    private String createRefreshToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + refreshExpiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .claim("type", "refresh")
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * 获取签名密钥
     *
     * @return 签名密钥
     */
    private Key getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 验证令牌
     *
     * @param token 令牌
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | ExpiredJwtException |
                 UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * 获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (SecurityException | MalformedJwtException | ExpiredJwtException |
                 UnsupportedJwtException | IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * 获取令牌的过期时间（毫秒时间戳）
     *
     * @param token 令牌
     * @return 过期时间的毫秒时间戳
     * @throws ExpiredJwtException 如果令牌已过期
     */
    public long getExpirationTimeFromToken(String token) throws ExpiredJwtException {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration().getTime();
        } catch (ExpiredJwtException e) {
            // 即使令牌过期，我们仍然可以从异常中获取过期时间
            return e.getClaims().getExpiration().getTime();
        } catch (Exception e) {
            throw new IllegalArgumentException("无效的JWT令牌", e);
        }
    }
} 