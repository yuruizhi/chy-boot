package com.chy.boot.common.commons.idempotent.service;

/**
 * 幂等性Token服务接口
 * 用于生成、验证和删除幂等性Token
 *
 * @author YuRuizhi
 */
public interface IdempotentTokenService {

    /**
     * 创建幂等性Token
     *
     * @param prefix Token前缀
     * @param timeout 超时时间
     * @param timeUnit 时间单位
     * @return 生成的Token
     */
    String createToken(String prefix, long timeout, java.util.concurrent.TimeUnit timeUnit);

    /**
     * 验证Token是否有效
     *
     * @param prefix Token前缀
     * @param token Token值
     * @return 是否有效
     */
    boolean validateToken(String prefix, String token);

    /**
     * 删除Token
     *
     * @param prefix Token前缀
     * @param token Token值
     * @return 是否删除成功
     */
    boolean deleteToken(String prefix, String token);
} 