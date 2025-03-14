package com.changyi.chy.job.config;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Quartz定时任务配置
 *
 * @author YuRuizhi
 */
@Configuration
@EnableScheduling
public class QuartzConfig {

    @Autowired
    private DataSource dataSource;

    /**
     * 配置Quartz属性
     *
     * @return Quartz属性
     * @throws IOException 配置文件读取异常
     */
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /**
     * 配置Scheduler工厂
     *
     * @return SchedulerFactoryBean
     * @throws IOException 配置文件读取异常
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        
        // 使用数据源，自动从数据库中加载任务
        factory.setDataSource(dataSource);
        // 配置属性
        factory.setQuartzProperties(quartzProperties());
        // 任务自动启动
        factory.setAutoStartup(true);
        // 任务覆盖已存在的任务
        factory.setOverwriteExistingJobs(true);
        // 等待任务完成再关闭应用
        factory.setWaitForJobsToCompleteOnShutdown(true);
        // 启动延迟
        factory.setStartupDelay(10);
        
        return factory;
    }

    /**
     * 配置Scheduler
     *
     * @return Scheduler
     * @throws IOException 配置文件读取异常
     */
    @Bean
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }
} 