package com.changyi.chy.commons.cache;

import com.changyi.chy.commons.platform.auth.entity.User;

/**
 * 用户缓存.
 *
 * @author Henry.yu
 * @date 2020.6.5
 */
public interface IUserCache {

    /**
     * 通过账号获取用户缓存信息.
     *
     * @param account 账号
     * @return {User}
     * @throws Exception 异常
     */
    User get(String account) throws Exception;

    /**
     * 删除缓存.
     *
     * @param account 账号
     */
    void delete(String account);

    /**
     * 清除所有全局缓存.
     */
    void clear();
}
