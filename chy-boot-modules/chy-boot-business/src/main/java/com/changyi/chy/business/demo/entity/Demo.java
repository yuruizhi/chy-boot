package com.changyi.chy.business.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.changyi.chy.persistence.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 示例实体
 * 
 * @author YuRuizhi
 * @update 2023/03/01 更新为MyBatis-Plus实体
 * @update 2024/03/15 迁移到新项目结构
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("demo")
@Schema(description = "示例实体")
public class Demo extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    /**
     * 示例名称
     */
    @Schema(description = "示例名称")
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
} 