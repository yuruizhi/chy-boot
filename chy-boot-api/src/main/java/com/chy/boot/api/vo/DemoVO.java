package com.chy.boot.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Demo视图对象
 *
 * @author YuRuizhi
 * @date 2024/3/17
 */
@Data
@Schema(description = "Demo视图对象")
public class DemoVO {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "标识码")
    private String code;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "配置参数")
    private String config;

    @Schema(description = "所属分类")
    private String category;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "状态描述")
    private String statusDesc;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    @Schema(description = "更新时间")
    private LocalDateTime updatedTime;
} 