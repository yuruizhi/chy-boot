package com.chy.boot.service.service.impl;

import com.chy.boot.common.component.cache.RedisUtil;
import com.chy.boot.core.mapper.DemoMapper;
import com.chy.boot.service.service.DemoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Demo表服务实现类
 *
 * @author Henry.Yu
 * @since 2025/3/21
 */
@Service("demoService")
public class DemoServiceImpl implements DemoService {
    @Resource
    private DemoMapper demoDao;

    @Resource
    private RedisUtil redisUtil;


}