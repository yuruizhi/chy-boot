package com.changyi.chy.common.component.log;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 日志组件配置.
 */
@Configuration
@ConditionalOnProperty("log.data.config")
@ImportResource("classpath:${log.data.config}")
public class LogDataConfig {

}
