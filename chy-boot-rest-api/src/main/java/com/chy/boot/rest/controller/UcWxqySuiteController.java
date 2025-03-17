package com.chy.boot.rest.controller;

import com.chy.boot.commons.annotation.ApiVersion;
import com.chy.boot.commons.api.R;
import com.chy.boot.rest.entity.UcWxqySuite;
import com.chy.boot.rest.remote.AuthService;
import com.chy.boot.rest.remote.HttpApi;
import com.chy.boot.rest.service.UcWxqySuiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

/**
 * 套件主表(UcWxqySuite)表控制层
 *
 * @author YuRuizhi
 * @since 2021-01-14 14:34:27
 */
@Tag(name = "套件")
@RestController
@RequestMapping("ucWxqySuite")
public class UcWxqySuiteController {
    /**
     * 服务对象
     */
    @Resource
    private UcWxqySuiteService ucWxqySuiteService;

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
    public UcWxqySuite selectOne(@Parameter(description = "套件id", required = true) String id) {
        return this.ucWxqySuiteService.getById(id);
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
    public UcWxqySuite selectOne3(@Parameter(description = "套件id", required = true) String id) {
        return this.ucWxqySuiteService.getById(id);
    }

    @PostMapping
    public R save(@RequestBody UcWxqySuite ucWxqySuite) {
        this.ucWxqySuiteService.save(ucWxqySuite);
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