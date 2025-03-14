package com.changyi.chy.api.business.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 示例响应对象
 *
 * @author YuRuizhi
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "示例响应对象")
public class DemoResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Integer id;

    /**
     * UUID
     */
    @Schema(description = "UUID")
    private String uuid;

    /**
     * 内容
     */
    @Schema(description = "内容")
    private String content;

    /**
     * 类型
     */
    @Schema(description = "类型")
    private String type;

    /**
     * 来源
     */
    @Schema(description = "来源")
    private String source;

    /**
     * 作者
     */
    @Schema(description = "作者")
    private String author;

    /**
     * 创建者
     */
    @Schema(description = "创建者")
    private String creator;

    /**
     * 创建者ID
     */
    @Schema(description = "创建者ID")
    private Integer creatorId;

    /**
     * 审核者
     */
    @Schema(description = "审核者")
    private Integer reviewer;

    /**
     * 提交来源
     */
    @Schema(description = "提交来源")
    private String commitFrom;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    /**
     * 长度
     */
    @Schema(description = "长度")
    private Integer length;
} 