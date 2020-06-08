package com.changyi.chy.commons.platform.auth.service;

import com.changyi.chy.commons.exception.DnConsoleException;
import com.changyi.chy.commons.platform.auth.entity.AuthResponse;

/**
 * 认证接口层.
 *
 * @author Henry.yu
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
