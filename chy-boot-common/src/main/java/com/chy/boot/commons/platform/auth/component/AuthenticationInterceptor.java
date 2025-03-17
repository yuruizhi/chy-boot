package com.chy.boot.commons.platform.auth.component;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import com.chy.boot.common.commons.api.AuthResultCode;
import com.chy.boot.common.commons.context.ExecuteContext;
import com.chy.boot.common.commons.exception.AuthException;
import com.chy.boot.common.commons.platform.auth.component.PassToken;
import com.chy.boot.common.commons.util.StringUtil;
import com.chy.boot.commons.platform.auth.service.impl.JwtAuthService;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("x-access-token");// 从 http 请求头中取出 token

        if (StringUtil.isBlank(token)) {
            token = httpServletRequest.getParameter("access_token");
        }

        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        if (method.getName().equals("error")) {
            return true;
        }

        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解

        if (token == null) {
            throw new AuthException(AuthResultCode.TOKEN_NOT_FOUND);
        }
        // 获取 token 中的 user id
        String channelId;
        try {
            channelId = JWT.decode(token).getAudience().get(0);
            ExecuteContext.getContext().setChannel(channelId);
        } catch (JWTDecodeException j) {
            throw new AuthException(AuthResultCode.JWT_DECODE_ERROR);
        }

        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(JwtAuthService.key)).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new AuthException(AuthResultCode.JWT_VERIFICATION_ERROR);
        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }

}