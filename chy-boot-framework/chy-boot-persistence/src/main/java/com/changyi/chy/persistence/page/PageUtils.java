package com.changyi.chy.persistence.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页工具类
 *
 * @author YuRuizhi
 */
public class PageUtils {

    /**
     * 将PageRequest转换为MyBatis-Plus的Page对象
     *
     * @param request 分页请求参数
     * @param <T>     实体类型
     * @return MyBatis-Plus的Page对象
     */
    public static <T> Page<T> convertToPage(PageRequest request) {
        Page<T> page = new Page<>(request.getCurrent(), request.getSize());
        
        // 设置排序
        if (StringUtils.hasText(request.getOrderField())) {
            if (Boolean.TRUE.equals(request.getIsAsc())) {
                page.addOrder(com.baomidou.mybatisplus.core.metadata.OrderItem.asc(request.getOrderField()));
            } else {
                page.addOrder(com.baomidou.mybatisplus.core.metadata.OrderItem.desc(request.getOrderField()));
            }
        }
        
        return page;
    }

    /**
     * 将MyBatis-Plus的IPage对象转换为PageResult
     *
     * @param page MyBatis-Plus的分页结果
     * @param <T>  实体类型
     * @return 分页结果
     */
    public static <T> PageResult<T> convertToPageResult(IPage<T> page) {
        return PageResult.of(page);
    }

    /**
     * 将实体分页结果转换为DTO分页结果
     *
     * @param page    实体分页结果
     * @param mapper  实体到DTO的转换函数
     * @param <T>     实体类型
     * @param <R>     DTO类型
     * @return DTO分页结果
     */
    public static <T, R> PageResult<R> convertToDto(IPage<T> page, Function<T, R> mapper) {
        List<R> records = page.getRecords().stream()
                .map(mapper)
                .collect(Collectors.toList());
        
        return PageResult.of(page.getCurrent(), page.getSize(), page.getTotal(), records);
    }

    /**
     * 创建空的分页结果
     *
     * @param <T> 数据类型
     * @return 空的分页结果
     */
    public static <T> PageResult<T> empty() {
        return new PageResult<>();
    }

    /**
     * 手动分页
     *
     * @param list    需要分页的列表
     * @param current 当前页码
     * @param size    每页大小
     * @param <T>     数据类型
     * @return 分页结果
     */
    public static <T> PageResult<T> manualPaging(List<T> list, long current, long size) {
        if (list == null || list.isEmpty()) {
            return empty();
        }
        
        long total = list.size();
        long pages = total / size;
        if (total % size != 0) {
            pages++;
        }
        
        if (current > pages) {
            current = pages;
        }
        
        long fromIndex = (current - 1) * size;
        if (fromIndex >= total) {
            return PageResult.of(current, size, total, new ArrayList<>());
        }
        
        long toIndex = current * size;
        if (toIndex > total) {
            toIndex = total;
        }
        
        List<T> pageList = list.subList((int) fromIndex, (int) toIndex);
        return PageResult.of(current, size, total, pageList);
    }
} 