package com.chy.boot.rest.remote;

import com.chy.boot.commons.api.R;
import com.chy.boot.commons.platform.auth.entity.AuthParam;
import com.chy.boot.commons.platform.auth.entity.AuthResponse;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * 身份验证服务
 * http api
 *
 * @author YuRuizhi
 * @date 2021/01/18
 */
// 注释掉RetrofitClient注解，因为它在当前版本中不可用
// @RetrofitClient(baseUrl = "${test.authUri}")
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
