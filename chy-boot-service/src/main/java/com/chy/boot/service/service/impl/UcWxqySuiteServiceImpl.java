package com.chy.boot.service.service.impl;

import cn.hutool.core.util.IdUtil;
import com.chy.boot.common.component.cache.RedisUtil;
import com.chy.boot.common.util.DateUtil;
import com.chy.boot.core.entity.UcWxqySuite;
import com.chy.boot.core.mapper.UcWxqySuiteDao;
import com.chy.boot.service.service.UcWxqySuiteService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 套件主表(UcWxqySuite)表服务实现类
 *
 * @author ZhangHao
 * @since 2021-01-14 14:34:26
 */
@Service("ucWxqySuiteService")
public class UcWxqySuiteServiceImpl implements UcWxqySuiteService {
    @Resource
    private UcWxqySuiteDao ucWxqySuiteDao;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 通过ID查询单条数据
     *
     * @param qysSuiteid 主键
     * @return 实例对象
     */
    @Override
    public UcWxqySuite queryById(String qysSuiteid) {
        UcWxqySuite ucWxqySuite = (UcWxqySuite) redisUtil.get("suite" + qysSuiteid);

        if (ucWxqySuite == null) {
            return this.ucWxqySuiteDao.queryById(qysSuiteid);
        } else {
            return ucWxqySuite;
        }
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<UcWxqySuite> queryAllByLimit(int offset, int limit) {
        return this.ucWxqySuiteDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param ucWxqySuite 实例对象
     * @return 实例对象
     */
    @Override
    public UcWxqySuite insert(UcWxqySuite ucWxqySuite) {
        ucWxqySuite.setQysSuiteid(IdUtil.fastUUID());
        ucWxqySuite.setQysCreated(DateUtil.getCurrentTimeMills());
        ucWxqySuite.setQysUpdated(DateUtil.getCurrentTimeMills());
        ucWxqySuite.setQysDeleted(DateUtil.getCurrentTimeMills());
        ucWxqySuite.setQysStatus(1);
        ucWxqySuite.setQysProvider("tencent");
        ucWxqySuite.setQysSuiteSecret("secret");
        ucWxqySuite.setQysSuiteAeskey("aeskey");
        redisUtil.set("suite" + ucWxqySuite.getQysSuiteid(), ucWxqySuite);
        this.ucWxqySuiteDao.insert(ucWxqySuite);
        return ucWxqySuite;
    }

    /**
     * 修改数据
     *
     * @param ucWxqySuite 实例对象
     * @return 实例对象
     */
    @Override
    public UcWxqySuite update(UcWxqySuite ucWxqySuite) {
        this.ucWxqySuiteDao.update(ucWxqySuite);
        return this.queryById(ucWxqySuite.getQysSuiteid());
    }

    /**
     * 通过主键删除数据
     *
     * @param qysSuiteid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String qysSuiteid) {
        return this.ucWxqySuiteDao.deleteById(qysSuiteid) > 0;
    }
}