package com.chy.boot.rest;

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
 */
@Slf4j
@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.chy.boot"})
@MapperScan(basePackages = "com.chy.boot.**.mapper")
public class RestApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
        log.info("Rest工程启动成功！");
    }
}
