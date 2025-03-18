package com.chy.boot.framework.common.context;

import com.chy.boot.common.commons.enums.ClientType;

/**
 * 客户端上下文
 * 用于存储当前请求的客户端类型
 *
 * @author YuRuizhi
 * @date 2024/3/18
 */
public class ClientContext {
    
    private static final ThreadLocal<ClientType> CLIENT_TYPE_THREAD_LOCAL = new ThreadLocal<>();
    
    /**
     * 设置客户端类型
     *
     * @param clientType 客户端类型
     */
    public static void setClientType(ClientType clientType) {
        CLIENT_TYPE_THREAD_LOCAL.set(clientType);
    }
    
    /**
     * 获取客户端类型
     *
     * @return 客户端类型，如果未设置则返回UNKNOWN
     */
    public static ClientType getClientType() {
        ClientType clientType = CLIENT_TYPE_THREAD_LOCAL.get();
        return clientType != null ? clientType : ClientType.UNKNOWN;
    }
    
    /**
     * 清除客户端类型
     */
    public static void clear() {
        CLIENT_TYPE_THREAD_LOCAL.remove();
    }
    
    /**
     * 检查是否为管理后台
     */
    public static boolean isAdmin() {
        return ClientType.WEB_ADMIN == getClientType();
    }
    
    /**
     * 检查是否为移动应用
     */
    public static boolean isMobile() {
        return ClientType.MOBILE_APP == getClientType();
    }
    
    /**
     * 检查是否为小程序
     */
    public static boolean isMiniProgram() {
        return ClientType.MINI_PROGRAM == getClientType();
    }
    
    /**
     * 检查是否为开放API
     */
    public static boolean isOpenApi() {
        return ClientType.OPEN_API == getClientType();
    }
} 