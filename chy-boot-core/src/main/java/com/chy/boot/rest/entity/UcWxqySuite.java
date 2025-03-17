package com.chy.boot.rest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 套件主表(UcWxqySuite)实体类
 *
 * @author YuRuizhi
 * @since 2021-01-14 14:34:22
 * @update 2023/03/01 更新为MyBatis-Plus实体
 */
@Data
@Accessors(chain = true)
@TableName("uc_wxqy_suite")
public class UcWxqySuite implements Serializable {
    private static final long serialVersionUID = 759760820368851749L;
    /**
     * 套件id
     */
    @TableId(value = "qys_suiteid", type = IdType.INPUT)
    private String qysSuiteid;
    /**
     * 套件秘钥
     */
    private String qysSuiteSecret;
    /**
     * 套件aeskey
     */
    private String qysSuiteAeskey;
    /**
     * 用于应用对消息推送请求时校验的进行签名认证的token
     */
    private String qysToken;
    /**
     * ticket
     */
    private String qysTicket;
    /**
     * ip地址
     */
    private Object qysIps;
    /**
     * 套件令牌
     */
    private String qysSuiteAccessToken;
    /**
     * 过期时间
     */
    private Object qysAccessTokenExpires;
    /**
     * 预授权码
     */
    private String qysPreAuthCode;
    /**
     * 预授权码期限
     */
    private Object qysAuthCodeExpires;
    /**
     * 所属服务商
     */
    private String qysProvider;
    /**
     * 记录状态, 1初始化，2=已更新, 3=已删除
     */
    private Object qysStatus;
    /**
     * 创建时间
     */
    private Object qysCreated;
    /**
     * 更新时间
     */
    private Object qysUpdated;
    /**
     * 删除时间
     */
    private Object qysDeleted;
}