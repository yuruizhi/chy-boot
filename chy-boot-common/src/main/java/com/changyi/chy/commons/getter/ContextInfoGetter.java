package com.changyi.chy.commons.getter;


import com.changyi.chy.commons.cache.IDanoneChannelCache;
import com.changyi.chy.commons.platform.auth.entity.DanoneChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Rpc api执行上下文信息获取
 *
 * @Date 16/5/20
 * @User three
 */
@Component
public class ContextInfoGetter implements IContextInfoGetter {

    @Autowired
    IDanoneChannelCache chyChannelCache;

    protected final Logger logger = LoggerFactory.getLogger(ContextInfoGetter.class);

    @Override
    public String getChannelType(String channelId) throws Exception {
        DanoneChannel chyChannel = chyChannelCache.get(channelId);
        if (chyChannel != null && chyChannel.getStatus().equals(1)) {
            return chyChannel.getChannelType();
        }
        return null;
    }

}
