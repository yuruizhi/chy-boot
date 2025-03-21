package com.changyi.chy.demo.controller;

import com.changyi.chy.commons.annotation.ApiVersion;
import com.changyi.chy.commons.api.R;
import com.changyi.chy.demo.entity.UcWxqySuite;
import com.changyi.chy.demo.remote.AuthService;
import com.changyi.chy.demo.remote.HttpApi;
import com.changyi.chy.demo.service.UcWxqySuiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

/**
 * 套件主表(UcWxqySuite)表控制层
 *
 * @author ZhangHao
 * @since 2021-01-14 14:34:27
 */
@Tag(name = "套件", description = "套件相关操作")
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
    @Parameter(name = "id", description = "套件id", required = true)
    @Operation(summary = "通过主键查询单条数据")
    @ApiVersion(3)
    @GetMapping("selectOne")
    public UcWxqySuite selectOne(String id) {
        return this.ucWxqySuiteService.queryById(id);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @Parameter(name = "id", description = "套件id", required = true)
    @Operation(summary = "通过主键查询单条数据")
    @ApiVersion(3)
    @GetMapping("/{version}/selectOne")
    public UcWxqySuite selectOne3(String id) {
        return this.ucWxqySuiteService.queryById(id);
    }

    @PostMapping
    @Operation(summary = "保存套件信息")
    public R save(@RequestBody UcWxqySuite ucWxqySuite) {
        this.ucWxqySuiteService.insert(ucWxqySuite);
        return R.success();
    }

    @GetMapping("/baidu")
    @Operation(summary = "测试Baidu接口")
    public R Baidu() {
        return R.data(httpApi.getOneHitokoto());
    }

    @GetMapping("/test")
    @Operation(summary = "测试接口")
    public R<String> test() {
        return authService.test();
    }
}