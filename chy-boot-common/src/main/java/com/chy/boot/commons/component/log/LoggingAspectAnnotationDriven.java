package com.chy.boot.commons.component.log;

import cn.hutool.core.util.StrUtil;
import com.chy.boot.commons.jackson.JsonUtil;
import com.chy.boot.commons.util.BeanUtil;
import com.chy.boot.commons.util.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基于注解方式记录日志.
 */
@Aspect
@Component
@Configuration
public class LoggingAspectAnnotationDriven {
    private static Logger logger = LoggerFactory.getLogger(LoggingAspectAnnotationDriven.class);

    @Before("@annotation(com.chy.boot.commons.component.log.LogStyle)")
    public void beforeHandler(JoinPoint joinPoint) throws NoSuchMethodException {
        LogStyle LogStyle = this.getLogger(joinPoint);
        String desc = LogStyle.beforeDesc();
        if (!desc.equals("")) {
            desc = "[" + LogStyle.version() + "]-" + "[" + LogStyle.tag() + "]-" + logDescFormat(desc, joinPoint);
            logger.info(desc);
        }
    }

    @AfterReturning(value = "@annotation(com.chy.boot.commons.component.log.LogStyle)", returning = "result")
    public void afterReturningHandler(JoinPoint joinPoint, Object result) throws NoSuchMethodException {
        LogStyle LogStyle = this.getLogger(joinPoint);
        String desc = LogStyle.afterDesc();
        if (!desc.equals("")) {
            desc = "[" + LogStyle.version() + "]-" + "[" + LogStyle.tag() + "]-" + desc;
            logger.info(desc, JsonUtil.object2EscapeJson(result));
            //logger.info(desc , result);
        }
    }

    private LogStyle getLogger(JoinPoint joinPoint) throws SecurityException, NoSuchMethodException {
        Method proxyMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Method sourceMethod = joinPoint.getTarget().getClass().getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());
        LogStyle LogStyle = sourceMethod.getAnnotation(LogStyle.class);
        if (LogStyle != null) {
            return LogStyle;
        }
        LogStyle = joinPoint.getTarget().getClass().getAnnotation(LogStyle.class);
        return LogStyle;
    }

    /**
     * 格式化日志描述
     *
     * @param desc
     * @param joinPoint
     * @return
     */
    private String logDescFormat(String desc, JoinPoint joinPoint) {
        if (StrUtil.isNotBlank(desc)) {
            String reg = "\\{[^\\}]*\\}";

            Pattern pa = Pattern.compile(reg);
            Matcher matcher = pa.matcher(desc);
            while (matcher.find()) {
                String oldStr = matcher.group();
                String str = oldStr.replace("{", "").replace("}", "");
                try {
                    String regNum = "\\d";
                    // 匹配规则中是否是数字例如{0},{1}
                    if (str.matches(regNum)) {
                        // TODO to xx 判断数组越界
                        int i = Integer.parseInt(str);
                        if (joinPoint.getArgs().length > i) {
                            String className = String.valueOf(joinPoint.getArgs()[i]);

                            // 如果是bean对象,需要转换json
                            if (className.indexOf("com.changyi") != -1) {
                                Object obj = joinPoint.getArgs()[Integer.parseInt(str)];
                                desc = desc.replace(oldStr, JsonUtil.object2Json(obj));
                                continue;
                            }
                            // 如果是占位符
                            if (StringUtil.isInteger(str)) {
                                desc = desc.replace(oldStr, String.valueOf(joinPoint.getArgs()[Integer.parseInt(str)]));
                            }
                            // 其它参数暂不处理
                        }
                    } else {
                        // 匹配bean属性参数例如{name}
                        Object obj = joinPoint.getArgs()[0];
                        Object val = BeanUtil.getProperty(obj, str);
                        desc = desc.replace(oldStr, String.valueOf(val));
                    }
                } catch (Exception e) {
                    logger.info("日志格式化错误", e);
                }
            }
        }

        return desc;
    }

}
