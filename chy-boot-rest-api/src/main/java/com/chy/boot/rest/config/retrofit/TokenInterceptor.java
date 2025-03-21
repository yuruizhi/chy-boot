package com.chy.boot.rest.config.retrofit;

import com.chy.boot.common.api.R;
import com.chy.boot.common.platform.auth.entity.AuthParam;
import com.chy.boot.common.platform.auth.entity.AuthResponse;
import com.chy.boot.service.remote.AuthService;
import jakarta.annotation.Resource;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.io.IOException;

/**
 * 令牌拦截器
 *
 * @author ZhangHao
 * @date 2021/01/18
 */
@Component
public class TokenInterceptor extends BaseGlobalInterceptor {

    private final PathMatcher pathMatcher = new AntPathMatcher();

    @Resource
    @Lazy
    private AuthService authService;

    /**
     * do intercept
     *
     * @param chain interceptor chain
     * @return http Response
     * @throws IOException IOException
     */
    @Override
    protected Response doIntercept(Chain chain) throws IOException {
        String[] exclude = new String[]{"/auth/token"};

        Request request = chain.request();

        String path = request.url().encodedPath();

        if (isMatch(exclude, path)) {
            return chain.proceed(request);
        }

        AuthParam authParam = new AuthParam();
        authParam.setAccount("admin");
        authParam.setPassword("123456");

        R<AuthResponse> authResponse = authService.getToken(authParam);

        Request newReq = request.newBuilder()
                .addHeader("x-access-token", authResponse.getData().getAccessToken())
                .build();
        return chain.proceed(newReq);
    }

    /**
     * <p>
     * 当前http的url路径是否与指定的patterns匹配
     * Whether the current http URL path matches the specified patterns
     * </p>
     *
     * @param patterns the specified patterns
     * @param path     http URL path
     * @return 匹配结果
     */
    private boolean isMatch(String[] patterns, String path) {
        if (patterns == null || patterns.length == 0) {
            return false;
        }
        for (String pattern : patterns) {
            boolean match = pathMatcher.match(pattern, path);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
