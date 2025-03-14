package com.changyi.chy.persistence.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 查询条件构建器
 *
 * @author YuRuizhi
 */
public class QueryBuilder {

    /**
     * 创建一个新的QueryWrapper
     *
     * @param <T> 实体类型
     * @return QueryWrapper实例
     */
    public static <T> QueryWrapper<T> query() {
        return new QueryWrapper<>();
    }

    /**
     * 创建一个新的LambdaQueryWrapper
     *
     * @param <T> 实体类型
     * @return LambdaQueryWrapper实例
     */
    public static <T> LambdaQueryWrapper<T> lambdaQuery() {
        return new LambdaQueryWrapper<>();
    }

    /**
     * 创建一个新的LambdaQueryWrapper，并进行自定义设置
     *
     * @param consumer LambdaQueryWrapper配置逻辑
     * @param <T>      实体类型
     * @return 配置后的LambdaQueryWrapper
     */
    public static <T> LambdaQueryWrapper<T> lambdaQuery(Consumer<LambdaQueryWrapper<T>> consumer) {
        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
        consumer.accept(wrapper);
        return wrapper;
    }

    /**
     * 添加非空条件（等于）
     *
     * @param wrapper   查询条件包装器
     * @param column    字段
     * @param value     值
     * @param <T>       实体类型
     * @param <R>       字段类型
     * @return 查询条件包装器
     */
    public static <T, R> LambdaQueryWrapper<T> eq(LambdaQueryWrapper<T> wrapper, SFunction<T, R> column, Object value) {
        if (value != null) {
            if (value instanceof String && !StringUtils.hasText((String) value)) {
                return wrapper;
            }
            wrapper.eq(column, value);
        }
        return wrapper;
    }

    /**
     * 添加非空条件（不等于）
     *
     * @param wrapper   查询条件包装器
     * @param column    字段
     * @param value     值
     * @param <T>       实体类型
     * @param <R>       字段类型
     * @return 查询条件包装器
     */
    public static <T, R> LambdaQueryWrapper<T> ne(LambdaQueryWrapper<T> wrapper, SFunction<T, R> column, Object value) {
        if (value != null) {
            if (value instanceof String && !StringUtils.hasText((String) value)) {
                return wrapper;
            }
            wrapper.ne(column, value);
        }
        return wrapper;
    }

    /**
     * 添加非空条件（大于）
     *
     * @param wrapper   查询条件包装器
     * @param column    字段
     * @param value     值
     * @param <T>       实体类型
     * @param <R>       字段类型
     * @return 查询条件包装器
     */
    public static <T, R> LambdaQueryWrapper<T> gt(LambdaQueryWrapper<T> wrapper, SFunction<T, R> column, Object value) {
        if (value != null) {
            wrapper.gt(column, value);
        }
        return wrapper;
    }

    /**
     * 添加非空条件（大于等于）
     *
     * @param wrapper   查询条件包装器
     * @param column    字段
     * @param value     值
     * @param <T>       实体类型
     * @param <R>       字段类型
     * @return 查询条件包装器
     */
    public static <T, R> LambdaQueryWrapper<T> ge(LambdaQueryWrapper<T> wrapper, SFunction<T, R> column, Object value) {
        if (value != null) {
            wrapper.ge(column, value);
        }
        return wrapper;
    }

    /**
     * 添加非空条件（小于）
     *
     * @param wrapper   查询条件包装器
     * @param column    字段
     * @param value     值
     * @param <T>       实体类型
     * @param <R>       字段类型
     * @return 查询条件包装器
     */
    public static <T, R> LambdaQueryWrapper<T> lt(LambdaQueryWrapper<T> wrapper, SFunction<T, R> column, Object value) {
        if (value != null) {
            wrapper.lt(column, value);
        }
        return wrapper;
    }

    /**
     * 添加非空条件（小于等于）
     *
     * @param wrapper   查询条件包装器
     * @param column    字段
     * @param value     值
     * @param <T>       实体类型
     * @param <R>       字段类型
     * @return 查询条件包装器
     */
    public static <T, R> LambdaQueryWrapper<T> le(LambdaQueryWrapper<T> wrapper, SFunction<T, R> column, Object value) {
        if (value != null) {
            wrapper.le(column, value);
        }
        return wrapper;
    }

    /**
     * 添加非空条件（包含）
     *
     * @param wrapper   查询条件包装器
     * @param column    字段
     * @param value     值
     * @param <T>       实体类型
     * @param <R>       字段类型
     * @return 查询条件包装器
     */
    public static <T, R> LambdaQueryWrapper<T> like(LambdaQueryWrapper<T> wrapper, SFunction<T, R> column, String value) {
        if (StringUtils.hasText(value)) {
            wrapper.like(column, value);
        }
        return wrapper;
    }

