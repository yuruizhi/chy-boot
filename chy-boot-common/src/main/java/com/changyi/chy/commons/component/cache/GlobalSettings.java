package com.changyi.chy.commons.component.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GlobalSettings {
    public enum Keys {
        USING_TEST,
        MEMBER_CALL_BACK_URL,
        BABY_CALL_BACK_URL,
        POP_DECODE_RECORD_BACK_URL,
        MAX_RETRY_TIMES,
        RETRY_SLEEP_MILLISECOND;

        public Setting get() {
            return GlobalSettings.get(this.name());
        }

        public Setting set(String val, String comment) {
            return GlobalSettings.save(this.name(), val, comment);
        }

        public Setting set(String val) {
            return GlobalSettings.save(this.name(), val, null);
        }

        public void del() {
            GlobalSettings.del(this.name());
        }
    }

    /**
     * settingsGetter 操作对象
     */
    private static IGlobalSettingsGetter settingsGetter;

    @Autowired
    /*public void setCache(IGlobalSettingsGetter getter){
        settingsGetter = getter;
    }*/

    /**
     * 内部常量使用
     *
     * @param key
     * @return
     */
    private static Setting get(String key) {
        try {
            return settingsGetter.get(key);
        } catch (Exception e) {
            log.error("获取缓存失败", e);
            return null;
        }
    }

    private static Setting save(String key, String val, String comment) {
        if (val == null) {
            val = key;
        }
        if (comment == null) {
            comment = val;
        }

        try {
            return settingsGetter.save(key, val, comment);
        } catch (Exception e) {
            log.info("保存配置失败", e);
            return null;
        }
    }

    private static void del(String key) {
        settingsGetter.delete(key);
    }

    /**
     * 清空缓存
     */
    public static void clear() {
        settingsGetter.clear();
    }

}
