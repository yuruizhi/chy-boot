package com.chy.boot.rest.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 示例表(Demo)实体类
 *
 * @author YuRuizhi
 * @since 2021-01-14 14:34:22
 * @update 2023/03/01 更新为MyBatis-Plus实体
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
    private String demoId;
    /**
     * 名称
     */
    @Schema(description = "名称")
    private String demoName;
    /**
     * 描述
     */
    @Schema(description = "描述")
    private String demoDescription;
    /**
     * 标识码
     */
    @Schema(description = "标识码")
    private String demoCode;
    /**
     * 内容
     */
    @Schema(description = "内容")
    private String demoContent;
    /**
     * 配置参数
     */
    @Schema(description = "配置参数")
    private Object demoConfig;
    /**
     * 令牌
     */
    @Schema(description = "令牌")
    private String demoToken;
    /**
     * 过期时间
     */
    @Schema(description = "过期时间")
    private Object demoExpires;
    /**
     * 授权码
     */
    @Schema(description = "授权码")
    private String demoAuthCode;
    /**
     * 授权过期时间
     */
    @Schema(description = "授权过期时间")
    private Object demoAuthExpires;
    /**
     * 所属分类
     */
    @Schema(description = "所属分类")
    private String demoCategory;
    /**
     * 记录状态, 1初始化，2=已更新, 3=已删除
     */
    @Schema(description = "记录状态, 1初始化，2=已更新, 3=已删除")
    private Object demoStatus;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Object demoCreated;
    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Object demoUpdated;
    /**
     * 删除时间
     */
    @Schema(description = "删除时间")
    private Object demoDeleted;
}