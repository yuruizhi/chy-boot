package com.changyi.chy.commons.component.cache;

/**
 * 全局配置信息获取接口
 *
 *@author wuwh
 *@date 2020/1/10
 */
public interface IGlobalSettingsGetter {
    /**
     * 缓存获取
     * @param key
     * @return
     * @throws Exception
     */
    Setting get(String key) throws Exception;

    /**
     * 缓存设置,需要实现自动同步数据库
     * @param key
     * @param value
     * @param comment
     * @return
     * @throws Exception
     */
    Setting save(String key, String value, String comment) throws Exception;

    /**
     * 删除缓存
     * @param key
     */
    void delete(String key);

    /**
     * 清楚所有全局缓存
     */
    void clear();
}
