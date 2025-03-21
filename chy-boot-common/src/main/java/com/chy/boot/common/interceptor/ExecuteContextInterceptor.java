package com.chy.boot.common.interceptor;

import com.chy.boot.common.context.ExecuteContext;
import com.chy.boot.common.context.SpringContextUtil;
import com.chy.boot.common.getter.IContextInfoGetter;
import com.chy.boot.common.util.DateUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;



/**
 * web请求上下文处理
 *
 * @author three
 * @date 2016/5/18
 */
public class ExecuteContextInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(ExecuteContextInterceptor.class);

    /**
     * This implementation always returns {@code true}.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String contentPath = request.getContextPath();
        int contentPathLen = contentPath.length();
        contentPathLen = contentPathLen > 0 ? contentPathLen : 1;
        String requestUri = request.getRequestURI();
        String definedPath = requestUri.substring(contentPathLen);

        String[] pathNodes = definedPath.split("/");
        String urlPrefix = pathNodes[0];

        if (ExecuteContext.MEMBER_BASE_API_PREFIX.equalsIgnoreCase(urlPrefix)) {
            String brand = pathNodes[1];

            ExecuteContext.getContext()
                    .setContextInfoGetter(SpringContextUtil.getBean(IContextInfoGetter.class))
                    .setBrand(brand);
        }


        // 系统调用

        logger.info(String.format("用户请求接口: %s. 初始化执行上下文...", definedPath));

        return true;
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.debug("清空执行上下文...");
        slowRequestLog(request);
        ExecuteContext.removeContext();
    }

    /**
     * 慢请求记录
     *
     * @param request
     */
    private void slowRequestLog(HttpServletRequest request) {
        long executeTime = DateUtil.getCurrentTimeMills() - ExecuteContext.getContext().getStartTime();
        if (executeTime > 200) {
            String contentPath = request.getContextPath();
            int contentPathLen = contentPath.length();
            contentPathLen = contentPathLen > 0 ? contentPathLen : 1;
            String requestUri = request.getRequestURI();
            String definedPath = requestUri.substring(contentPathLen);
            logger.info("重要信息|慢REST: {} 接口执行时间太长 <{}> ms", definedPath, executeTime);
        }
    }


}
