package com.changyi.chy.persistence.page;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * 分页请求参数
 *
 * @author YuRuizhi
 */
@Data
public class PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 默认页码
     */
    public static final int DEFAULT_PAGE_NUMBER = 1;

    /**
     * 默认每页记录数
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 最大每页记录数
     */
    public static final int MAX_PAGE_SIZE = 100;

    /**
     * 当前页码
     */
    @Min(value = 1, message = "页码不能小于1")
    private Integer current = DEFAULT_PAGE_NUMBER;

    /**
     * 每页记录数
     */
    @Min(value = 1, message = "每页条数不能小于1")
    @Max(value = MAX_PAGE_SIZE, message = "每页条数不能大于" + MAX_PAGE_SIZE)
    private Integer size = DEFAULT_PAGE_SIZE;

    /**
     * 排序字段
     */
    private String orderField;

    /**
     * 排序方式，true表示升序，false表示降序
     */
    private Boolean isAsc = true;

    /**
     * 获取起始行
     *
     * @return 起始行
     */
    public int getOffset() {
        return (current - 1) * size;
    }
} 