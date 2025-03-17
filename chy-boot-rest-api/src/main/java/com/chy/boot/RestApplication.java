package com.chy.boot;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * rest应用程序
 *
 * @author YuRuizhi
 * @date 2021/01/14
 * @update 2023/03/01 升级到SpringBoot 3.2.3和MyBatis-Plus
 */
@Slf4j
@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.changyi.chy"}, exclude = DruidDataSourceAutoConfigure.class)
@MapperScan(basePackages = "com.changyi.chy.**.mapper")
public class RestApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
        log.info("Rest工程启动成功！");
    }
}
