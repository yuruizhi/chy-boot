package com.chy.boot.rest.config.retrofit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * OkHttp基础拦截器
 * 
 * @author Henry.Yu
 * @date 2021/01/18
 */
public abstract class BaseGlobalInterceptor implements Interceptor {

    /**
     * 拦截器实现
     * 
     * @param chain 拦截器链
     * @return Response
     * @throws IOException IO异常
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        return doIntercept(chain);
    }

    /**
     * 执行拦截逻辑
     * 
     * @param chain 拦截器链
     * @return Response
     * @throws IOException IO异常
     */
    protected abstract Response doIntercept(Chain chain) throws IOException;
} 