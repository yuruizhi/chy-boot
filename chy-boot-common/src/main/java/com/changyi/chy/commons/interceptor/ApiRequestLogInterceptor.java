package com.changyi.chy.commons.interceptor;

import com.changyi.chy.commons.context.ExecuteContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 接口请求日志拦截器.
 *
 * @author three
 * @date 2016/5/16
 */
public class ApiRequestLogInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.debug("清空执行上下文...");
        ExecuteContext.removeContext();
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("情况执行上下文...");
        ExecuteContext.removeContext();
    }
}
