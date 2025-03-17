package com.chy.boot.rest.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chy.boot.common.commons.component.validate.ValidGroup;
import com.chy.boot.common.commons.component.validate.ValidateMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 示例表(Demo)实体类
 *
 * @author YuRuizhi
 * @since 2021-01-14 14:34:22
 * @update 2023/03/01 更新为MyBatis-Plus实体
 * @update 2024/06/14 优化实体设计和字段类型
 */
@Data
@Accessors(chain = true)
@TableName("demo")
@Schema(description = "示例实体")
public class Demo implements Serializable {
    private static final long serialVersionUID = 759760820368851749L;
    /**
     * 主键ID
     */
    @TableId(value = "demo_id", type = IdType.INPUT)
    @Schema(description = "ID")
    @Null(message = ValidateMessage.Null, groups = {ValidGroup.Add.class})
    @NotNull(message = ValidateMessage.NotNull, groups = {ValidGroup.Update.class, ValidGroup.Delete.class})
    private String demoId;
    
    /**
     * 名称
     */
    @Schema(description = "名称")
    @NotBlank(message = ValidateMessage.NotBlank, groups = {ValidGroup.Add.class, ValidGroup.Update.class})
    @Size(max = 50, message = ValidateMessage.MaxLength, groups = {ValidGroup.Add.class, ValidGroup.Update.class})
    private String demoName;
    
    /**
     * 描述
     */
    @Schema(description = "描述")
    @Size(max = 500, message = ValidateMessage.MaxLength, groups = {ValidGroup.Add.class, ValidGroup.Update.class})
    private String demoDescription;
    
    /**
     * 标识码
     */
    @Schema(description = "标识码")
    @Pattern(regexp = "^[A-Z0-9_]+$", message = ValidateMessage.Pattern, 
             groups = {ValidGroup.Add.class, ValidGroup.Update.class})
    @Size(max = 30, message = ValidateMessage.MaxLength, groups = {ValidGroup.Add.class, ValidGroup.Update.class})
    private String demoCode;
    
    /**
     * 内容
     */
    @Schema(description = "内容")
    @Size(max = 2000, message = ValidateMessage.MaxLength, groups = {ValidGroup.Add.class, ValidGroup.Update.class})
    private String demoContent;
    
    /**
     * 配置参数 - JSON格式
     */
    @Schema(description = "配置参数")
    private String demoConfig;
    
    /**
     * 令牌
     */
    @Schema(description = "令牌")
    private String demoToken;
    
    /**
     * 过期时间
     */
    @Schema(description = "过期时间")
    private LocalDateTime demoExpires;
    
    /**
     * 授权码
     */
    @Schema(description = "授权码")
    private String demoAuthCode;
    
    /**
     * 授权过期时间
     */
    @Schema(description = "授权过期时间")
    private LocalDateTime demoAuthExpires;
    
    /**
     * 所属分类
     */
    @Schema(description = "所属分类")
    private String demoCategory;
    
    /**
     * 记录状态, 1初始化，2=已更新, 3=已删除
     */
    @Schema(description = "记录状态")
    @NotNull(message = ValidateMessage.NotNull, groups = {ValidGroup.SetStatus.class})
    private Integer demoStatus;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime demoCreated;
    
    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime demoUpdated;
    
    /**
     * 删除时间
     */
    @Schema(description = "删除时间")
    private LocalDateTime demoDeleted;
    
    /**
     * 激活Demo
     */
    public void activate() {
        this.demoStatus = 1;
        this.demoUpdated = LocalDateTime.now();
    }
    
    /**
     * 停用Demo
     */
    public void deactivate() {
        this.demoStatus = 2;
        this.demoUpdated = LocalDateTime.now();
    }
    
    /**
     * 标记删除
     */
    public void markDeleted() {
        this.demoStatus = 3;
        this.demoDeleted = LocalDateTime.now();
    }
}