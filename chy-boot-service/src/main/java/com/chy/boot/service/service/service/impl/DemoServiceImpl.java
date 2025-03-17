package com.chy.boot.service.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.boot.common.commons.component.cache.RedisUtil;
import com.chy.boot.rest.core.entity.Demo;
import com.chy.boot.rest.core.mapper.DemoDao;
import com.chy.boot.service.service.service.DemoService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 示例表(Demo)表服务实现类
 *
 * @author YuRuizhi
 * @since 2021-01-14 14:34:26
 * @update 2023/03/01 升级到MyBatis-Plus
 */
@Service("demoService")
public class DemoServiceImpl extends ServiceImpl<DemoDao, Demo> implements DemoService {
    
    @Resource
    private RedisUtil redisUtil;

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
} 