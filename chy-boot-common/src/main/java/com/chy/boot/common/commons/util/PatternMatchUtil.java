package com.chy.boot.common.commons.util;

import java.util.Map;
import java.util.Optional;

/**
 * JDK 21模式匹配工具类
 * <p>
 * 展示JDK 21的模式匹配(Pattern Matching)特性
 * </p>
 *
 * @author YuRuizhi
 * @since 2024-03-17
 */
public class PatternMatchUtil {

    /**
     * 使用增强的switch模式匹配格式化不同类型的值
     *
     * @param value 要格式化的值（可以是不同类型）
     * @return 格式化后的字符串
     */
    public static String formatValue(Object value) {
        return switch (value) {
            case null -> "N/A";
            case String s -> s.isEmpty() ? "空字符串" : s;
            case Integer i -> String.format("整数: %d", i);
            case Long l -> String.format("长整数: %d", l);
            case Double d -> String.format("小数: %.2f", d);
            case Float f -> String.format("小数: %.2f", f);
            case Boolean b -> b ? "是" : "否";
            case Map<?, ?> map -> String.format("Map(大小=%d)", map.size());
            case Object[] arr -> String.format("数组(长度=%d)", arr.length);
            case Optional<?> opt -> opt.map(PatternMatchUtil::formatValue).orElse("空值");
            default -> value.toString();
        };
    }

    /**
     * 使用JDK 21的增强型模式匹配进行类型检查并处理
     *
     * @param obj 待处理的对象
     * @return 处理结果
     */
    public static String processObject(Object obj) {
        // 使用模式变量和条件判断的组合
        if (obj instanceof String s && !s.isEmpty()) {
            return "字符串: " + s.toUpperCase();
        } else if (obj instanceof Number n && n.doubleValue() > 0) {
            return "正数: " + n;
        } else if (obj instanceof Boolean b && b) {
            return "布尔值: 真";
        } else {
            return "其他类型或空值";
        }
    }
    
    /**
     * 使用record pattern匹配处理嵌套结构
     * 
     * @param pair 键值对对象
     * @return 处理结果
     */
    public static String processKeyValue(Pair pair) {
        return switch (pair) {
            // 当Pair包含一个String类型的key和Integer类型的value时
            case Pair(String key, Integer value) when value > 100 -> 
                String.format("大数值键值对: %s=%d", key, value);
                
            // 当Pair包含String类型的key和任意类型的value时
            case Pair(String key, var value) -> 
                String.format("字符串键值对: %s=%s", key, value);
                
            // 默认情况
            default -> "未知的键值对类型";
        };
    }
    
    /**
     * 键值对的record类型定义
     */
    public record Pair(Object key, Object value) {}
} 