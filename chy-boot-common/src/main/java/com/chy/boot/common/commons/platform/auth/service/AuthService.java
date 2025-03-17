package com.chy.boot.common.commons.platform.auth.service;

import com.chy.boot.common.commons.exception.DnConsoleException;
import com.chy.boot.common.commons.platform.auth.entity.AuthResponse;

/**
 * 认证接口层.
 *
 * @author YuRuizhi
 * @date 2020.6.5
 */
public interface AuthService {

    /**
     * 获取token.
     *
     * @param account  账号
     * @param password 密码
     * @return {AuthResponse}
     * @throws DnConsoleException
     */
    AuthResponse getToken(String account, String password) throws DnConsoleException;

}
