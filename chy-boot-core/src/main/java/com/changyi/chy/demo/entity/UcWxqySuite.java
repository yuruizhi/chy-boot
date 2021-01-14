package com.changyi.chy.demo.entity;

import java.io.Serializable;

/**
 * 套件主表(UcWxqySuite)实体类
 *
 * @author ZhangHao
 * @since 2021-01-14 14:34:22
 */
public class UcWxqySuite implements Serializable {
    private static final long serialVersionUID = 759760820368851749L;
    /**
     * 套件id
     */
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


    public String getQysSuiteid() {
        return qysSuiteid;
    }

    public void setQysSuiteid(String qysSuiteid) {
        this.qysSuiteid = qysSuiteid;
    }

    public String getQysSuiteSecret() {
        return qysSuiteSecret;
    }

    public void setQysSuiteSecret(String qysSuiteSecret) {
        this.qysSuiteSecret = qysSuiteSecret;
    }

    public String getQysSuiteAeskey() {
        return qysSuiteAeskey;
    }

    public void setQysSuiteAeskey(String qysSuiteAeskey) {
        this.qysSuiteAeskey = qysSuiteAeskey;
    }

    public String getQysToken() {
        return qysToken;
    }

    public void setQysToken(String qysToken) {
        this.qysToken = qysToken;
    }

    public String getQysTicket() {
        return qysTicket;
    }

    public void setQysTicket(String qysTicket) {
        this.qysTicket = qysTicket;
    }

    public Object getQysIps() {
        return qysIps;
    }

    public void setQysIps(Object qysIps) {
        this.qysIps = qysIps;
    }

    public String getQysSuiteAccessToken() {
        return qysSuiteAccessToken;
    }

    public void setQysSuiteAccessToken(String qysSuiteAccessToken) {
        this.qysSuiteAccessToken = qysSuiteAccessToken;
    }

    public Object getQysAccessTokenExpires() {
        return qysAccessTokenExpires;
    }

    public void setQysAccessTokenExpires(Object qysAccessTokenExpires) {
        this.qysAccessTokenExpires = qysAccessTokenExpires;
    }

    public String getQysPreAuthCode() {
        return qysPreAuthCode;
    }

    public void setQysPreAuthCode(String qysPreAuthCode) {
        this.qysPreAuthCode = qysPreAuthCode;
    }

    public Object getQysAuthCodeExpires() {
        return qysAuthCodeExpires;
    }

    public void setQysAuthCodeExpires(Object qysAuthCodeExpires) {
        this.qysAuthCodeExpires = qysAuthCodeExpires;
    }

    public String getQysProvider() {
        return qysProvider;
    }

    public void setQysProvider(String qysProvider) {
        this.qysProvider = qysProvider;
    }

    public Object getQysStatus() {
        return qysStatus;
    }

    public void setQysStatus(Object qysStatus) {
        this.qysStatus = qysStatus;
    }

    public Object getQysCreated() {
        return qysCreated;
    }

    public void setQysCreated(Object qysCreated) {
        this.qysCreated = qysCreated;
    }

    public Object getQysUpdated() {
        return qysUpdated;
    }

    public void setQysUpdated(Object qysUpdated) {
        this.qysUpdated = qysUpdated;
    }

    public Object getQysDeleted() {
        return qysDeleted;
    }

    public void setQysDeleted(Object qysDeleted) {
        this.qysDeleted = qysDeleted;
    }

}