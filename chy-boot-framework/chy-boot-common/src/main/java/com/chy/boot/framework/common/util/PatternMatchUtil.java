package com.chy.boot.common.commons.util;

import java.util.Map;
import java.util.Optional;

/**
 * 模式匹配工具类
 * <p>
 * 提供类型识别和处理功能
 * </p>
 *
 * @author YuRuizhi
 * @date 2024/3/17
 */
public class PatternMatchUtil {

    /**
     * 格式化不同类型的值
     *
     * @param value 要格式化的值（可以是不同类型）
     * @return 格式化后的字符串
     */
    public static String formatValue(Object value) {
        if (value == null) {
            return "N/A";
        } else if (value instanceof String) {
            String s = (String) value;
            return s.isEmpty() ? "空字符串" : s;
        } else if (value instanceof Integer) {
            Integer i = (Integer) value;
            return String.format("整数: %d", i);
        } else if (value instanceof Long) {
            Long l = (Long) value;
            return String.format("长整数: %d", l);
        } else if (value instanceof Double) {
            Double d = (Double) value;
            return String.format("小数: %.2f", d);
        } else if (value instanceof Float) {
            Float f = (Float) value;
            return String.format("小数: %.2f", f);
        } else if (value instanceof Boolean) {
            Boolean b = (Boolean) value;
            return b ? "是" : "否";
        } else if (value instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) value;
            return String.format("Map(大小=%d)", map.size());
        } else if (value instanceof Object[]) {
            Object[] arr = (Object[]) value;
            return String.format("数组(长度=%d)", arr.length);
        } else if (value instanceof Optional) {
            Optional<?> opt = (Optional<?>) value;
            return opt.map(PatternMatchUtil::formatValue).orElse("空值");
        } else {
            return value.toString();
        }
    }

    /**
     * 进行类型检查并处理
     *
     * @param obj 待处理的对象
     * @return 处理结果
     */
    public static String processObject(Object obj) {
        if (obj instanceof String) {
            String s = (String) obj;
            if (!s.isEmpty()) {
                return "字符串: " + s.toUpperCase();
            }
        } else if (obj instanceof Number) {
            Number n = (Number) obj;
            if (n.doubleValue() > 0) {
                return "正数: " + n;
            }
        } else if (obj instanceof Boolean) {
            Boolean b = (Boolean) obj;
            if (b) {
                return "布尔值: 真";
            }
        }
        return "其他类型或空值";
    }
    
    /**
     * 处理键值对
     * 
     * @param pair 键值对对象
     * @return 处理结果
     */
    public static String processKeyValue(Pair pair) {
        Object key = pair.key();
        Object value = pair.value();
        
        if (key instanceof String && value instanceof Integer) {
            String keyStr = (String) key;
            Integer valueInt = (Integer) value;
            
            if (valueInt > 100) {
                return String.format("大数值键值对: %s=%d", keyStr, valueInt);
            }
            return String.format("字符串键值对: %s=%d", keyStr, valueInt);
        } else if (key instanceof String) {
            String keyStr = (String) key;
            return String.format("字符串键值对: %s=%s", keyStr, value);
        }
        
        return "未知的键值对类型";
    }
    
    /**
     * 键值对的record类型定义
     */
    public record Pair(Object key, Object value) {}
} 