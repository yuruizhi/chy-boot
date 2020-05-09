package com.changyi.chy.commons.platform.auth.service;

import com.changyi.chy.commons.exception.DnConsoleException;
import com.changyi.chy.commons.platform.auth.entity.AuthResponse;

public interface AuthService {

    AuthResponse getToken(String id, String Secret) throws DnConsoleException;


}
