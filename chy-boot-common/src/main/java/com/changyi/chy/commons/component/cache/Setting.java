package com.changyi.chy.commons.component.cache;


import lombok.Data;

import java.io.Serializable;

/**
 * 全局缓存数据结构
 *
 * @author Henry.Yu
 * @date 2020/1/10
 */
@Data
public class Setting implements Serializable {

    private String setId;

    private String setKey;

    private Integer setType;

    private Integer setStatus;

    private Long setCreated;

    private Long setUpdated;

    private Long setDeleted;

    private String setValue;

    private String setComment;

    private static final long serialVersionUID = 1L;
}
