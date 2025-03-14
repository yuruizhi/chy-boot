package com.changyi.chy.demo.service.impl;

import com.changyi.chy.commons.component.cache.RedisUtil;
import com.changyi.chy.demo.entity.UcWxqySuite;
import com.changyi.chy.demo.mapper.UcWxqySuiteDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 套件服务实现类测试
 * @author YuRuizhi
 */
@DisplayName("UcWxqySuiteServiceImpl 单元测试")
class UcWxqySuiteServiceImplTest {

    @Mock
    private UcWxqySuiteDao baseMapper;

    @Mock
    private RedisUtil redisUtil;

    @InjectMocks
    private UcWxqySuiteServiceImpl ucWxqySuiteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("测试分页查询")
    void testQueryAllByLimit() {
        // 准备测试数据
        List<UcWxqySuite> expectedList = new ArrayList<>();
        UcWxqySuite suite1 = new UcWxqySuite();
        suite1.setQysSuiteid("suite-1");
        suite1.setQysSuiteSecret("secret-1");
        
        UcWxqySuite suite2 = new UcWxqySuite();
        suite2.setQysSuiteid("suite-2");
        suite2.setQysSuiteSecret("secret-2");
        
        expectedList.add(suite1);
        expectedList.add(suite2);
        
        // 配置Mock行为
        when(baseMapper.queryAllByLimit(anyInt(), anyInt())).thenReturn(expectedList);
        
        // 执行测试方法
        List<UcWxqySuite> result = ucWxqySuiteService.queryAllByLimit(0, 10);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("suite-1", result.get(0).getQysSuiteid());
        assertEquals("secret-1", result.get(0).getQysSuiteSecret());
        assertEquals("suite-2", result.get(1).getQysSuiteid());
        
        // 验证方法调用
        verify(baseMapper).queryAllByLimit(0, 10);
    }

    @Test
    @DisplayName("测试通过ID获取单条记录")
    void testGetById() {
        // 准备测试数据
        UcWxqySuite expectedSuite = new UcWxqySuite();
        expectedSuite.setQysSuiteid("test-id");
        expectedSuite.setQysSuiteSecret("test-secret");
        
        // 配置Mock行为 - 使用父类BaseMapper的方法
        when(baseMapper.selectById(anyString())).thenReturn(expectedSuite);
        
        // 执行测试方法
        UcWxqySuite result = ucWxqySuiteService.getById("test-id");
        
        // 验证结果
        assertNotNull(result);
        assertEquals("test-id", result.getQysSuiteid());
        assertEquals("test-secret", result.getQysSuiteSecret());
        
        // 验证方法调用
        verify(baseMapper).selectById("test-id");
    }

    @Test
    @DisplayName("测试保存记录")
    void testSave() {
        // 准备测试数据
        UcWxqySuite suiteToSave = new UcWxqySuite();
        suiteToSave.setQysSuiteid("new-suite");
        suiteToSave.setQysSuiteSecret("new-secret");
        
        // 配置Mock行为 - 使用父类BaseMapper的方法
        when(baseMapper.insert(any(UcWxqySuite.class))).thenReturn(1);
        
        // 执行测试方法
        boolean result = ucWxqySuiteService.save(suiteToSave);
        
        // 验证结果
        assertEquals(true, result);
        
        // 验证方法调用
        verify(baseMapper).insert(suiteToSave);
    }
} 