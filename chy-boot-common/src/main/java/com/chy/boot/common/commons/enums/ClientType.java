package com.chy.boot.common.commons.enums;

import lombok.Getter;

/**
 * 客户端类型枚举
 * 用于区分不同的客户端访问
 *
 * @author YuRuizhi
 * @date 2024/3/18
 */
@Getter
public enum ClientType {
    
    /**
     * PC管理后台
     */
    WEB_ADMIN("web-admin", "管理后台"),
    
    /**
     * 移动应用
     */
    MOBILE_APP("mobile-app", "移动应用"),
    
    /**
     * 微信小程序
     */
    MINI_PROGRAM("mini-program", "微信小程序"),
    
    /**
     * 开放API
     */
    OPEN_API("open-api", "开放API"),
    
    /**
     * 未知客户端
     */
    UNKNOWN("unknown", "未知客户端");
    
    /**
     * 编码
     */
    private final String code;
    
    /**
     * 描述
     */
    private final String desc;
    
    ClientType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    /**
     * 根据编码获取客户端类型
     *
     * @param code 编码
     * @return 客户端类型
     */
    public static ClientType getByCode(String code) {
        if (code == null) {
            return UNKNOWN;
        }
        
        for (ClientType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        
        return UNKNOWN;
    }
} 