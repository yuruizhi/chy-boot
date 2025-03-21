package com.chy.boot.rest.controller;

import com.chy.boot.common.annotation.ApiVersion;
import com.chy.boot.common.api.R;
import com.chy.boot.core.entity.UcWxqySuite;
import com.chy.boot.service.remote.AuthService;
import com.chy.boot.service.remote.HttpApi;
import com.chy.boot.service.service.UcWxqySuiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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