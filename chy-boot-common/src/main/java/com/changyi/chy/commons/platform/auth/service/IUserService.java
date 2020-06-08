package com.changyi.chy.commons.platform.auth.service;

import com.changyi.chy.commons.platform.auth.entity.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-01-10
 */
public interface IUserService {

    User getByAccount(String account);

}
