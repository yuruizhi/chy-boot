package com.chy.boot.service.remote;

import org.springframework.stereotype.Service;

/**
 * HTTP API服务 - 不再使用Retrofit
 *
 * @auther Henry.Yu
 * @date 2023/09/06
 */
@Service
public class HttpApi {

    /**
     * 简单的HTTP请求示例方法，返回一个字符串内容
     * 这里使用模拟数据替代原来的Retrofit实现
     *
     * @return 一个模拟的响应字符串
     */
    public String getContent() {
        return "这是一个模拟的HTTP响应内容";
    }
}
