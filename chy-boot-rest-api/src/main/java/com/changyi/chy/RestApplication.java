package com.changyi.chy;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import com.baomidou.mybatisplus.annotation.MapperScan;

/**
 * rest应用程序
 *
 * @author ZhangHao
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
