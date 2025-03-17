package com.chy.boot.commons.cache;

import cn.hutool.core.util.StrUtil;
import com.chy.boot.commons.component.cache.CacheKeyGeneratorDefined;
import com.chy.boot.commons.platform.auth.entity.User;
import com.chy.boot.commons.platform.auth.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * 用户缓存.
 *
 * @author YuRuizhi
 * @date 2020.6.5
 */
@Component
@Slf4j
public class UserCache implements IUserCache {

    @Autowired
    IUserService userService;

    @Override
    @Cacheable(value = CacheKeyGeneratorDefined.USER_INFO_CACHE_KG,
            keyGenerator = CacheKeyGeneratorDefined.USER_INFO_CACHE_KG,
            unless = "#result == null"
    )
    public User get(String account) throws Exception {

        if (StrUtil.isBlank(account)) {
            log.error("获取用户信息缓存, 账号为空");
            return null;
        }
        return userService.getByAccount(account);
    }

    @Override
    @CacheEvict(value = CacheKeyGeneratorDefined.USER_INFO_CACHE_KG,
            keyGenerator = CacheKeyGeneratorDefined.USER_INFO_CACHE_KG,
            beforeInvocation = true)
    public void delete(String account) {

    }

    @Override
    @CacheEvict(value = CacheKeyGeneratorDefined.USER_INFO_CACHE_KG,
            beforeInvocation = true,
            allEntries = true
    )
    public void clear() {

    }

}
