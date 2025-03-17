package com.chy.boot.rest.dto;

import com.chy.boot.common.commons.component.validate.ValidateMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Demo创建数据传输对象
 *
 * @author YuRuizhi
 * @since 2024-06-14
 */
@Data
@Schema(description = "Demo创建数据传输对象")
public class CreateDemoDTO {

    @NotBlank(message = ValidateMessage.NotBlank)
    @Size(max = 50, message = ValidateMessage.MaxLength)
    @Schema(description = "名称")
    private String demoName;

    @Size(max = 500, message = ValidateMessage.MaxLength)
    @Schema(description = "描述")
    private String demoDescription;

    @NotBlank(message = ValidateMessage.NotBlank)
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "标识码只能包含大写字母、数字和下划线")
    @Size(max = 30, message = ValidateMessage.MaxLength)
    @Schema(description = "标识码")
    private String demoCode;

    @Size(max = 2000, message = ValidateMessage.MaxLength)
    @Schema(description = "内容")
    private String demoContent;

    @Schema(description = "配置参数(JSON格式)")
    private String demoConfig;

    @Schema(description = "所属分类")
    private String demoCategory;
} 