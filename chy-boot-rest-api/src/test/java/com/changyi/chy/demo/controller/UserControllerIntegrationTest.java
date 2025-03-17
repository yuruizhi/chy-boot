package com.changyi.chy.demo.controller;

import com.changyi.chy.config.TestConfig;
import com.changyi.chy.demo.entity.User;
import com.changyi.chy.demo.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 用户控制器集成测试
 * 
 * @author YuRuizhi
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestConfig.class)
@DisplayName("UserController 集成测试")
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private UserService userService;
    
    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("测试通过ID获取数据库中的记录")
    void testGetById() throws Exception {
        // 从测试数据库中获取预设数据
        mockMvc.perform(get("/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.username", is("admin")));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("测试分页查询用户")
    void testList() throws Exception {
        mockMvc.perform(get("/user/list")
                .param("page", "1")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data[0].username", is("admin")));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("测试保存记录到数据库")
    void testSave() throws Exception {
        // 创建新记录
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"integration-test-user\",\"password\":\"123456\",\"nickname\":\"集成测试用户\",\"email\":\"integration@example.com\",\"status\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.success", is(true)));
        
        // 验证记录是否已保存
        mockMvc.perform(get("/user/list")
                .param("page", "1")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data[?(@.username == 'integration-test-user')].nickname", is("集成测试用户")));
    }
} 