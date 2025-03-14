package com.changyi.chy.commons.config;

import com.baomidou.dynamic.datasource.plugin.MasterSlaveAutoRoutingPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 多数据源配置
 *
 * <p>
 *     在纯的读写分离环境，写操作全部是master，读操作全部是slave
 * </p>
 *
 * @author YuRuizhi
 * @date 2021/01/15
 */
@Configuration
public class DynamicDateSourceConfig {
    @Bean
    public MasterSlaveAutoRoutingPlugin masterSlaveAutoRoutingPlugin(){
        return new MasterSlaveAutoRoutingPlugin();
    }
}
