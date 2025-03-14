package com.changyi.chy.datasource.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.provider.AbstractDataSourceProvider;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源配置类
 *
 * @author YuRuizhi
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@AutoConfigureBefore(DynamicDataSourceAutoConfiguration.class)
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
public class DynamicDataSourceConfig {

    private final DynamicDataSourceProperties properties;

    /**
     * 创建动态数据源提供者
     *
     * @return 动态数据源提供者
     */
    @Bean
    public DynamicDataSourceProvider dynamicDataSourceProvider() {
        return new AbstractDataSourceProvider() {
            @Override
            public Map<String, DataSource> loadDataSources() {
                // 获取配置的所有数据源
                Map<String, DataSourceProperty> dataSourcePropertiesMap = properties.getDatasource();
                Map<String, DataSource> dataSourceMap = createDataSourceMap(dataSourcePropertiesMap);
                
                log.info("初始化数据源完成，一共加载 {} 个数据源", dataSourceMap.size());
                // 返回数据源
                return dataSourceMap;
            }
        };
    }

    /**
     * 创建动态数据源
     *
     * @return 动态数据源
     */
    @Bean
    @Primary
    public DataSource dataSource() {
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
        // 设置数据源提供者
        dataSource.setProvider(dynamicDataSourceProvider());
        // 设置默认数据源
        dataSource.setPrimary(properties.getPrimary());
        // 设置启用严格模式（未找到数据源时抛出异常，不使用默认数据源）
        dataSource.setStrict(properties.getStrict());
        // 设置策略类（默认轮询策略）
        dataSource.setStrategy(properties.getStrategy());
        return dataSource;
    }
} 