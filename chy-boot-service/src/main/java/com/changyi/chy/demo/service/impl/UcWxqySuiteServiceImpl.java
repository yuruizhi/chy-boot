package com.changyi.chy.demo.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changyi.chy.commons.component.cache.RedisUtil;
import com.changyi.chy.demo.entity.UcWxqySuite;
import com.changyi.chy.demo.mapper.UcWxqySuiteDao;
import com.changyi.chy.demo.service.UcWxqySuiteService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 套件主表(UcWxqySuite)表服务实现类
 *
 * @author YuRuizhi
 * @since 2021-01-14 14:34:26
 * @update 2023/03/01 升级到MyBatis-Plus
 */
@Service("ucWxqySuiteService")
public class UcWxqySuiteServiceImpl extends ServiceImpl<UcWxqySuiteDao, UcWxqySuite> implements UcWxqySuiteService {
    
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
    public List<UcWxqySuite> queryAllByLimit(int offset, int limit) {
        return baseMapper.queryAllByLimit(offset, limit);
    }
}