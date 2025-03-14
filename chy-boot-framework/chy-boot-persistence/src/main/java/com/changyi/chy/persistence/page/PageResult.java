package com.changyi.chy.persistence.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页查询结果封装
 *
 * @author YuRuizhi
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 当前页码
     */
    private long current;

    /**
     * 每页记录数
     */
    private long size;

    /**
     * 总页数
     */
    private long pages;

    /**
     * 当前页数据
     */
    private List<T> records;

    /**
     * 是否有上一页
     */
    private boolean hasPrevious;

    /**
     * 是否有下一页
     */
    private boolean hasNext;

    /**
     * 创建一个空的分页结果
     */
    public PageResult() {
        this.total = 0L;
        this.current = 1L;
        this.size = 10L;
        this.pages = 0L;
        this.records = Collections.emptyList();
    }

    /**
     * 基于MyBatis-Plus的IPage创建分页结果
     *
     * @param page MyBatis-Plus分页结果
     * @param <T>  数据类型
     * @return 分页结果
     */
    public static <T> PageResult<T> of(IPage<T> page) {
        PageResult<T> result = new PageResult<>();
        result.setTotal(page.getTotal());
        result.setCurrent(page.getCurrent());
        result.setSize(page.getSize());
        result.setPages(page.getPages());
        result.setRecords(page.getRecords());
        result.setHasPrevious(page.getCurrent() > 1);
        result.setHasNext(page.getCurrent() < page.getPages());
        return result;
    }

    /**
     * 创建一个带数据的分页结果
     *
     * @param current  当前页
     * @param size     每页大小
     * @param total    总记录数
     * @param records  数据列表
     * @param <T>      数据类型
     * @return 分页结果
     */
    public static <T> PageResult<T> of(long current, long size, long total, List<T> records) {
        PageResult<T> result = new PageResult<>();
        result.setCurrent(current);
        result.setSize(size);
        result.setTotal(total);
        
        // 计算总页数
        long pages = total / size;
        if (total % size != 0) {
            pages++;
        }
        result.setPages(pages);
        result.setRecords(records);
        result.setHasPrevious(current > 1);
        result.setHasNext(current < pages);
        return result;
    }
} 