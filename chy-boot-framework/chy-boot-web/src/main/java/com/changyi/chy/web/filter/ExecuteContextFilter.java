package com.changyi.chy.commons.filter;


import cn.hutool.core.util.StrUtil;
import com.changyi.chy.commons.context.ExecuteContext;
import com.changyi.chy.commons.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 统一的Web请求上下文处理过滤器
 * 处理请求上下文、链路ID追踪和MDC日志上下文
 *
 * @author YuRuizhi (based on original work by three)
 * @date 2023/06/10
 */
@Component
@Order(0)
@ConditionalOnProperty(name = "web.execute.context.enabled", havingValue = "true", matchIfMissing = true)
public class ExecuteContextFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(ExecuteContextFilter.class);

    // MDC常量
    private static final String REQUEST_ID = "requestId";
    private static final String REQUEST_ID_HEADER = "X-Request-Id";
    private static final String USER_ID = "userId";
    private static final String REQUEST_IP = "requestIp";
    private static final String REQUEST_PATH = "requestPath";
    private static final String REQUEST_METHOD = "requestMethod";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化逻辑（如果需要）
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            // 处理请求ID
            setupRequestId(request, response);
            
            // 设置MDC上下文
            setupMDC(request);
            
            // 处理Context Path
            setupContextPath(request);

            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            ExecuteContext.removeContext();
            clearMDC();
        }
    }

    /**
     * 设置请求ID
     * 统一处理来自不同来源的requestId：
     * 1. 从请求头获取
     * 2. 从URL参数获取
     * 3. 如果都没有，生成新的
     */
    private void setupRequestId(HttpServletRequest request, HttpServletResponse response) {
        String requestId = request.getHeader(REQUEST_ID_HEADER);
        
        // 兼容小写header名称
        if (StrUtil.isBlank(requestId)) {
            requestId = request.getHeader(REQUEST_ID_HEADER.toLowerCase());
        }
        
        // 尝试从URL参数获取
        if (StrUtil.isBlank(requestId)) {
            requestId = request.getParameter("request_id");
        }
        
        // 如果仍然为空，生成新的ID
        if (StrUtil.isBlank(requestId)) {
            requestId = UUIDGenerator.generate();
        }

        // 设置响应头，便于客户端跟踪
        response.setHeader(REQUEST_ID_HEADER, requestId);
        
        // 将requestId放入MDC
        MDC.put(REQUEST_ID, requestId);
        
        if (!isExcludedUri(request.getRequestURI())) {
            logger.debug("请求ID: {}", requestId);
        }
    }
    
    /**
     * 设置MDC上下文
     * 记录各种请求信息到MDC中，便于日志分析
     */
    private void setupMDC(HttpServletRequest request) {
        // 记录请求IP
        String remoteAddr = request.getRemoteAddr();
        MDC.put(REQUEST_IP, remoteAddr);
        
        // 记录请求路径
        String requestURI = request.getRequestURI();
        MDC.put(REQUEST_PATH, requestURI);
        
        // 记录请求方法
        String method = request.getMethod();
        MDC.put(REQUEST_METHOD, method);
        
        // 如果有用户ID，也记录下来（从SecurityContext中获取）
        Object principal = request.getUserPrincipal();
        if (principal != null) {
            MDC.put(USER_ID, principal.toString());
        }
    }

    /**
     * 设置Context Path
     * 处理请求路径，初始化执行上下文
     */
    private void setupContextPath(HttpServletRequest request) {
        String contentPath = request.getContextPath();
        int contentPathLen = contentPath.length();
        contentPathLen = contentPathLen > 0 ? contentPathLen : 1;
        String requestUri = request.getRequestURI();
        String definedPath = requestUri.substring(contentPathLen);

        String[] pathNodes = definedPath.split("/");
        String urlPrefix = pathNodes.length > 0 ? pathNodes[0] : "";

        // 初始化上下文，通过getContext方法创建带有requestId的上下文
        String requestId = MDC.get(REQUEST_ID);
        ExecuteContext.getContext(requestId);
        
        if (!isExcludedUri(definedPath)) {
            logger.info("用户请求: {}. 初始化执行上下文...", definedPath);
        }
    }
    
    /**
     * 清理MDC上下文
     * 确保每个请求结束后清理MDC，避免信息泄漏到其他请求
     */
    private void clearMDC() {
        MDC.remove(REQUEST_ID);
        MDC.remove(USER_ID);
        MDC.remove(REQUEST_IP);
        MDC.remove(REQUEST_PATH);
        MDC.remove(REQUEST_METHOD);
    }

    @Override
    public void destroy() {
        ExecuteContext.removeContext();
    }

    /**
     * 判断URI是否排除日志记录
     * 对于某些频繁访问且信息量不大的URI，可以排除日志记录
     */
    private boolean isExcludedUri(String uri) {
        if (uri == null) {
            return false;
        }
        // 排除健康检查等高频请求
        if (uri.contains("actuator/health") || 
            uri.contains("ping") || 
            uri.contains("favicon.ico")) {
            return true;
        }
        return false;
    }
}
