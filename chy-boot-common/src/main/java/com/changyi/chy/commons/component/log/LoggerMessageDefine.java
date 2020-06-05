package com.changyi.chy.commons.component.log;

/**
 * logger 消息格式定义.
 */
public enum LoggerMessageDefine {
    DEMO("这个只是一个demo演示,大家不要在意我的存在,请忽略我, %s");
    private final String message;

    LoggerMessageDefine(String message) {
        this.message = message;
    }

    public String message() {
        return this.message;
    }
}
