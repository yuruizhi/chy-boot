package com.changyi.chy;

import com.changyi.chy.starter.ChyBootApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * 应用启动测试
 *
 * @author YuRuizhi
 */
@SpringBootTest(classes = ChyBootApplication.class)
@ActiveProfiles("test")
public class ApplicationTest {

    @Test
    public void contextLoads() {
        // 验证应用上下文是否正常加载
    }
} 