package com.changyi.chy.commons.getter;


/**
 * 上下文信息获取接口,每个业务系统自己实现获取信息逻辑
 *
 * @author three
 * @date 2016/5/19
 */
public interface IContextInfoGetter {

    String getChannelType(String channelId) throws Exception;

}
