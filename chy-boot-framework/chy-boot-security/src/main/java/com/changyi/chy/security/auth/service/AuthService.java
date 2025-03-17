package com.changyi.chy.security.auth.service;

import com.changyi.chy.common.exception.DnConsoleException;
import com.changyi.chy.commons.platform.auth.entity.AuthResponse;

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
     */
    AuthResponse getToken(String account, String password) throws DnConsoleException;

}
