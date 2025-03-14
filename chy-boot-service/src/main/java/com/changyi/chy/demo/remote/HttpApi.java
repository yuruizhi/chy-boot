package com.changyi.chy.demo.remote;

import com.changyi.chy.demo.response.RespDemo;
import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import retrofit2.http.GET;


/**
 * http api
 *
 * @author YuRuizhi
 * @date 2021/01/18
 */
@RetrofitClient(baseUrl = "${test.baseUrl}")
public interface HttpApi {
    /**
     * 百度
     *
     * @return {@link String}
     */
    @GET("/")
    RespDemo getOneHitokoto();
}