    /**
     * 添加非空条件（左包含）
     *
     * @param wrapper   查询条件包装器
     * @param column    字段
     * @param value     值
     * @param <T>       实体类型
     * @param <R>       字段类型
     * @return 查询条件包装器
     */
    public static <T, R> LambdaQueryWrapper<T> likeLeft(LambdaQueryWrapper<T> wrapper, SFunction<T, R> column, String value) {
        if (StringUtils.hasText(value)) {
            wrapper.likeLeft(column, value);
        }
        return wrapper;
    }

    /**
     * 添加非空条件（右包含）
     *
     * @param wrapper   查询条件包装器
     * @param column    字段
     * @param value     值
     * @param <T>       实体类型
     * @param <R>       字段类型
     * @return 查询条件包装器
     */
    public static <T, R> LambdaQueryWrapper<T> likeRight(LambdaQueryWrapper<T> wrapper, SFunction<T, R> column, String value) {
        if (StringUtils.hasText(value)) {
            wrapper.likeRight(column, value);
        }
        return wrapper;
    }

    /**
     * 添加非空条件（IN）
     *
     * @param wrapper   查询条件包装器
     * @param column    字段
     * @param values    值集合
     * @param <T>       实体类型
     * @param <R>       字段类型
     * @return 查询条件包装器
     */
    public static <T, R> LambdaQueryWrapper<T> in(LambdaQueryWrapper<T> wrapper, SFunction<T, R> column, Collection<?> values) {
        if (values != null && !values.isEmpty()) {
            wrapper.in(column, values);
        }
        return wrapper;
    }

    /**
     * 添加非空条件（NOT IN）
     *
     * @param wrapper   查询条件包装器
     * @param column    字段
     * @param values    值集合
     * @param <T>       实体类型
     * @param <R>       字段类型
     * @return 查询条件包装器
     */
    public static <T, R> LambdaQueryWrapper<T> notIn(LambdaQueryWrapper<T> wrapper, SFunction<T, R> column, Collection<?> values) {
        if (values != null && !values.isEmpty()) {
            wrapper.notIn(column, values);
        }
        return wrapper;
    }

    /**
     * 添加非空条件（BETWEEN）
     *
     * @param wrapper   查询条件包装器
     * @param column    字段
     * @param begin     开始值
     * @param end       结束值
     * @param <T>       实体类型
     * @param <R>       字段类型
     * @return 查询条件包装器
     */
    public static <T, R> LambdaQueryWrapper<T> between(LambdaQueryWrapper<T> wrapper, SFunction<T, R> column, Object begin, Object end) {
        if (begin != null && end != null) {
            wrapper.between(column, begin, end);
        } else if (begin != null) {
            wrapper.ge(column, begin);
        } else if (end != null) {
            wrapper.le(column, end);
        }
        return wrapper;
    }

    /**
     * 根据条件决定是否添加条件
     *
     * @param wrapper   查询条件包装器
     * @param condition 条件
     * @param consumer  条件消费者
     * @param <T>       实体类型
     * @return 查询条件包装器
     */
    public static <T> LambdaQueryWrapper<T> condition(LambdaQueryWrapper<T> wrapper, boolean condition, Consumer<LambdaQueryWrapper<T>> consumer) {
        if (condition) {
            consumer.accept(wrapper);
        }
        return wrapper;
    }

    /**
     * 根据对象非空决定是否添加条件
     *
     * @param wrapper   查询条件包装器
     * @param value     值
     * @param consumer  条件消费者
     * @param <T>       实体类型
     * @return 查询条件包装器
     */
    public static <T> LambdaQueryWrapper<T> conditionNotNull(LambdaQueryWrapper<T> wrapper, Object value, Consumer<LambdaQueryWrapper<T>> consumer) {
        return condition(wrapper, Objects.nonNull(value), consumer);
    }

    /**
     * 根据字符串非空决定是否添加条件
     *
     * @param wrapper   查询条件包装器
     * @param value     值
     * @param consumer  条件消费者
     * @param <T>       实体类型
     * @return 查询条件包装器
     */
    public static <T> LambdaQueryWrapper<T> conditionNotEmpty(LambdaQueryWrapper<T> wrapper, String value, Consumer<LambdaQueryWrapper<T>> consumer) {
        return condition(wrapper, StringUtils.hasText(value), consumer);
    }

    /**
     * 根据集合非空决定是否添加条件
     *
     * @param wrapper    查询条件包装器
     * @param collection 集合
     * @param consumer   条件消费者
     * @param <T>        实体类型
     * @return 查询条件包装器
     */
    public static <T> LambdaQueryWrapper<T> conditionNotEmpty(LambdaQueryWrapper<T> wrapper, Collection<?> collection, Consumer<LambdaQueryWrapper<T>> consumer) {
        return condition(wrapper, collection != null && !collection.isEmpty(), consumer);
    }
} 