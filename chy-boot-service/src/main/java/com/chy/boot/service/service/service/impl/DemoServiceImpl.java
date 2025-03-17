package com.chy.boot.service.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.boot.common.commons.component.cache.RedisUtil;
import com.chy.boot.common.commons.component.log.LogStyle;
import com.chy.boot.common.commons.component.log.LoggerFormat;
import com.chy.boot.rest.core.entity.Demo;
import com.chy.boot.rest.core.mapper.DemoDao;
import com.chy.boot.rest.dto.DemoQueryParam;
import com.chy.boot.service.service.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 示例表(Demo)表服务实现类
 *
 * @author YuRuizhi
 * @since 2021-01-14 14:34:26
 * @update 2023/03/01 升级到MyBatis-Plus
 * @update 2024/06/14 增强服务实现，添加缓存和日志
 */
@Slf4j
@Service("demoService")
@Transactional(rollbackFor = Exception.class)
public class DemoServiceImpl extends ServiceImpl<DemoDao, Demo> implements DemoService {
    
    // 缓存相关常量
    private static final String CACHE_KEY_PREFIX = "demo:entity:";
    private static final String CACHE_LIST_PREFIX = "demo:list:";
    private static final long CACHE_EXPIRE = 3600L; // 1小时
    
    private final RedisUtil redisUtil;
    
    @Autowired
    public DemoServiceImpl(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Demo> queryAllByLimit(int offset, int limit) {
        return baseMapper.queryAllByLimit(offset, limit);
    }
    
    /**
     * 根据ID获取实体，优先从缓存获取
     *
     * @param id 实体ID
     * @return 实体对象
     */
    @Override
    public Demo getByIdWithCache(String id) {
        if (!StringUtils.hasText(id)) {
            return null;
        }
        
        String cacheKey = CACHE_KEY_PREFIX + id;
        
        // 从缓存获取
        Object cachedObj = redisUtil.get(cacheKey);
        if (cachedObj != null) {
            log.debug(LoggerFormat.debug(log, "从缓存获取Demo, ID: {}", id));
            return (Demo) cachedObj;
        }
        
        // 缓存未命中，从数据库获取
        log.debug(LoggerFormat.debug(log, "缓存未命中，从数据库获取Demo, ID: {}", id));
        Demo demo = super.getById(id);
        
        // 放入缓存
        if (demo != null) {
            redisUtil.set(cacheKey, demo, CACHE_EXPIRE);
            log.debug(LoggerFormat.debug(log, "已将Demo放入缓存, ID: {}", id));
        }
        
        return demo;
    }
    
    /**
     * 保存实体，并清除相关缓存
     *
     * @param demo 实体对象
     * @return 是否成功
     */
    @Override
    @LogStyle(
        tag = "Demo业务", 
        beforeDesc = "开始保存Demo, 名称: {0.demoName}", 
        afterDesc = "保存结果: %s"
    )
    public boolean saveWithCache(Demo demo) {
        if (demo == null) {
            return false;
        }
        
        // 设置默认值
        if (demo.getDemoStatus() == null) {
            demo.setDemoStatus(1); // 初始状态
        }
        
        LocalDateTime now = LocalDateTime.now();
        if (demo.getDemoCreated() == null) {
            demo.setDemoCreated(now);
        }
        if (demo.getDemoUpdated() == null) {
            demo.setDemoUpdated(now);
        }
        
        boolean result = super.save(demo);
        
        // 清除列表缓存
        if (result) {
            clearListCache();
            log.info(LoggerFormat.info(log, "Demo保存成功, ID: {}, 名称: {}", 
                    demo.getDemoId(), demo.getDemoName()));
        } else {
            log.warn(LoggerFormat.warn(log, "Demo保存失败, 名称: {}", demo.getDemoName()));
        }
        
        return result;
    }
    
    /**
     * 更新实体，并清除相关缓存
     *
     * @param demo 实体对象
     * @return 是否成功
     */
    @Override
    @LogStyle(
        tag = "Demo业务", 
        beforeDesc = "开始更新Demo, ID: {0.demoId}", 
        afterDesc = "更新结果: %s"
    )
    public boolean updateByIdWithCache(Demo demo) {
        if (demo == null || !StringUtils.hasText(demo.getDemoId())) {
            return false;
        }
        
        // 设置更新时间
        demo.setDemoUpdated(LocalDateTime.now());
        
        boolean result = super.updateById(demo);
        
        // 清除缓存
        if (result) {
            String cacheKey = CACHE_KEY_PREFIX + demo.getDemoId();
            redisUtil.remove(cacheKey);
            clearListCache();
            log.info(LoggerFormat.info(log, "Demo更新成功, ID: {}", demo.getDemoId()));
        } else {
            log.warn(LoggerFormat.warn(log, "Demo更新失败, ID: {}", demo.getDemoId()));
        }
        
        return result;
    }
    
    /**
     * 删除实体，并清除相关缓存
     *
     * @param id 实体ID
     * @return 是否成功
     */
    @Override
    @LogStyle(
        tag = "Demo业务", 
        beforeDesc = "开始删除Demo, ID: {0}", 
        afterDesc = "删除结果: %s"
    )
    public boolean removeByIdWithCache(String id) {
        if (!StringUtils.hasText(id)) {
            return false;
        }
        
        boolean result = super.removeById(id);
        
        // 清除缓存
        if (result) {
            String cacheKey = CACHE_KEY_PREFIX + id;
            redisUtil.remove(cacheKey);
            clearListCache();
            log.info(LoggerFormat.info(log, "Demo删除成功, ID: {}", id));
        } else {
            log.warn(LoggerFormat.warn(log, "Demo删除失败, ID: {}", id));
        }
        
        return result;
    }
    
    /**
     * 检查标识码是否已存在
     *
     * @param code 标识码
     * @return 是否存在
     */
    @Override
    public boolean existsByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return false;
        }
        
