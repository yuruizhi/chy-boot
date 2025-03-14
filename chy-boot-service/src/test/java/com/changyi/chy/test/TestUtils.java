package com.changyi.chy.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * 测试工具类
 * 提供通用的测试辅助方法
 *
 * @author YuRuizhi
 */
public class TestUtils {
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Random random = new Random();
    
    static {
        objectMapper.registerModule(new JavaTimeModule());
    }
    
    /**
     * 将对象转换为JSON字符串
     *
     * @param obj 要转换的对象
     * @return JSON字符串
     * @throws JsonProcessingException 如果转换失败
     */
    public static String toJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
    
    /**
     * 为MockHttpServletRequestBuilder添加JSON内容
     *
     * @param builder 请求构建器
     * @param obj 要转换为JSON的对象
     * @return 修改后的请求构建器
     * @throws JsonProcessingException 如果转换失败
     */
    public static MockHttpServletRequestBuilder jsonContent(MockHttpServletRequestBuilder builder, Object obj) 
            throws JsonProcessingException {
        return builder.contentType(MediaType.APPLICATION_JSON)
                .content(toJson(obj));
    }
    
    /**
     * 生成随机字符串
     *
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String randomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
    
    /**
     * 生成随机整数
     *
     * @param min 最小值（包含）
     * @param max 最大值（包含）
     * @return 随机整数
     */
    public static int randomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
    
    /**
     * 生成当前时间前后的随机时间
     *
     * @param daysBefore 向前偏移的最大天数
     * @param daysAfter 向后偏移的最大天数
     * @return 随机时间
     */
    public static LocalDateTime randomDateTime(int daysBefore, int daysAfter) {
        long minDay = -1L * daysBefore;
        long maxDay = daysAfter;
        long randomDay = minDay + random.nextInt((int)(maxDay - minDay + 1));
        
        return LocalDateTime.now().plusDays(randomDay)
                .plusHours(random.nextInt(24))
                .plusMinutes(random.nextInt(60))
                .plusSeconds(random.nextInt(60));
    }
} 