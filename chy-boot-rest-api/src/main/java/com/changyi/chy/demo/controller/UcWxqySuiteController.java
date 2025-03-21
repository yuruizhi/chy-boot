package com.changyi.chy.demo.controller;

import com.changyi.chy.commons.annotation.ApiVersion;
import com.changyi.chy.commons.api.R;
import com.changyi.chy.demo.entity.UcWxqySuite;
import com.changyi.chy.demo.remote.AuthService;
import com.changyi.chy.demo.remote.HttpApi;
import com.changyi.chy.demo.service.UcWxqySuiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 套件主表(UcWxqySuite)表控制层
 *
 * @author ZhangHao
 * @since 2021-01-14 14:34:27
 */
@Api(tags = "套件")
@RestController
@RequestMapping("ucWxqySuite")
@ApiOperation(value = "套件")
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
    @ApiImplicitParam(name = "id",value = "套件id",required = true)
    @ApiOperation(value = "通过主键查询单条数据")
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
    @ApiImplicitParam(name = "id",value = "套件id",required = true)
    @ApiOperation(value = "通过主键查询单条数据")
    @ApiVersion(3)
    @GetMapping("/{version}/selectOne")
    public UcWxqySuite selectOne3(String id) {
        return this.ucWxqySuiteService.queryById(id);
    }

    @PostMapping
    public R save(@RequestBody UcWxqySuite ucWxqySuite) {
        this.ucWxqySuiteService.insert(ucWxqySuite);
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