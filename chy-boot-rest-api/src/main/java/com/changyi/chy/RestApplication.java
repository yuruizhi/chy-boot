package com.changyi.chy;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


/**
 * rest应用程序
 *
 * @author ZhangHao
 * @date 2021/01/14
 */
@Slf4j
@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.changyi.chy"}, exclude = DruidDataSourceAutoConfigure.class)
@MapperScan(basePackages = "com.changyi.chy.**.mapper")
@OpenAPIDefinition(
    info = @Info(
        title = "CHY Boot API",
        description = "CHY Boot REST API Documentation",
        version = "1.0.0"
    )
)
public class RestApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
        log.info("Rest工程启动成功！");
    }
}
