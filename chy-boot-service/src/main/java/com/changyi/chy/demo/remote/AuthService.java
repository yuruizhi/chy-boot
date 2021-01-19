package com.changyi.chy.demo.remote;

import com.changyi.chy.commons.api.R;
import com.changyi.chy.commons.platform.auth.entity.AuthParam;
import com.changyi.chy.commons.platform.auth.entity.AuthResponse;
import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * 身份验证服务
 * http api
 *
 * @author ZhangHao
 * @date 2021/01/18
 */
@RetrofitClient(baseUrl = "${test.authUri}")
public interface AuthService {
    /**
     * 获得令牌
     * @param authParam 身份验证参数
     * @return {@link R<AuthResponse>}
     */
    @POST("auth/token")
    R<AuthResponse> getToken(@Body AuthParam authParam);

    /**
     * 百度
     *
     * @return {@link String}
     */
    @GET("test")
    R<String> test();
}
