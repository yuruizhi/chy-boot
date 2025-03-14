package com.changyi.chy.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 示例实体
 * 
 * @author YuRuizhi
 * @update 2023/03/01 更新为MyBatis-Plus实体
 */
@Data
@Accessors(chain = true)
@TableName("demo")
@Schema(description = "示例实体")
public class Demo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    @Schema(description = "ID")
    private String id;

    @Schema(description = "数据状态: 1-正常, 0-删除")
    private Integer status;

    @Schema(description = "创建时间")
    private Long createTime;

    @Schema(description = "修改时间")
    private Long updateTime;
}