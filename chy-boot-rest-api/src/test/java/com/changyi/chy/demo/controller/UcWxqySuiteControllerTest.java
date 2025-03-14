package com.changyi.chy.demo.controller;

import com.changyi.chy.commons.api.R;
import com.changyi.chy.demo.entity.UcWxqySuite;
import com.changyi.chy.demo.remote.AuthService;
import com.changyi.chy.demo.remote.HttpApi;
import com.changyi.chy.demo.service.UcWxqySuiteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 套件主表控制器测试
 * @author YuRuizhi
 */
@DisplayName("UcWxqySuiteController 单元测试")
class UcWxqySuiteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UcWxqySuiteService ucWxqySuiteService;

    @Mock
    private HttpApi httpApi;

    @Mock
    private AuthService authService;

    @InjectMocks
    private UcWxqySuiteController ucWxqySuiteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ucWxqySuiteController).build();
    }

    @Test
    @DisplayName("测试通过ID获取单条记录")
    void testSelectOne() throws Exception {
        // 准备测试数据
        UcWxqySuite mockSuite = new UcWxqySuite();
        mockSuite.setQysSuiteid("test-suite-id");
        mockSuite.setQysSuiteSecret("test-secret");
        
        // 配置Mock行为
        when(ucWxqySuiteService.getById(anyString())).thenReturn(mockSuite);
        
        // 执行请求并验证
        mockMvc.perform(get("/ucWxqySuite/selectOne")
                .param("id", "test-suite-id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.qysSuiteid").value("test-suite-id"))
                .andExpect(jsonPath("$.qysSuiteSecret").value("test-secret"));
    }

    @Test
    @DisplayName("测试版本化API获取单条记录")
    void testSelectOneWithVersion() throws Exception {
        // 准备测试数据
        UcWxqySuite mockSuite = new UcWxqySuite();
        mockSuite.setQysSuiteid("test-suite-id");
        mockSuite.setQysSuiteSecret("test-secret");
        
        // 配置Mock行为
        when(ucWxqySuiteService.getById(anyString())).thenReturn(mockSuite);
        
        // 执行请求并验证
        mockMvc.perform(get("/ucWxqySuite/v3/selectOne")
                .param("id", "test-suite-id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.qysSuiteid").value("test-suite-id"))
                .andExpect(jsonPath("$.qysSuiteSecret").value("test-secret"));
    }

    @Test
    @DisplayName("测试保存套件信息")
    void testSave() throws Exception {
        // 配置Mock行为
        when(ucWxqySuiteService.save(any(UcWxqySuite.class))).thenReturn(true);
        
        // 执行请求并验证
        mockMvc.perform(post("/ucWxqySuite")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"qysSuiteid\":\"test-suite-id\",\"qysSuiteSecret\":\"test-secret\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("测试远程HTTP调用")
    void testBaidu() throws Exception {
        // 配置Mock行为
        when(httpApi.getOneHitokoto()).thenReturn(null);
        
        // 执行请求并验证
        mockMvc.perform(get("/ucWxqySuite/baidu")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("测试认证服务调用")
    void testAuthService() throws Exception {
        // 配置Mock行为
        when(authService.test()).thenReturn(R.success("认证成功"));
        
        // 执行请求并验证
        mockMvc.perform(get("/ucWxqySuite/test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value("认证成功"));
    }
} 