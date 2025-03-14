package com.changyi.chy.business.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changyi.chy.business.demo.entity.Demo;
import com.changyi.chy.business.demo.mapper.DemoMapper;
import com.changyi.chy.business.demo.service.DemoService;
import com.changyi.chy.common.core.domain.R;
import com.changyi.chy.persistence.result.PageResult;
import com.changyi.chy.persistence.request.PageRequest;
import com.changyi.chy.persistence.util.PageUtils;
import com.changyi.chy.persistence.util.QueryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 示例服务实现类
 *
 * @author YuRuizhi
 */
@Slf4j
@Service
public class DemoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements DemoService {

    /**
     * 分页查询
     *
     * @param request 分页参数
     * @return 分页结果
     */
    @Override
    public PageResult<Demo> page(PageRequest request) {
        // 构建查询条件
        LambdaQueryWrapper<Demo> queryWrapper = QueryBuilder.lambdaQuery();

        // 转换为MyBatis-Plus分页对象
        Page<Demo> page = PageUtils.convertToPage(request);
        
        // 执行分页查询
        page = this.page(page, queryWrapper);
        
        // 转换为通用分页结果
        return PageUtils.convertToPageResult(page);
    }

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    @Cacheable(value = "demoCache", key = "'demoList:' + #offset + ':' + #limit")
    public List<Demo> queryAllByLimit(int offset, int limit) {
        log.debug("查询示例列表，offset={}, limit={}", offset, limit);
        return baseMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return 示例对象
     */
    @Override
    @Cacheable(value = "demoCache", key = "'demo:id:' + #id")
    public Demo getById(String id) {
        log.debug("根据ID查询示例，id={}", id);
        return super.getById(id);
    }

    /**
     * 新增数据
     *
     * @param demo 示例对象
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CachePut(value = "demoCache", key = "'demo:id:' + #result.data.id", condition = "#result.data != null")
    public R<Demo> insert(Demo demo) {
        log.debug("新增示例，name={}", demo.getName());
        
        // 名称查重
        LambdaQueryWrapper<Demo> queryWrapper = QueryBuilder.lambdaQuery();
        queryWrapper.eq(Demo::getName, demo.getName());
        if (this.count(queryWrapper) > 0) {
            return R.fail("示例名称已存在");
        }
        
        // 保存
        boolean success = super.save(demo);
        if (success) {
            return R.data(demo);
        } else {
            return R.fail("新增失败");
        }
    }

    /**
     * 修改数据
     *
     * @param demo 示例对象
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CachePut(value = "demoCache", key = "'demo:id:' + #demo.id", condition = "#result.code == 200")
    public R<Boolean> update(Demo demo) {
        log.debug("更新示例，id={}", demo.getId());
        
        // 验证ID
        if (demo.getId() == null) {
            return R.fail("ID不能为空");
        }
        
        // 存在性验证
        Demo oldDemo = getById(demo.getId());
        if (oldDemo == null) {
            return R.fail("示例不存在");
        }
        
        // 名称查重（排除自身）
        if (!Objects.equals(oldDemo.getName(), demo.getName())) {
            LambdaQueryWrapper<Demo> queryWrapper = QueryBuilder.lambdaQuery();
            queryWrapper.eq(Demo::getName, demo.getName())
                    .ne(Demo::getId, demo.getId());
            if (this.count(queryWrapper) > 0) {
                return R.fail("示例名称已存在");
            }
        }
        
        // 更新
        boolean success = updateById(demo);
        return success ? R.ok(true) : R.fail("更新失败");
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "demoCache", key = "'demo:id:' + #id")
    public R<Boolean> deleteById(String id) {
        log.debug("删除示例，id={}", id);
        
        // 验证ID
        if (id == null) {
            return R.fail("ID不能为空");
        }
        
        // 存在性验证
        Demo demo = getById(id);
        if (demo == null) {
            return R.fail("示例不存在");
        }
        
        // 删除
        boolean success = super.removeById(id);
        return success ? R.ok(true) : R.fail("删除失败");
    }
} 