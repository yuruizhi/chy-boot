package com.chy.boot.service.remote;

import com.chy.boot.common.api.R;
import com.chy.boot.service.request.AuthParam;
import com.chy.boot.service.response.AuthResponse;
import org.springframework.stereotype.Service;

/**
 * 认证服务 - 不再使用Retrofit
 *
 * @auther Henry.Yu
 * @date 2025/03/21
 */
@Service
public class AuthService {

    /**
     * 获取令牌方法
     * 这里使用模拟数据替代原来的Retrofit实现
     *
     * @param authParam 认证参数
     * @return 认证响应结果
     */
    public R<AuthResponse> getToken(AuthParam authParam) {
        // 模拟认证逻辑
        AuthResponse response = new AuthResponse();
        response.setToken("mock-token-for-" + authParam.getAccount());
        response.setExpireTime(System.currentTimeMillis() + 3600 * 1000);

        return R.data(response);
    }
}
