package com.changyi.chy.security.auth.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.changyi.chy.common.auth.entity.AuthResponse;
import com.changyi.chy.common.auth.entity.User;
import com.changyi.chy.common.auth.service.AuthService;
import com.changyi.chy.common.auth.service.UserService;
import com.changyi.chy.common.exception.AuthException;
import com.changyi.chy.common.util.Func;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * JWT认证服务实现
 */
@Service
public class JwtAuthService implements AuthService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private static final String JWT_SECRET = "@@chang##yi$$zhao&&chu**rui@@";
    private static final String ISSUER = "chy-boot";
    private static final String USER_ID = "userId";
    private static final String USERNAME = "username";
    private static final String REAL_NAME = "realName";
    private static final long EXPIRATION_TIME = 2 * 60 * 60 * 1000; // 2小时
    private static final long REFRESH_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000; // 7天

    @Autowired
    private UserService userService;

    @Override
    public AuthResponse getToken(String username, String password) throws AuthException {
        User user = userService.validateUser(username, password);
        if (Func.isNull(user) || !user.getStatus().equals(1)) {
            throw new AuthException("用户名或密码错误");
        }
        return generateToken(user);
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) throws AuthException {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(JWT_SECRET))
                    .withIssuer(ISSUER)
                    .build()
                    .verify(refreshToken);
                    
            String userId = jwt.getClaim(USER_ID).asString();
            User user = userService.getUserById(userId);
            if (Func.isNull(user) || !user.getStatus().equals(1)) {
                throw new AuthException("用户不存在或已禁用");
            }
            return generateToken(user);
        } catch (JWTVerificationException e) {
            throw new AuthException("刷新令牌无效或已过期");
        }
    }

    @Override
    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(JWT_SECRET))
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    @Override
    public String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(JWT_SECRET))
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token);
            return jwt.getClaim(USER_ID).asString();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    /**
     * 生成JWT token
     *
     * @param user 用户
     * @return 认证响应
     */
    private AuthResponse generateToken(User user) {
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + EXPIRATION_TIME);
        Date refreshExpireTime = new Date(now.getTime() + REFRESH_EXPIRATION_TIME);
        
        // 生成访问令牌
        String accessToken = JWT.create()
                .withIssuer(ISSUER)
                .withClaim(USER_ID, user.getId())
                .withClaim(USERNAME, user.getUsername())
                .withClaim(REAL_NAME, user.getRealName())
                .withIssuedAt(now)
                .withExpiresAt(expireTime)
                .sign(Algorithm.HMAC256(JWT_SECRET));
                
        // 生成刷新令牌
        String refreshToken = JWT.create()
                .withIssuer(ISSUER)
                .withClaim(USER_ID, user.getId())
                .withIssuedAt(now)
                .withExpiresAt(refreshExpireTime)
                .sign(Algorithm.HMAC256(JWT_SECRET));
                
        return new AuthResponse()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .setTokenType("Bearer")
                .setExpireTime(expireTime)
                .setUserId(user.getId())
                .setUsername(user.getUsername())
                .setRealName(user.getRealName());
    }
}
