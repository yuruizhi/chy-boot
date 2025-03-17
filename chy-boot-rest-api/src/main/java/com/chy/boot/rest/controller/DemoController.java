package com.chy.boot.rest.controller;

import com.chy.boot.common.commons.annotation.ApiVersion;
import com.chy.boot.common.commons.api.R;
import com.chy.boot.rest.core.entity.Demo;
import com.chy.boot.service.service.remote.AuthService;
import com.chy.boot.service.service.remote.HttpApi;
import com.chy.boot.service.service.service.DemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

/**
 * 示例表(Demo)表控制层
 *
 * @author YuRuizhi
 * @since 2021-01-14 14:34:27
 */
@Tag(name = "示例")
@RestController
@RequestMapping("demo")
public class DemoController {
    /**
     * 服务对象
     */
    @Resource
    private DemoService demoService;

    @Resource
    private HttpApi httpApi;

    @Resource
    private AuthService authService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @Operation(summary = "通过主键查询单条数据")
    @ApiVersion(3)
    @GetMapping("selectOne")
    public Demo selectOne(@Parameter(description = "示例ID", required = true) String id) {
        return this.demoService.getById(id);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @Operation(summary = "通过主键查询单条数据")
    @ApiVersion(3)
    @GetMapping("/{version}/selectOne")
    public Demo selectOne3(@Parameter(description = "示例ID", required = true) String id) {
        return this.demoService.getById(id);
    }

    @PostMapping
    public R save(@RequestBody Demo demo) {
        this.demoService.save(demo);
        return R.success();
    }

    @GetMapping("/baidu")
    public R Baidu() {
        return R.data(httpApi.getOneHitokoto());
    }

    @GetMapping("/test")
    public R<String> test() {
        return authService.test();
    }
} 