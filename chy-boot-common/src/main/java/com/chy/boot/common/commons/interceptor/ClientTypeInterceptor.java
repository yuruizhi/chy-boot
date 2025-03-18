package com.chy.boot.common.commons.interceptor;

import com.chy.boot.common.commons.constants.ClientConstants;
import com.chy.boot.common.commons.context.ClientContext;
import com.chy.boot.common.commons.enums.ClientType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 客户端类型拦截器
 * 用于自动识别请求的客户端类型
 *
 * @author YuRuizhi
 * @date 2024/3/18
 */
@Slf4j
public class ClientTypeInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 优先从请求头获取客户端类型
        String clientTypeHeader = request.getHeader(ClientConstants.CLIENT_TYPE_HEADER);
        if (clientTypeHeader != null && !clientTypeHeader.isEmpty()) {
            ClientType clientType = ClientType.getByCode(clientTypeHeader);
            ClientContext.setClientType(clientType);
            log.debug("从请求头识别客户端类型: {}", clientType);
            return true;
        }
        
        // 2. 从请求路径推断客户端类型
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith(ClientConstants.PATH_PREFIX_WEB_ADMIN)) {
            ClientContext.setClientType(ClientType.WEB_ADMIN);
            log.debug("从请求路径识别为管理后台");
        } else if (requestURI.startsWith(ClientConstants.PATH_PREFIX_MOBILE_APP)) {
            ClientContext.setClientType(ClientType.MOBILE_APP);
            log.debug("从请求路径识别为移动应用");
        } else if (requestURI.startsWith(ClientConstants.PATH_PREFIX_MINI_PROGRAM)) {
            ClientContext.setClientType(ClientType.MINI_PROGRAM);
            log.debug("从请求路径识别为小程序");
        } else if (requestURI.startsWith(ClientConstants.PATH_PREFIX_OPEN_API)) {
            ClientContext.setClientType(ClientType.OPEN_API);
            log.debug("从请求路径识别为开放API");
        } else {
            ClientContext.setClientType(ClientType.UNKNOWN);
            log.debug("无法识别客户端类型，设为UNKNOWN");
        }
        
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // 不做处理
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 清理上下文
        ClientContext.clear();
    }
} 