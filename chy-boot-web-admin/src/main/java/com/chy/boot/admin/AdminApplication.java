package com.chy.boot.admin;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 管理后台应用程序
 *
 * @author YuRuizhi
 * @date 2024/03/18
 */
@Slf4j
@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.chy.boot"})
@MapperScan(basePackages = "com.chy.boot.**.mapper")
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
        log.info("管理后台应用启动成功！");
    }
} 