package com.chy.boot.common.api;

import java.io.Serializable;

/**
 * 返回信息.
 *
 * @author Henry.yu
 * @date 2020.6.5
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
