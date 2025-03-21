package com.chy.boot.service.service.impl;

import com.chy.boot.common.api.R;
import com.chy.boot.common.component.log.LogStyle;
import com.chy.boot.common.platform.auth.component.PassToken;
import com.chy.boot.core.entity.Demo;
import com.chy.boot.core.mapper.DemoMapper;
import com.chy.boot.service.remote.HttpApi;
import com.chy.boot.service.request.ReqDemo;
import com.chy.boot.service.response.RespDemo;
import com.chy.boot.service.service.DemoService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Demo服务实现类
 *
 * @author Henry.Yu
 * @date 2023/09/05
 */
@Slf4j
@Service
public class DemoServiceImpl implements DemoService {

    @Resource
    private HttpApi httpApi;

    @Resource
    private DemoMapper demoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogStyle(beforeDesc = "创建Demo:{0}", afterDesc = "创建Demo结果:{}")
    public R<Demo> createDemo(Demo demo) {

        // 设置默认状态为启用
        if (demo.getStatus() == null) {
            demo.setStatus(Integer.valueOf(1));
        }

        // 插入数据库
        int rows = demoMapper.insert(demo);
        if (rows > 0) {
            return R.data(demo);
        } else {
            return R.fail("创建Demo失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogStyle(beforeDesc = "更新Demo:{0}", afterDesc = "更新Demo结果:{}")
    public R<Demo> updateDemo(Demo demo) {
        // 检查ID是否存在
        Demo existingDemo = demoMapper.selectById(demo.getId());
        if (existingDemo == null) {
            return R.fail("未找到ID为" + demo.getId() + "的Demo对象");
        }

        // 更新数据库
        int rows = demoMapper.update(demo);
        if (rows > 0) {
            // 重新获取最新数据
            Demo updatedDemo = demoMapper.selectById(demo.getId());
            return R.data(updatedDemo);
        } else {
            return R.fail("更新Demo失败");
        }
    }

    @Override
    @LogStyle(beforeDesc = "查询Demo,ID:{0}", afterDesc = "查询Demo结果:{}")
    public R<Demo> getDemoById(Long id) {

        // 查询数据库
        Demo demo = demoMapper.selectById(id);
        if (demo != null) {
            return R.data(demo);
        } else {
            return R.fail("未找到ID为" + id + "的Demo对象");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogStyle(beforeDesc = "删除Demo,ID:{0}", afterDesc = "删除Demo结果:{}")
    public R<Boolean> deleteDemo(Long id) {
        // 检查ID是否存在
        Demo existingDemo = demoMapper.selectById(id);
        if (existingDemo == null) {
            return R.fail("未找到ID为" + id + "的Demo对象");
        }

        // 物理删除
        int rows = demoMapper.deleteById(id);
        if (rows > 0) {
            return R.data(true);
        } else {
            return R.fail("删除Demo失败");
        }
    }

    @Override
    @LogStyle(beforeDesc = "查询Demo列表,name:{0},page:{1},size:{2}", afterDesc = "查询Demo列表结果:{}")
    public R<List<Demo>> listDemos(String name, Integer page, Integer size) {

        // 默认值处理
        page = page == null || page < 1 ? 1 : page;
        size = size == null || size < 1 ? 10 : size;

        // 查询列表数据
        List<Demo> demoList = demoMapper.selectByNameLike(name);

        // 简单的分页处理
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, demoList.size());

        // 检查索引是否合法
        if (fromIndex >= demoList.size()) {
            return R.data(List.of());
        }

        // 返回分页后的数据
        List<Demo> pagedList = demoList.subList(fromIndex, toIndex);
        return R.data(pagedList);
    }

    @Override
    @LogStyle(beforeDesc = "查询所有Demo", afterDesc = "查询所有Demo结果:{}")
    public R<List<Demo>> listAllDemos() {
        // 查询所有数据
        List<Demo> demoList = demoMapper.selectAll();
        return R.data(demoList);
    }

    @Override
    @LogStyle(beforeDesc = "根据状态查询Demo,status:{0}", afterDesc = "根据状态查询Demo结果:{}")
    public R<List<Demo>> listDemosByStatus(Integer status) {
        // 查询状态匹配的数据
        List<Demo> demoList = demoMapper.selectByStatus(status);
        return R.data(demoList);
    }

    @Override
    @LogStyle(beforeDesc = "处理示例请求:{0}", afterDesc = "处理示例请求结果:{}")
    public R<RespDemo> processRequest(ReqDemo reqDemo) {
        try {
            // 调用远程服务获取一条示例数据
            RespDemo respDemo = getOneHitokoto();

            // 为响应添加一些额外信息
            if (reqDemo.getMobile() != null) {
                respDemo.setCommit_from("Mobile: " + reqDemo.getMobile());
            } else if (reqDemo.getOpenId() != null) {
                respDemo.setCommit_from("OpenID: " + reqDemo.getOpenId());
            } else {
                respDemo.setCommit_from("Unknown source");
            }

            return R.data(respDemo);
        } catch (Exception e) {
            log.error("处理示例请求失败", e);
            return R.fail("处理请求失败: " + e.getMessage());
        }
    }

    @Override
    @PassToken
    public RespDemo getOneHitokoto() {
        try {
            // 由于HttpApi接口中的getOneHitokoto方法已被注释，这里直接创建默认响应
            log.info("创建示例数据");

            // 创建一个默认的响应数据
            RespDemo defaultResp = new RespDemo();
            defaultResp.setId(0);
            defaultResp.setUuid(UUID.randomUUID().toString());
            defaultResp.setHitokoto("这是一个示例响应");
            defaultResp.setType("text");
            defaultResp.setFrom("default");
            defaultResp.setCreator("system");
            defaultResp.setCreated_at(new Date().toString());

            return defaultResp;
        } catch (Exception e) {
            log.error("获取示例数据失败", e);

            // 创建一个默认的响应数据
            RespDemo defaultResp = new RespDemo();
            defaultResp.setId(0);
            defaultResp.setUuid(UUID.randomUUID().toString());
            defaultResp.setHitokoto("获取数据失败，这是一个默认响应");
            defaultResp.setType("text");
            defaultResp.setFrom("default");
            defaultResp.setCreator("system");
            defaultResp.setCreated_at(new Date().toString());

            return defaultResp;
        }
    }
}