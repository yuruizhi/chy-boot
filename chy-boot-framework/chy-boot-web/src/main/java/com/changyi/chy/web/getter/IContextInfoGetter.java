package com.changyi.chy.web.getter;

/**
 * 上下文信息获取器接口
 *
 * @author three
 * @date 2016/5/19
 */
public interface IContextInfoGetter {

    /**
     * 根据频道标识获取频道类型
     *
     * @param channel 频道标识
     * @return 频道类型
     */
    String getChannelType(String channel);

} 