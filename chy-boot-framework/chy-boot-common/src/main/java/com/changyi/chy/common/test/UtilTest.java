package com.changyi.chy.common.test;

import com.changyi.chy.common.support.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 工具类测试
 */
public class UtilTest {

    public static void main(String[] args) {
        testStringUtil();
        testDateUtil();
        testDateTimeUtil();
        testBeanUtil();
        System.out.println("所有测试通过！");
    }

    public static void testStringUtil() {
        // 测试格式化方法
        String result = StringUtil.format("Hello {} {}", "World", "Test");
        if (!"Hello World Test".equals(result)) {
            throw new AssertionError("StringUtil.format 测试失败");
        }
        System.out.println("StringUtil 测试通过");
    }

    public static void testDateUtil() {
        // 测试日期格式化
        Date now = new Date();
        String dateStr = DateUtil.formatDate(now);
        if (dateStr == null || dateStr.isEmpty()) {
            throw new AssertionError("DateUtil.formatDate 测试失败");
        }
        
        // 测试日期解析
        Date parsed = DateUtil.parse(dateStr, DateUtil.PATTERN_DATE);
        if (parsed == null) {
            throw new AssertionError("DateUtil.parse 测试失败");
        }
        
        // 测试plusDays方法
        Date tomorrow = DateUtil.plusDays(now, 1);
        if (!tomorrow.after(now)) {
            throw new AssertionError("DateUtil.plusDays 测试失败");
        }
        System.out.println("DateUtil 测试通过");
    }

    public static void testDateTimeUtil() {
        // 测试时间格式化
        LocalDateTime now = LocalDateTime.now();
        String formatted = DateTimeUtil.formatDateTime(now);
        if (formatted == null || formatted.isEmpty()) {
            throw new AssertionError("DateTimeUtil.formatDateTime 测试失败");
        }
        
        // 测试时间解析
        try {
            String dateTimeStr = "2023-03-17 12:30:45";
            LocalDateTime parsed = DateTimeUtil.parse(dateTimeStr, DateUtil.PATTERN_DATETIME);
            if (parsed == null) {
                throw new AssertionError("DateTimeUtil.parse 测试失败");
            }
        } catch (Exception e) {
            throw new AssertionError("DateTimeUtil.parse 测试异常: " + e.getMessage());
        }
        System.out.println("DateTimeUtil 测试通过");
    }

    public static void testBeanUtil() {
        // 测试Map转Bean
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Test");
        map.put("value", 100);
        
        TestBean bean = BeanUtil.toBean(map, TestBean.class);
        if (!"Test".equals(bean.getName()) || bean.getValue() != 100) {
            throw new AssertionError("BeanUtil.toBean 测试失败");
        }
        
        // 测试Bean转Map
        Map<String, Object> resultMap = BeanUtil.toMap(bean);
        if (!"Test".equals(resultMap.get("name")) || !Integer.valueOf(100).equals(resultMap.get("value"))) {
            throw new AssertionError("BeanUtil.toMap 测试失败");
        }
        
        // 测试对象复制
        TestBean copy = BeanUtil.copy(bean, TestBean.class);
        if (!"Test".equals(copy.getName()) || copy.getValue() != 100) {
            throw new AssertionError("BeanUtil.copy 测试失败");
        }
        System.out.println("BeanUtil 测试通过");
    }
    
    /**
     * 测试Bean
     */
    public static class TestBean {
        private String name;
        private int value;
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public int getValue() {
            return value;
        }
        
        public void setValue(int value) {
            this.value = value;
        }
    }
} 