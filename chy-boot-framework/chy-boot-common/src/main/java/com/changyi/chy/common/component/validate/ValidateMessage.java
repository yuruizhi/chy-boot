package com.changyi.chy.common.component.validate;

/**
 * 用于表单校验的消息常量
 */
public interface ValidateMessage {
    /**
     * 不能为空
     */
    String NotNull = "不能为空";
    
    /**
     * 长度不够
     */
    String MinLength = "长度不够";
    
    /**
     * 超出最大长度
     */
    String MaxLength = "超出最大长度";
    
    /**
     * 格式不正确
     */
    String Pattern = "格式不正确";
    
    /**
     * 无效的值
     */
    String Invalid = "无效的值";
} 