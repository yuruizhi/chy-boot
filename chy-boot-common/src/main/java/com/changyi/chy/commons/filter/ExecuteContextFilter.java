package com.changyi.chy.commons.filter;


import cn.hutool.core.util.StrUtil;
import com.changyi.chy.commons.context.ExecuteContext;
import com.changyi.chy.commons.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * web请求上下文处理.
 *
 * @author three
 * @date 2016/5/18
 */
@Component
@ConditionalOnProperty(name = "web.execute.context.enabled")
public class ExecuteContextFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(ExecuteContextFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String requestId = request.getHeader("x-request-id");
        if (StrUtil.isBlank(requestId)) {
            requestId = request.getParameter("request_id");
        }
        if (StrUtil.isBlank(requestId)) {
            requestId = UUIDGenerator.generate();
        }

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("x-request-id", requestId);

        String contentPath = request.getContextPath();
        int contentPathLen = contentPath.length();
        contentPathLen = contentPathLen > 0 ? contentPathLen : 1;
        String requestUri = request.getRequestURI();
        String definedPath = requestUri.substring(contentPathLen);

        String[] pathNodes = definedPath.split("/");
        String urlPrefix = pathNodes[0];

        try {

            if (!isExcludedUri(definedPath)) {
                logger.info(String.format("用户请求: %s. 初始化执行上下文...", definedPath));
            }

            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            ExecuteContext.removeContext();
        }
    }

    @Override
    public void destroy() {
        ExecuteContext.removeContext();
    }

    private boolean isExcludedUri(String uri) {
        if ("actuator/health".equals(uri)) {
            return true;
        }
        return false;
    }
}