        LambdaQueryWrapper<Demo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Demo::getDemoCode, code);
        
        return super.count(queryWrapper) > 0;
    }
    
    /**
     * 通过查询参数进行分页查询
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页结果
     */
    @Override
    public IPage<Demo> getPage(Page<Demo> page, DemoQueryParam param) {
        QueryWrapper<Demo> queryWrapper = createQueryWrapper(param);
        return super.page(page, queryWrapper);
    }
    
    /**
     * 更新状态
     *
     * @param id     实体ID
     * @param status 新状态
     * @return 是否成功
     */
    @Override
    @LogStyle(
        tag = "Demo业务", 
        beforeDesc = "开始更新Demo状态, ID: {0}, 状态: {1}", 
        afterDesc = "状态更新结果: %s"
    )
    public boolean updateStatus(String id, Integer status) {
        if (!StringUtils.hasText(id) || status == null) {
            return false;
        }
        
        Demo demo = super.getById(id);
        if (demo == null) {
            log.warn(LoggerFormat.warn(log, "要更新状态的Demo不存在, ID: {}", id));
            return false;
        }
        
        demo.setDemoStatus(status);
        demo.setDemoUpdated(LocalDateTime.now());
        
        boolean result = super.updateById(demo);
        
        // 清除缓存
        if (result) {
            String cacheKey = CACHE_KEY_PREFIX + id;
            redisUtil.remove(cacheKey);
            clearListCache();
            log.info(LoggerFormat.info(log, "Demo状态更新成功, ID: {}, 新状态: {}", id, status));
        } else {
            log.warn(LoggerFormat.warn(log, "Demo状态更新失败, ID: {}", id));
        }
        
        return result;
    }
    
    /**
     * 批量保存或更新，处理缓存
     *
     * @param demoList 实体列表
     * @return 是否成功
     */
    @Override
    @LogStyle(
        tag = "Demo业务", 
        beforeDesc = "开始批量保存Demo, 数量: {0.size()}", 
        afterDesc = "批量保存结果: %s"
    )
    public boolean saveBatchWithCache(List<Demo> demoList) {
        if (CollectionUtils.isEmpty(demoList)) {
            return false;
        }
        
        // 设置默认值
        LocalDateTime now = LocalDateTime.now();
        for (Demo demo : demoList) {
            if (demo.getDemoStatus() == null) {
                demo.setDemoStatus(1);
            }
            if (demo.getDemoCreated() == null) {
                demo.setDemoCreated(now);
            }
            if (demo.getDemoUpdated() == null) {
                demo.setDemoUpdated(now);
            }
        }
        
        // 分批处理
        int batchSize = 100;
        boolean result = super.saveBatch(demoList, batchSize);
        
        // 清除列表缓存
        if (result) {
            clearListCache();
            log.info(LoggerFormat.info(log, "批量保存Demo成功, 数量: {}", demoList.size()));
        } else {
            log.warn(LoggerFormat.warn(log, "批量保存Demo失败"));
        }
        
        return result;
    }
    
    /**
     * 缓存预热
     *
     * @param limit 预热数量
     */
    @Override
    public void preloadCache(int limit) {
        log.info(LoggerFormat.info(log, "开始Demo缓存预热, 数量上限: {}", limit));
        
        // 查询最近更新的数据
        QueryWrapper<Demo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("demo_updated");
        queryWrapper.last("LIMIT " + limit);
        
        List<Demo> demoList = super.list(queryWrapper);
        int count = 0;
        
        for (Demo demo : demoList) {
            String cacheKey = CACHE_KEY_PREFIX + demo.getDemoId();
            redisUtil.set(cacheKey, demo, CACHE_EXPIRE);
            count++;
        }
        
        log.info(LoggerFormat.info(log, "Demo缓存预热完成, 已预热: {}", count));
    }
    
    /**
     * 清除列表缓存
     */
    private void clearListCache() {
        redisUtil.removePattern(CACHE_LIST_PREFIX + "*");
        log.debug(LoggerFormat.debug(log, "已清除Demo列表缓存"));
    }
    
    /**
     * 创建查询条件
     */
    private QueryWrapper<Demo> createQueryWrapper(DemoQueryParam param) {
        QueryWrapper<Demo> queryWrapper = new QueryWrapper<>();
        
        if (param != null) {
            // 名称模糊查询
            if (StringUtils.hasText(param.getDemoName())) {
                queryWrapper.like("demo_name", param.getDemoName());
            }
            
            // 标识码精确查询
            if (StringUtils.hasText(param.getDemoCode())) {
                queryWrapper.eq("demo_code", param.getDemoCode());
            }
            
            // 分类查询
            if (StringUtils.hasText(param.getDemoCategory())) {
                queryWrapper.eq("demo_category", param.getDemoCategory());
            }
            
            // 状态查询
            if (param.getDemoStatus() != null) {
                queryWrapper.eq("demo_status", param.getDemoStatus());
            }
            
            // 时间范围查询
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.hasText(param.getStartTime())) {
                try {
                    LocalDateTime startTime = LocalDateTime.parse(param.getStartTime(), formatter);
                    queryWrapper.ge("demo_created", startTime);
                } catch (Exception e) {
                    log.error(LoggerFormat.error(log, "开始时间格式错误: {}", param.getStartTime()), e);
                }
            }
            
            if (StringUtils.hasText(param.getEndTime())) {
                try {
                    LocalDateTime endTime = LocalDateTime.parse(param.getEndTime(), formatter);
                    queryWrapper.le("demo_created", endTime);
                } catch (Exception e) {
                    log.error(LoggerFormat.error(log, "结束时间格式错误: {}", param.getEndTime()), e);
                }
            }
        }
        
        // 默认按更新时间倒序
        queryWrapper.orderByDesc("demo_updated");
        
        return queryWrapper;
    }
} 