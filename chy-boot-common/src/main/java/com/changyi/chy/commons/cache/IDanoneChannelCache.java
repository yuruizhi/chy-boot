package com.changyi.chy.commons.cache;

import com.changyi.chy.commons.platform.auth.entity.DanoneChannel;


public interface IDanoneChannelCache {

    DanoneChannel get(String channelId)throws Exception;



    /**
     * 删除缓存
     * @param epEnumber
     */

    void delete(String epEnumber);


    /**
     * 清楚所有全局缓存
     */

    void clear();
}
