package com.chy.boot.common.commons.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JDK 21新特性单元测试
 *
 * @author YuRuizhi
 * @date 2024/3/17
 */
class Java21FeaturesTest {

    /**
     * 测试PatternMatchUtil中的模式匹配功能
     */
    @Test
    @DisplayName("测试模式匹配功能")
    void testPatternMatching() {
        // 测试不同类型的值
        assertEquals("N/A", PatternMatchUtil.formatValue(null));
        assertEquals("测试字符串", PatternMatchUtil.formatValue("测试字符串"));
        assertEquals("空字符串", PatternMatchUtil.formatValue(""));
        assertEquals("整数: 42", PatternMatchUtil.formatValue(42));
        assertEquals("长整数: 9223372036854775807", PatternMatchUtil.formatValue(Long.MAX_VALUE));
        assertEquals("小数: 3.14", PatternMatchUtil.formatValue(3.14));
        assertEquals("是", PatternMatchUtil.formatValue(true));
        assertEquals("否", PatternMatchUtil.formatValue(false));
        
        // 测试集合类型
        Map<String, Integer> testMap = new HashMap<>();
        testMap.put("key1", 1);
        testMap.put("key2", 2);
        assertEquals("Map(大小=2)", PatternMatchUtil.formatValue(testMap));
        
        // 测试数组类型
        String[] testArray = {"a", "b", "c"};
        assertEquals("数组(长度=3)", PatternMatchUtil.formatValue(testArray));
    }
    
    /**
     * 测试processObject方法的模式匹配功能
     */
    @Test
    @DisplayName("测试条件模式匹配")
    void testConditionalPatternMatching() {
        assertEquals("字符串: TEST", PatternMatchUtil.processObject("test"));
        assertEquals("其他类型或空值", PatternMatchUtil.processObject(""));
        assertEquals("正数: 10", PatternMatchUtil.processObject(10));
        assertEquals("其他类型或空值", PatternMatchUtil.processObject(-5));
        assertEquals("布尔值: 真", PatternMatchUtil.processObject(true));
        assertEquals("其他类型或空值", PatternMatchUtil.processObject(false));
    }
    
    /**
     * 测试Record Pattern功能
     */
    @Test
    @DisplayName("测试Record Pattern")
    void testRecordPattern() {
        // 测试大数值键值对
        PatternMatchUtil.Pair largePair = new PatternMatchUtil.Pair("count", 200);
        assertEquals("大数值键值对: count=200", PatternMatchUtil.processKeyValue(largePair));
        
        // 测试普通字符串键值对
        PatternMatchUtil.Pair normalPair = new PatternMatchUtil.Pair("name", "张三");
        assertEquals("字符串键值对: name=张三", PatternMatchUtil.processKeyValue(normalPair));
        
        // 测试非字符串键的键值对
        PatternMatchUtil.Pair numberKeyPair = new PatternMatchUtil.Pair(123, "值");
        assertEquals("未知的键值对类型", PatternMatchUtil.processKeyValue(numberKeyPair));
    }
    
    /**
     * 测试结构化并发 - executeAllOrFail
     */
    @Test
    @DisplayName("测试结构化并发 - 全部执行")
    void testStructuredConcurrencyAllOrFail() throws Exception {
        // 创建一组任务
        List<Supplier<String>> tasks = List.of(
            () -> {
                sleep(100);
                return "任务1结果";
            },
            () -> {
                sleep(50);
                return "任务2结果";
            },
            () -> {
                sleep(150);
                return "任务3结果";
            }
        );
        
        // 执行所有任务
        List<String> results = ConcurrencyUtil.executeAllOrFail(tasks);
        
        // 验证结果
        assertEquals(3, results.size());
        assertTrue(results.contains("任务1结果"));
        assertTrue(results.contains("任务2结果"));
        assertTrue(results.contains("任务3结果"));
    }
    
    /**
     * 测试结构化并发 - executeAnySuccess
     */
    @Test
    @DisplayName("测试结构化并发 - 最快成功")
    void testStructuredConcurrencyAnySuccess() throws Exception {
        // 创建一组任务
        List<Supplier<String>> tasks = List.of(
            () -> {
                sleep(100);
                return "慢任务";
            },
            () -> {
                sleep(50);
                return "最快任务";
            },
            () -> {
                sleep(150);
                return "最慢任务";
            }
        );
        
        // 执行任务，获取最快的结果
        String result = ConcurrencyUtil.executeAnySuccess(tasks, Duration.ofSeconds(1));
        
        // 验证结果 - 应该是第二个任务的结果
        assertEquals("最快任务", result);
    }
    
    /**
     * 测试结构化并发 - 执行超时
     */
    @Test
    @DisplayName("测试结构化并发 - 执行超时")
    void testStructuredConcurrencyTimeout() {
        // 创建一组长时间运行的任务
        List<Supplier<String>> tasks = List.of(
            () -> {
                sleep(800);
                return "任务1";
            },
            () -> {
                sleep(900);
                return "任务2";
            }
        );
        
        // 设置很短的超时时间，应该触发超时异常
        assertThrows(RuntimeException.class, () -> 
            ConcurrencyUtil.executeAnySuccess(tasks, Duration.ofMillis(10))
        );
    }
    
    /**
     * 辅助方法：休眠指定毫秒
     */
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
} 