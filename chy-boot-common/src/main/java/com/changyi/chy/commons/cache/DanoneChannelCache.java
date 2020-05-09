package com.changyi.chy.commons.cache;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.changyi.chy.commons.component.cache.CacheKeyGeneratorDefined;
import com.changyi.chy.commons.platform.auth.entity.DanoneChannel;
import com.changyi.chy.commons.platform.auth.service.IDnChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DanoneChannelCache implements IDanoneChannelCache{

    @Autowired
    IDnChannelService channelService;

    @Override
    @Cacheable(value = CacheKeyGeneratorDefined.CHANNEL_INFO_CACHE_KG,
            keyGenerator = CacheKeyGeneratorDefined.CHANNEL_INFO_CACHE_KG,
            unless = "#result == null"
    )
    public DanoneChannel get(String channelId)throws Exception{

        if(StringUtils.isBlank(channelId)) {
            log.error("获取渠道信息缓存, channelId为空");
            return null;
        }
        DanoneChannel chyChannel = channelService.getById(channelId);
        return chyChannel;
    }

    @Override
    @CacheEvict(value = CacheKeyGeneratorDefined.CHANNEL_INFO_CACHE_KG,
            keyGenerator = CacheKeyGeneratorDefined.CHANNEL_INFO_CACHE_KG,
            beforeInvocation=true)
    public void delete(String channelId) {

    }

    @Override
    @CacheEvict(value = CacheKeyGeneratorDefined.CHANNEL_INFO_CACHE_KG,
            beforeInvocation=true,
            allEntries = true
    )
    public void clear() {

    }

}
