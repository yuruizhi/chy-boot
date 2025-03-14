package com.changyi.chy.common.api;

import java.io.Serializable;

/**
 * 返回信息接口
 *
 * @author YuRuizhi
 */
public interface IResultCode extends Serializable {

    /**
     * 消息
     *
     * @return String
     */
    String getMessage();

    /**
     * 状态码
     *
     * @return int
     */
    int getCode();
} 