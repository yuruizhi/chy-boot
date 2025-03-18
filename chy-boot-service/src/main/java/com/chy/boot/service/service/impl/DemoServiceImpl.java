package com.chy.boot.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.boot.api.dto.request.CreateDemoDTO;
import com.chy.boot.api.dto.request.UpdateDemoDTO;
import com.chy.boot.api.service.DemoService;
import com.chy.boot.api.vo.DemoVO;
import com.chy.boot.common.commons.component.cache.RedisUtil;
import com.chy.boot.rest.core.entity.Demo;
import com.chy.boot.rest.core.mapper.DemoDao;
import com.chy.boot.service.service.converter.DemoConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Demo服务实现类
 * 直接实现api模块中定义的接口
 *
 * @author YuRuizhi
 * @date 2024/03/18
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class DemoServiceImpl extends ServiceImpl<DemoDao, Demo> implements DemoService {

    // 缓存相关常量
    private static final String CACHE_KEY_PREFIX = "demo:entity:";
    private static final String CACHE_LIST_PREFIX = "demo:list:";
    private static final long CACHE_EXPIRE = 3600L; // 1小时
    
    private final DemoConverter demoConverter;
    private final RedisUtil redisUtil;
    
    @Autowired
    public DemoServiceImpl(DemoConverter demoConverter, RedisUtil redisUtil) {
        this.demoConverter = demoConverter;
        this.redisUtil = redisUtil;
    }

    @Override
    public DemoVO create(CreateDemoDTO createDTO) {
        // 创建实体对象
        Demo demo = new Demo();
        
        // 设置ID
        demo.setDemoId(UUID.randomUUID().toString().replace("-", ""));
        
        // 设置基本字段
        demo.setDemoName(createDTO.getDemoName());
        demo.setDemoDescription(createDTO.getDemoDescription());
        demo.setDemoCode(createDTO.getDemoCode());
        demo.setDemoContent(createDTO.getDemoContent());
        demo.setDemoCategory(createDTO.getDemoCategory());
        
        // 设置状态和时间
        demo.setDemoStatus(1); // 初始状态
        LocalDateTime now = LocalDateTime.now();
        demo.setDemoCreated(now);
        demo.setDemoUpdated(now);
        
        // 保存到数据库
        boolean success = save(demo);
        
        if (success) {
            // 清除列表缓存
            clearListCache();
            log.info("创建Demo成功: {}", demo.getDemoId());
            
            // 返回视图对象
            return demoConverter.entityToVO(demo);
        } else {
            log.error("创建Demo失败");
            return null;
        }
    }

    @Override
    public DemoVO update(UpdateDemoDTO updateDTO) {
        // 检查是否存在
        String id = updateDTO.getDemoId();
        Demo existingDemo = getEntityById(id);
        if (existingDemo == null) {
            log.warn("更新Demo失败，ID不存在: {}", id);
            return null;
        }
        
        // 更新字段
        existingDemo.setDemoName(updateDTO.getDemoName());
        existingDemo.setDemoDescription(updateDTO.getDemoDescription());
        existingDemo.setDemoCode(updateDTO.getDemoCode());
        existingDemo.setDemoContent(updateDTO.getDemoContent());
        existingDemo.setDemoCategory(updateDTO.getDemoCategory());
        
        // 更新时间
        existingDemo.setDemoUpdated(LocalDateTime.now());
        
        // 更新到数据库
        boolean success = updateById(existingDemo);
        
        if (success) {
            // 清除缓存
            String cacheKey = CACHE_KEY_PREFIX + id;
            redisUtil.remove(cacheKey);
            clearListCache();
            
            log.info("更新Demo成功: {}", id);
            
            // 返回视图对象
            return demoConverter.entityToVO(existingDemo);
        } else {
            log.error("更新Demo失败: {}", id);
            return null;
        }
    }

    @Override
    public DemoVO getById(String id) {
        if (!StringUtils.hasText(id)) {
            return null;
        }
        
        String cacheKey = CACHE_KEY_PREFIX + id;
        
        // 从缓存获取
        Object cachedObj = redisUtil.get(cacheKey);
        if (cachedObj != null) {
            log.debug("从缓存获取Demo: {}", id);
            return demoConverter.entityToVO((Demo) cachedObj);
        }
        
        // 从数据库获取
        Demo demo = super.getById(id);
        
        // 放入缓存并返回
        if (demo != null) {
            redisUtil.set(cacheKey, demo, CACHE_EXPIRE);
            log.debug("已将Demo放入缓存: {}", id);
            return demoConverter.entityToVO(demo);
        }
        
        return null;
    }

    @Override
    public boolean removeById(String id) {
        // 检查是否存在
        Demo existingDemo = getEntityById(id);
        if (existingDemo == null) {
            log.warn("删除Demo失败，ID不存在: {}", id);
            return false;
        }
        
        // 从数据库删除
        boolean success = super.removeById(id);
        
        if (success) {
            // 清除缓存
            String cacheKey = CACHE_KEY_PREFIX + id;
            redisUtil.remove(cacheKey);
            clearListCache();
            
            log.info("删除Demo成功: {}", id);
            return true;
        } else {
            log.error("删除Demo失败: {}", id);
            return false;
        }
    }

    @Override
    public IPage<DemoVO> page(int page, int size, String name, String code, String category) {
        // 创建分页对象
        Page<Demo> pageable = new Page<>(page, size);
        
        // 构建查询条件
        LambdaQueryWrapper<Demo> queryWrapper = new LambdaQueryWrapper<>();
        
        // 添加过滤条件
        if (StringUtils.hasText(name)) {
            queryWrapper.like(Demo::getDemoName, name);
        }
        
        if (StringUtils.hasText(code)) {
            queryWrapper.eq(Demo::getDemoCode, code);
        }
        
        if (StringUtils.hasText(category)) {
            queryWrapper.eq(Demo::getDemoCategory, category);
        }
        
        // 添加排序
        queryWrapper.orderByDesc(Demo::getDemoUpdated);
        
        // 执行查询
        IPage<Demo> result = super.page(pageable, queryWrapper);
        
        // 转换结果
        IPage<DemoVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<DemoVO> voList = result.getRecords().stream()
                .map(demoConverter::entityToVO)
                .collect(Collectors.toList());
        voPage.setRecords(voList);
        
        return voPage;
    }

    @Override
    public boolean existsByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return false;
        }
        
        LambdaQueryWrapper<Demo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Demo::getDemoCode, code);
        
        return super.count(queryWrapper) > 0;
    }

    @Override
    public boolean updateStatus(String id, Integer status) {
        if (!StringUtils.hasText(id) || status == null) {
            return false;
        }
        
        // 获取实体
        Demo demo = getEntityById(id);
        if (demo == null) {
            log.warn("更新Demo状态失败，ID不存在: {}", id);
            return false;
        }
        
        // 更新状态和时间
        demo.setDemoStatus(status);
        demo.setDemoUpdated(LocalDateTime.now());
        
        // 更新到数据库
        boolean success = updateById(demo);
        
        if (success) {
            // 清除缓存
            String cacheKey = CACHE_KEY_PREFIX + id;
            redisUtil.remove(cacheKey);
            clearListCache();
            
            log.info("更新Demo状态成功: {}, 新状态: {}", id, status);
            return true;
        } else {
            log.error("更新Demo状态失败: {}", id);
            return false;
        }
    }

    @Override
    public List<DemoVO> listByCategory(String category) {
        if (!StringUtils.hasText(category)) {
            return List.of();
        }
        
        // 缓存键
        String cacheKey = CACHE_LIST_PREFIX + "category:" + category;
        
        // 从缓存获取
        Object cachedObj = redisUtil.get(cacheKey);
        if (cachedObj != null) {
            log.debug("从缓存获取分类Demo列表: {}", category);
            return (List<DemoVO>) cachedObj;
        }
        
        // 构建查询条件
        LambdaQueryWrapper<Demo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Demo::getDemoCategory, category);
        queryWrapper.orderByDesc(Demo::getDemoUpdated);
        
        // 执行查询
        List<Demo> demoList = super.list(queryWrapper);
        
        // 转换结果
        List<DemoVO> voList = demoList.stream()
                .map(demoConverter::entityToVO)
                .collect(Collectors.toList());
        
        // 放入缓存
        redisUtil.set(cacheKey, voList, CACHE_EXPIRE);
        log.debug("已将分类Demo列表放入缓存: {}", category);
        
        return voList;
    }
    
    /**
     * 清除列表缓存
     */
    private void clearListCache() {
        // 使用通配符删除所有列表缓存
        redisUtil.removePattern(CACHE_LIST_PREFIX + "*");
        log.debug("已清除Demo列表缓存");
    }

    /**
     * 根据ID获取Demo实体
     *
     * @param id Demo ID
     * @return Demo实体对象
     */
    private Demo getEntityById(String id) {
        if (!StringUtils.hasText(id)) {
            return null;
        }
        
        return super.getById(id);
    }
} 