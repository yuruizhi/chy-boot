package com.chy.boot.framework.common.utils;

import com.chy.boot.framework.common.context.ClientContext;
import com.chy.boot.framework.common.enums.ClientType;

/**
 * 客户端类型工具类
 *
 * @author YuRuizhi
 * @date 2024/3/18
 */
public class ClientTypeUtils {

    /**
     * 判断当前请求是否来自管理后台
     *
     * @return 是否来自管理后台
     */
    public static boolean isAdmin() {
        return ClientContext.isAdmin();
    }

    /**
     * 判断当前请求是否来自移动应用
     *
     * @return 是否来自移动应用
     */
    public static boolean isMobile() {
        return ClientContext.isMobile();
    }

    /**
     * 判断当前请求是否来自小程序
     *
     * @return 是否来自小程序
     */
    public static boolean isMiniProgram() {
        return ClientContext.isMiniProgram();
    }

    /**
     * 判断当前请求是否来自开放API
     *
     * @return 是否来自开放API
     */
    public static boolean isOpenApi() {
        return ClientContext.isOpenApi();
    }
    
    /**
     * 获取当前请求的客户端类型
     *
     * @return 客户端类型
     */
    public static ClientType getClientType() {
        return ClientContext.getClientType();
    }
    
    /**
     * 判断当前客户端类型是否为指定类型
     *
     * @param clientType 客户端类型
     * @return 是否为指定客户端类型
     */
    public static boolean isClientType(ClientType clientType) {
        return clientType != null && clientType.equals(getClientType());
    }
} 