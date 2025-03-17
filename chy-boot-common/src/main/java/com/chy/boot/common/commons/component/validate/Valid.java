package com.chy.boot.common.commons.component.validate;

import lombok.Data;

import java.io.Serializable;

/**
 * jsr自定义校验bean.
 *
 * @author YuRuizhi
 * @date 2020/1/15
 */
@Data
public class Valid implements Serializable {

    private static final long serialVersionUID = 982912973564659307L;

    /**
     * 字段名称
     */
    private String field;

    /**
     * 错误提示
     */
    private String message;
}
