package com.changyi.chy.starter;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * CHY-Boot应用启动类
 *
 * @author YuRuizhi
 * @date 2023/03/01
 * @update 2024/03/14 优化项目结构
 */
@Slf4j
@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.changyi.chy"})
@MapperScan(basePackages = "com.changyi.chy.**.mapper")
public class ChyBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChyBootApplication.class, args);
        log.info("CHY-Boot应用启动成功！");
    }
} 