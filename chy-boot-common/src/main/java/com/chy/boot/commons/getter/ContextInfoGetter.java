package com.chy.boot.commons.getter;


import com.chy.boot.commons.cache.IUserCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 执行上下文信息获取.
 *
 * @author YuRuizhi
 * @date 2020.6.5
 */
@Component
public class ContextInfoGetter implements IContextInfoGetter {

    @Autowired
    IUserCache userCache;

    protected final Logger logger = LoggerFactory.getLogger(ContextInfoGetter.class);

   /* @Override
    public String getChannelType(String account) throws Exception {
        User chyChannel = chyChannelCache.get(channelId);
        if (chyChannel != null && chyChannel.getStatus().equals(1)) {
            return chyChannel.getChannelType();
        }
        return null;
    }*/

}
