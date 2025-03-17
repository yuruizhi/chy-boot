package com.chy.boot.commons.component.log;

import org.slf4j.Logger;

/**
 * 日志格式输出格式化, 自己不输出任何日志信息.
 */
public abstract class LoggerFormat {

    /**
     * 日志输出格式化,实现使用String类中的format方法
     * {@link String#format(String format, Object... args)}
     *
     * @param logger
     * @param format
     * @param args
     * @return
     */
    public static String info(Logger logger, String format, Object... args) {
        if (logger.isInfoEnabled()) {
            return String.format(format, args);
        }
        return "";
    }

    public static String debug(Logger logger, String format, Object... args) {
        if (logger.isDebugEnabled()) {
            return String.format(format, args);
        }
        return "";
    }

    public static String error(Logger logger, String format, Object... args) {
        if (logger.isErrorEnabled()) {
            return String.format(format, args);
        }
        return "";
    }

    public static String trace(Logger logger, String format, Object... args) {
        if (logger.isTraceEnabled()) {
            return String.format(format, args);
        }
        return "";
    }

    public static String warn(Logger logger, String format, Object... args) {
        if (logger.isWarnEnabled()) {
            return String.format(format, args);
        }
        return "";
    }

    public static String info(Logger logger, LoggerMessageDefine format, Object... args) {
        if (logger.isInfoEnabled()) {
            return String.format(format.message(), args);
        }
        return "";
    }

    public static String debug(Logger logger, LoggerMessageDefine format, Object... args) {
        if (logger.isDebugEnabled()) {
            return String.format(format.message(), args);
        }
        return "";
    }

    public static String error(Logger logger, LoggerMessageDefine format, Object... args) {
        if (logger.isErrorEnabled()) {
            return String.format(format.message(), args);
        }
        return "";
    }

    public static String trace(Logger logger, LoggerMessageDefine format, Object... args) {
        if (logger.isTraceEnabled()) {
            return String.format(format.message(), args);
        }
        return "";
    }

    public static String warn(Logger logger, LoggerMessageDefine format, Object... args) {
        if (logger.isWarnEnabled()) {
            return String.format(format.message(), args);
        }
        return "";
    }

}
