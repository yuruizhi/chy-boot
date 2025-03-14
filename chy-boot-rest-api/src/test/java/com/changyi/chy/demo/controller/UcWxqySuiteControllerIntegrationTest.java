package com.changyi.chy.demo.controller;

import com.changyi.chy.config.TestConfig;
import com.changyi.chy.demo.entity.UcWxqySuite;
import com.changyi.chy.demo.service.UcWxqySuiteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 套件控制器集成测试
 * 
 * @author YuRuizhi
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestConfig.class)
@DisplayName("UcWxqySuiteController 集成测试")
class UcWxqySuiteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private UcWxqySuiteService ucWxqySuiteService;
    
    @Test
    @DisplayName("测试通过ID获取数据库中的记录")
    void testSelectOne() throws Exception {
        // 从测试数据库中获取预设数据
        mockMvc.perform(get("/ucWxqySuite/selectOne")
                .param("id", "test-suite-1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.qysSuiteid", is("test-suite-1")))
                .andExpect(jsonPath("$.qysSuiteSecret", is("test-secret-1")));
    }
    
    @Test
    @DisplayName("测试保存记录到数据库")
    void testSave() throws Exception {
        String newSuiteId = "integration-test-suite";
        
        // 创建新记录
        mockMvc.perform(post("/ucWxqySuite")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"qysSuiteid\":\"" + newSuiteId + "\",\"qysSuiteSecret\":\"integration-test-secret\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.success", is(true)));
        
        // 验证记录是否已保存
        mockMvc.perform(get("/ucWxqySuite/selectOne")
                .param("id", newSuiteId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.qysSuiteid", is(newSuiteId)))
                .andExpect(jsonPath("$.qysSuiteSecret", is("integration-test-secret")));
    }
} 