package com.changyi.chy;

import com.chy.boot.ChyBootApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * 应用程序上下文加载测试
 * 用于验证Spring Boot上下文是否正确配置
 *
 * @author YuRuizhi
 */
@SpringBootTest(classes = ChyBootApplication.class)
@ActiveProfiles("test")
@DisplayName("应用程序上下文测试")
class ApplicationTest {

    @Test
    @DisplayName("测试Spring上下文是否成功加载")
    void contextLoads() {
        // 如果上下文成功加载，此测试应该通过
        // 不需要断言，如果Spring上下文有问题，测试会失败
    }
} 