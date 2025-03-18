package com.chy.boot.framework.common.constants;

/**
 * 客户端相关常量
 *
 * @author YuRuizhi
 * @date 2024/3/18
 */
public interface ClientConstants {

    /**
     * 客户端类型请求头
     */
    String CLIENT_TYPE_HEADER = "X-Client-Type";
    
    /**
     * 客户端类型 - 后台管理
     */
    String CLIENT_TYPE_WEB_ADMIN = "web-admin";
    
    /**
     * 客户端类型 - 移动应用
     */
    String CLIENT_TYPE_MOBILE_APP = "mobile-app";
    
    /**
     * 客户端类型 - 小程序
     */
    String CLIENT_TYPE_MINI_PROGRAM = "mini-program";
    
    /**
     * 客户端类型 - 开放API
     */
    String CLIENT_TYPE_OPEN_API = "open-api";
    
    /**
     * 客户端类型 - 未知
     */
    String CLIENT_TYPE_UNKNOWN = "unknown";
    
    /**
     * 请求路径前缀 - 后台管理
     */
    String PATH_PREFIX_WEB_ADMIN = "/api";
    
    /**
     * 请求路径前缀 - 移动应用
     */
    String PATH_PREFIX_MOBILE_APP = "/mobile";
    
    /**
     * 请求路径前缀 - 小程序
     */
    String PATH_PREFIX_MINI_PROGRAM = "/mini";
    
    /**
     * 请求路径前缀 - 开放API
     */
    String PATH_PREFIX_OPEN_API = "/open";
} 
