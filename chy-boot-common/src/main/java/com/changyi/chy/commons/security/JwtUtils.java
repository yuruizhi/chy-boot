package com.changyi.chy.commons.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

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

        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(now)
                .withExpiresAt(expirationDate)
                .withClaim("type", "access")
                .sign(algorithm);
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

        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(now)
                .withExpiresAt(expirationDate)
                .withClaim("type", "refresh")
                .sign(algorithm);
    }

    /**
     * 验证令牌
     *
     * @param token 令牌
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
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
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }
} 