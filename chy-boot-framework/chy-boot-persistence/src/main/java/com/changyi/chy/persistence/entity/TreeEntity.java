package com.changyi.chy.persistence.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形结构实体基类
 *
 * @author YuRuizhi
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "树形结构实体基类")
public abstract class TreeEntity<E> extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 父级ID
     */
    @Schema(description = "父级ID")
    private String parentId;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;

    /**
     * 层级
     */
    @Schema(description = "层级")
    private Integer level;

    /**
     * 子节点
     */
    @TableField(exist = false)
    @Schema(description = "子节点")
    private List<E> children = new ArrayList<>();

    /**
     * 是否是根节点
     */
    public boolean isRoot() {
        return parentId == null || "0".equals(parentId) || "".equals(parentId);
    }
    
    /**
     * 是否有子节点
     */
    @TableField(exist = false)
    public boolean hasChildren() {
        return this.children != null && !this.children.isEmpty();
    }
} 