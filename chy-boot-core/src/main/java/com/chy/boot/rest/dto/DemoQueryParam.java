package com.chy.boot.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Demo查询参数
 *
 * @author YuRuizhi
 * @since 2024-06-14
 */
@Data
@Schema(description = "Demo查询参数")
public class DemoQueryParam {

    @Schema(description = "名称(模糊查询)")
    private String demoName;

    @Schema(description = "标识码(精确查询)")
    private String demoCode;

    @Schema(description = "分类")
    private String demoCategory;

    @Schema(description = "状态")
    private Integer demoStatus;

    @Schema(description = "创建开始时间")
    private String startTime;

    @Schema(description = "创建结束时间")
    private String endTime;
} 