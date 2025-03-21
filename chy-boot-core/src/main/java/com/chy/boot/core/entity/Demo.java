package com.chy.boot.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class Demo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private String id;

    @Schema(description = "数据状态: 1-正常, 0-删除")
    private Integer status;

    @Schema(description = "创建时间")
    private Long createTime;

    @Schema(description = "修改时间")
    private Long updateTime;
}