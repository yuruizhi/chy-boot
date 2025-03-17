package com.changyi.chy.demo.controller;

import com.changyi.chy.commons.api.R;
import com.changyi.chy.demo.entity.User;
import com.changyi.chy.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 用户控制器测试
 * @author YuRuizhi
 */
@DisplayName("UserController 单元测试")
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("测试通过ID获取用户")
    void testGetById() throws Exception {
        // 准备测试数据
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("admin");
        mockUser.setNickname("管理员");
        mockUser.setEmail("admin@example.com");
        mockUser.setStatus(1);
        mockUser.setCreateTime(LocalDateTime.now());
        
        // 配置Mock行为
        when(userService.getById(anyLong())).thenReturn(mockUser);
        
        // 执行请求并验证
        mockMvc.perform(get("/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.username").value("admin"))
                .andExpect(jsonPath("$.data.nickname").value("管理员"));
    }

    @Test
    @DisplayName("测试分页查询用户")
    void testList() throws Exception {
        // 准备测试数据
        List<User> mockUsers = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("admin");
        user1.setNickname("管理员");
        
        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user");
        user2.setNickname("普通用户");
        
        mockUsers.add(user1);
        mockUsers.add(user2);
        
        // 配置Mock行为
        when(userService.queryAllByLimit(anyInt(), anyInt())).thenReturn(mockUsers);
        
        // 执行请求并验证
        mockMvc.perform(get("/user/list")
                .param("page", "1")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].username").value("admin"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].username").value("user"));
    }

    @Test
    @DisplayName("测试创建用户")
    void testSave() throws Exception {
        // 配置Mock行为
        when(userService.save(any(User.class))).thenReturn(true);
        
        // 执行请求并验证
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"new-user\",\"password\":\"123456\",\"nickname\":\"新用户\",\"email\":\"new@example.com\",\"status\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }
} 