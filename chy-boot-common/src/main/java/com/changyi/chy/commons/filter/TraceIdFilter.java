package com.changyi.chy.commons.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * 请求追踪过滤器
 * 为每个请求生成并记录traceId
 *
 * @author YuRuizhi
 */
@Component
@Order(0)
public class TraceIdFilter extends OncePerRequestFilter {

    private static final String TRACE_ID = "traceId";
    private static final String REQUEST_ID_HEADER = "X-Request-Id";
    private static final String USER_ID = "userId";
    private static final String REQUEST_IP = "requestIp";
    private static final String REQUEST_PATH = "requestPath";
    private static final String REQUEST_METHOD = "requestMethod";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            setupMDC(request);
            response.setHeader(REQUEST_ID_HEADER, MDC.get(TRACE_ID));
            filterChain.doFilter(request, response);
        } finally {
            clearMDC();
        }
    }

    /**
     * 设置MDC上下文
     *
     * @param request HTTP请求
     */
    private void setupMDC(HttpServletRequest request) {
        // 获取请求中的traceId，如果没有则生成一个新的
        String traceId = request.getHeader(REQUEST_ID_HEADER);
        if (!StringUtils.hasText(traceId)) {
            traceId = generateTraceId();
        }
        MDC.put(TRACE_ID, traceId);
        
        // 记录请求IP
        String remoteAddr = request.getRemoteAddr();
        MDC.put(REQUEST_IP, remoteAddr);
        
        // 记录请求路径
        String requestURI = request.getRequestURI();
        MDC.put(REQUEST_PATH, requestURI);
        
        // 记录请求方法
        String method = request.getMethod();
        MDC.put(REQUEST_METHOD, method);
        
        // 如果有用户ID，也记录下来（假设从SecurityContext中获取）
        Object principal = request.getUserPrincipal();
        if (principal != null) {
            MDC.put(USER_ID, principal.toString());
        }
    }

    /**
     * 清理MDC上下文
     */
    private void clearMDC() {
        MDC.remove(TRACE_ID);
        MDC.remove(USER_ID);
        MDC.remove(REQUEST_IP);
        MDC.remove(REQUEST_PATH);
        MDC.remove(REQUEST_METHOD);
    }

    /**
     * 生成追踪ID
     *
     * @return 追踪ID
     */
    private String generateTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
} 