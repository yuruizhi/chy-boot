package com.changyi.chy.api.business.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 示例数据传输对象
 *
 * @author YuRuizhi
 */
@Data
@Schema(description = "示例数据传输对象")
public class DemoDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private String id;

    /**
     * 示例名称
     */
    @NotBlank(message = "名称不能为空")
    @Size(min = 2, max = 50, message = "名称长度必须在2-50之间")
    @Schema(description = "示例名称", required = true)
    private String name;
    
    /**
     * 示例类型
     */
    @Schema(description = "示例类型")
    private Integer type;
    
    /**
     * 示例内容
     */
    @Schema(description = "示例内容")
    private String content;
    
    /**
     * 状态（1：正常；0：禁用）
     */
    @Schema(description = "状态（1：正常；0：禁用）")
    private Integer status;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
} 