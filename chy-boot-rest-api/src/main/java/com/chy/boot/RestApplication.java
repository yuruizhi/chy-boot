package com.chy.boot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动类
 *
 * @auther Henry.Yu
 * @date 2025/03/21
 */
@Slf4j
@EnableAsync
@SpringBootApplication
@MapperScan(basePackages = "com.chy.boot.**.mapper")
@OpenAPIDefinition(
        info = @Info(
                title = "CHY Boot API",
                version = "1.0.0",
                description = "CHY Boot REST API Documentation",
                contact = @Contact(name = "Henry Yu", email = "yuruizhi@foxmail.com"),
                license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html")
        )
)
public class RestApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RestApplication.class, args);
        log.info("服务启动成功");
    }
}
