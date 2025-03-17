package com.chy.boot.service.service.remote;

import com.chy.boot.service.service.response.RespDemo;
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
