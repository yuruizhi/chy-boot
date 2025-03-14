package com.changyi.chy.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.changyi.chy"})
public class ChyBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChyBootApplication.class, args);
    }
} 