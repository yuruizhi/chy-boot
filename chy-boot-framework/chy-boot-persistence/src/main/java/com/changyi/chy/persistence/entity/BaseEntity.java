package com.changyi.chy.persistence.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.changyi.chy.common.constant.CommonConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类
 * 
 * @author YuRuizhi
 */
@Getter
@Setter
@ToString
@Schema(description = "基础实体类")
public abstract class BaseEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键ID")
    private String id;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建人")
    private String createBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新人")
    private String updateBy;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 删除标记（0：正常；1：删除）
     */
    @TableLogic
    @Schema(description = "删除标记（0：正常；1：删除）")
    private Integer deleted = CommonConstant.DB_NOT_DELETED;
    
    /**
     * 乐观锁版本号
     */
    @Version
    @Schema(description = "乐观锁版本号")
    private Integer version = 1;
    
    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private String tenantId;
    
    /**
     * 状态（1：正常；0：禁用）
     */
    @Schema(description = "状态（1：正常；0：禁用）")
    private Integer status = CommonConstant.DB_STATUS_NORMAL;
    
    /**
     * 是否是系统内置（1：是；0：否）
     */
    @Schema(description = "是否是系统内置（1：是；0：否）")
    private Integer isSystem = 0;
} 