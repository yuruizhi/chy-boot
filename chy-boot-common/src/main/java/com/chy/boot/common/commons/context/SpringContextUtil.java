package com.chy.boot.common.commons.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * 获得spring上下文
 * Created by zsanything on 16/5/17.
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context = null;

    private static Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        SpringContextUtil.environment = environment;
    }

    public static Environment getEnvironment() {
        return environment;
    }

    public static String getProperty(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }

    public static String getProperty(String key) {
        return environment.getProperty(key);
    }

    /**
     * 是否开发模式, 开发模式支付金额为 1 分.
     *
     * @return
     */
    public static boolean isDevMode() {
        String mode = getProperty("pay.isDevMode");
        Boolean result = false;
        if ("true".equals(mode)) {
            result = true;
        }
        return result;
    }

    public void setApplicationContext(ApplicationContext newContext) throws BeansException {
        SpringContextUtil.setContext(newContext);
    }

    private static void setContext(ApplicationContext newContext) {
        if (context == null) {
            context = newContext;
        }
    }

    public static Object getBean(String beanName) throws BeansException {
        return context.getBean(beanName);
    }

    public static <T> T getBean(Class<T> c) throws BeansException {
        return context.getBean(c);
    }

    public static <T> T getBean(String beanId, Class<T> clazz) throws BeansException {
        return context.getBean(beanId, clazz);
    }


    /**
     * 根据错误code得到错误明细
     *
     * @param code        错误代码
     * @param params      参数
     * @param defaultDesc 默认描述
     * @param local       本地语言环境
     * @return
     */
    public static String getMessage(String code, Object[] params, String defaultDesc, Locale local) {
        return context.getMessage(code, params, defaultDesc, local);
    }

    /**
     * 根据错误code得到错误明细
     *
     * @param code   错误代码
     * @param params 参数
     * @param local  本地语言环境
     * @return
     */
    public static String getMessage(String code, Object[] params, Locale local) {
        return context.getMessage(code, params, local);
    }

    /**
     * 获取 HttpServletRequest 对象
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = null;
        if (null != RequestContextHolder.getRequestAttributes()) {
            request = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();
        }
        return request;
    }

    /**
     * 获取本地语言环境
     *
     * @param request
     * @return
     */
    public static Locale getLocale(HttpServletRequest request) {
        return RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
    }
}
