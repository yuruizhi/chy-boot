package com.chy.boot.service.remote;

import com.chy.boot.service.response.RespDemo;
import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import retrofit2.http.GET;


/**
 * http api
 *
 * @author ZhangHao
 * @date 2021/01/18
 */
// 使用RetrofitClient注解来定义一个HTTP客户端接口，指定基础URL
@RetrofitClient(baseUrl = "${test.baseUrl}")
public interface HttpApi {
    /**
     * 百度
     *
     * @return {@link String}
     */
    // 使用GET注解来定义一个HTTP GET请求，路径为"/"
    @GET("/")
    // 定义一个方法getOneHitokoto，该方法用于发送GET请求并返回一个RespDemo对象
    RespDemo getOneHitokoto();
}
