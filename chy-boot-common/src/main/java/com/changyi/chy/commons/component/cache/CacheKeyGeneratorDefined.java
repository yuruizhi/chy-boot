package com.changyi.chy.commons.component.cache;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "spring.redis.host")
public class CacheKeyGeneratorDefined implements BeanFactoryAware {
    /**
     * 全局配置信息
     */
    public static final String GLOBAL_SETTINGS_CACHE_KG = "GLOBAL_SETTINGS_CACHE_KG";


    public static final String CHANNEL_INFO_CACHE_KG = "CHANNEL_INFO_CACHE_KG";

    /**
     * 短信验证码
     */
    public static final String SMS_CODE = "SMS_CODE:";
    /**
     * 图片验证码
     */
    public static final String CAPTCHA_CODE = "CAPTCHA_CODE:";

    protected BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


    @PostConstruct
    public void onPostConstruct() {
        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;

        log.info("初始化 redis key Generator ...");
        new RedisPrefixParamsCacheConfig(GLOBAL_SETTINGS_CACHE_KG).registerSingleton(configurableBeanFactory);
        new RedisPrefixParamsCacheConfig(CHANNEL_INFO_CACHE_KG).registerSingleton(configurableBeanFactory);

    }

    /**
     * 缓存 prefix cache keyGenerator 定义
     * 拼接所有的方法调用参数
     */
    public static class RedisPrefixParamsCacheConfig {
        public String keyGenerator;
        public String prefix;
        public String key;
        public String value;

        RedisPrefixParamsCacheConfig(String keyGenerator) {
            this.keyGenerator = keyGenerator;
            this.prefix = keyGenerator+"_";
            this.key = keyGenerator;
            this.value = keyGenerator;
        }

        void registerSingleton(ConfigurableBeanFactory configurableBeanFactory) {
            configurableBeanFactory.registerSingleton(keyGenerator, new KeyGenerator() {
                private final Logger log = LoggerFactory.getLogger(getClass());
                @Override
                public Object generate(Object target, Method method, Object... params) {
                    StringBuilder sb = new StringBuilder();

                    sb.append(prefix);
                    for (Object p: params) {
                        sb.append(p).append('_');
                    }
                    //log.debug("execute redis key generator: "+sb.toString());
                    return sb.toString();
                }

            });
        }
    }


    @Bean
    public KeyGenerator generalKeyGenerator() {

        return new KeyGenerator() {
            private final Logger log = LoggerFactory.getLogger(getClass());

            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());


                }
                //log.debug("execute redis key generator: "+sb.toString());
                return sb.toString();
            }

        };

    }
}
