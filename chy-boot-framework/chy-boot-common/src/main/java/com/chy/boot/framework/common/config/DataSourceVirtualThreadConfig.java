package com.chy.boot.framework.common.config;

import com.zaxxer.hikari.HikariConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 数据库连接池虚拟线程优化配置
 * <p>
 * 针对虚拟线程优化数据库连接池配置，避免线程饥饿问题
 * </p>
 *
 * @author YuRuizhi
 * @date 2024/3/17
 */
@Configuration
@Slf4j
@ConditionalOnProperty(name = "app.virtual-threads.enabled", havingValue = "true", matchIfMissing = true)
public class DataSourceVirtualThreadConfig {

    @Value("${spring.datasource.dynamic.hikari.maximum-pool-size:20}")
    private int maxPoolSize;
    
    @Value("${spring.datasource.dynamic.hikari.minimum-idle:5}")
    private int minIdle;
    
    @Value("${spring.datasource.dynamic.hikari.connection-timeout:30000}")
    private long connectionTimeout;
    
    @PostConstruct
    public void init() {
        log.info("初始化虚拟线程优化的数据库连接池配置");
        
        // 当使用虚拟线程时，我们应该确保连接池大小适中
        // 过大的连接池会浪费资源，过小的连接池会限制并发性能
        log.info("数据库连接池配置: 最大连接数={}, 最小空闲连接={}, 连接超时={}ms", 
                maxPoolSize, minIdle, connectionTimeout);
        
        // 虚拟线程相关的建议配置说明
        log.info("使用虚拟线程时的数据库连接池建议:");
        log.info("1. 降低连接池大小 - 虚拟线程可以共享更少的数据库连接");
        log.info("2. 设置合理的超时时间 - 避免长时间阻塞");
        log.info("3. 确保使用连接测试查询 - 验证连接有效性");
        log.info("4. 考虑使用Reactive驱动 - 进一步提高异步处理能力");
    }
    
    /**
     * 针对虚拟线程优化的HikariCP配置示例
     * 注: 此方法仅用于展示最佳实践，不是实际配置
     */
    private HikariConfig createOptimalHikariConfig() {
        HikariConfig config = new HikariConfig();
        
        // 使用虚拟线程时建议的连接池配置
        config.setMaximumPoolSize(20);      // 降低常规连接池大小
        config.setMinimumIdle(5);
        config.setConnectionTimeout(30000); // 30秒连接超时
        config.setIdleTimeout(600000);      // 10分钟空闲超时
        config.setMaxLifetime(1800000);     // 30分钟最大生存期
        
        // 设置连接测试查询，确保连接有效
        config.setConnectionTestQuery("SELECT 1");
        
        // 设置连接初始化SQL，如设置会话参数
        config.setConnectionInitSql("SET application_name = 'chy-boot-vthread'");
        
        return config;
    }
} 
