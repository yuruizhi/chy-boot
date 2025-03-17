package com.changyi.chy.commons;

import com.changyi.chy.common.support.*;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 工具类测试
 */
public class UtilTest {

    @Test
    public void testStringUtil() {
        // 测试格式化方法
        String result = StringUtil.format("Hello {} {}", "World", "Test");
        Assert.assertEquals("Hello World Test", result);
    }

    @Test
    public void testDateUtil() {
        // 测试日期格式化
        Date now = new Date();
        String dateStr = DateUtil.formatDate(now);
        Assert.assertNotNull(dateStr);
        
        // 测试日期解析
        Date parsed = DateUtil.parse(dateStr, DateUtil.PATTERN_DATE);
        Assert.assertNotNull(parsed);
        
        // 测试plusDays方法
        Date tomorrow = DateUtil.plusDays(now, 1);
        Assert.assertTrue(tomorrow.after(now));
    }

    @Test
    public void testDateTimeUtil() {
        // 测试时间格式化
        LocalDateTime now = LocalDateTime.now();
        String formatted = DateTimeUtil.formatDateTime(now);
        Assert.assertNotNull(formatted);
        
        // 测试时间解析
        String dateTimeStr = now.format(DateTimeFormatter.ofPattern(DateUtil.PATTERN_DATETIME));
        LocalDateTime parsed = DateTimeUtil.parse(dateTimeStr, DateUtil.PATTERN_DATETIME);
        Assert.assertNotNull(parsed);
    }

    @Test
    public void testBeanUtil() {
        // 测试Map转Bean
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Test");
        map.put("value", 100);
        
        TestBean bean = BeanUtil.toBean(map, TestBean.class);
        Assert.assertEquals("Test", bean.getName());
        Assert.assertEquals(100, bean.getValue());
        
        // 测试Bean转Map
        Map<String, Object> resultMap = BeanUtil.toMap(bean);
        Assert.assertEquals("Test", resultMap.get("name"));
        Assert.assertEquals(100, resultMap.get("value"));
        
        // 测试对象复制
        TestBean copy = BeanUtil.copy(bean, TestBean.class);
        Assert.assertEquals("Test", copy.getName());
        Assert.assertEquals(100, copy.getValue());
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