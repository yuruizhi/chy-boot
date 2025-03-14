package com.changyi.chy.persistence.util;

import com.changyi.chy.persistence.entity.TreeEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 树形结构转换工具类
 *
 * @author YuRuizhi
 */
public class TreeUtils {

    /**
     * 列表转树形结构
     *
     * @param list      原始列表
     * @param <T>       实体类型
     * @return 树形结构列表
     */
    public static <T extends TreeEntity<T>> List<T> buildTree(List<T> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        
        // 将列表转换为 id 和实体的映射关系
        Map<String, T> map = list.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(TreeEntity::getId, Function.identity(), (o, n) -> n));
        
        List<T> result = new ArrayList<>();
        
        // 遍历列表，将子节点放入父节点的 children 列表中
        for (T entity : list) {
            if (entity == null) {
                continue;
            }
            
            // 如果是根节点，直接添加到结果列表中
            if (entity.isRoot()) {
                result.add(entity);
                continue;
            }
            
            // 将子节点放入父节点的 children 列表中
            T parent = map.get(entity.getParentId());
            if (parent != null) {
                parent.getChildren().add(entity);
            } else {
                // 如果找不到父节点，则作为顶级节点
                result.add(entity);
            }
        }
        
        return result;
    }
    
    /**
     * 列表转树形结构，并按指定字段排序
     *
     * @param list     原始列表
     * @param <T>      实体类型
     * @return 排序后的树形结构列表
     */
    public static <T extends TreeEntity<T>> List<T> buildSortedTree(List<T> list) {
        List<T> tree = buildTree(list);
        sortTree(tree);
        return tree;
    }
    
    /**
     * 对树形结构进行排序
     *
     * @param list 树形结构列表
     * @param <T>  实体类型
     */
    public static <T extends TreeEntity<T>> void sortTree(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        
        // 对节点列表按 sort 排序
        list.sort((o1, o2) -> {
            Integer sort1 = o1.getSort() == null ? 0 : o1.getSort();
            Integer sort2 = o2.getSort() == null ? 0 : o2.getSort();
            return sort1.compareTo(sort2);
        });
        
        // 递归对子节点排序
        for (T entity : list) {
            if (entity.hasChildren()) {
                sortTree(entity.getChildren());
            }
        }
    }
    
    /**
     * 获取指定节点的所有子节点ID（包含自身）
     *
     * @param id   节点ID
     * @param list 原始列表
     * @param <T>  实体类型
     * @return 所有子节点ID列表
     */
    public static <T extends TreeEntity<T>> List<String> getChildrenIds(String id, List<T> list) {
        if (list == null || list.isEmpty() || id == null) {
            return Collections.emptyList();
        }
        
        List<String> result = new ArrayList<>();
        result.add(id);
        
        // 递归获取所有子节点ID
        getChildrenIdsRecursively(id, list, result);
        
        return result;
    }
    
    /**
     * 递归获取子节点ID
     *
     * @param parentId 父节点ID
     * @param list     原始列表
     * @param result   结果列表
     * @param <T>      实体类型
     */
    private static <T extends TreeEntity<T>> void getChildrenIdsRecursively(String parentId, List<T> list, List<String> result) {
        for (T entity : list) {
            if (entity != null && parentId.equals(entity.getParentId())) {
                result.add(entity.getId());
                getChildrenIdsRecursively(entity.getId(), list, result);
            }
        }
    }
    
    /**
     * 根据指定的ID查找节点
     *
     * @param id   节点ID
     * @param tree 树形结构
     * @param <T>  实体类型
     * @return 节点
     */
    public static <T extends TreeEntity<T>> T findNodeById(String id, List<T> tree) {
        if (tree == null || tree.isEmpty() || id == null) {
            return null;
        }
        
        for (T node : tree) {
            if (node == null) {
                continue;
            }
            
            if (id.equals(node.getId())) {
                return node;
            }
            
            if (node.hasChildren()) {
                T found = findNodeById(id, node.getChildren());
                if (found != null) {
                    return found;
                }
            }
        }
        
        return null;
    }
    
    /**
     * 获取指定节点的所有父节点ID（不包含自身）
     *
     * @param id   节点ID
     * @param list 原始列表
     * @param <T>  实体类型
     * @return 所有父节点ID列表
     */
    public static <T extends TreeEntity<T>> List<String> getParentIds(String id, List<T> list) {
        if (list == null || list.isEmpty() || id == null) {
            return Collections.emptyList();
        }
        
        List<String> result = new ArrayList<>();
        
        // 将列表转换为 id 和实体的映射关系
        Map<String, T> map = list.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(TreeEntity::getId, Function.identity(), (o, n) -> n));
        
        // 查找当前节点
        T current = map.get(id);
        
        // 递归查找父节点
        while (current != null && !current.isRoot()) {
            String parentId = current.getParentId();
            if (parentId != null && !parentId.isEmpty() && !parentId.equals("0")) {
                result.add(parentId);
                current = map.get(parentId);
            } else {
                break;
            }
        }
        
        return result;
    }
} 