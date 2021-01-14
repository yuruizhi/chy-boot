package com.changyi.chy.demo.controller;

import com.changyi.chy.commons.annotation.ApiVersion;
import com.changyi.chy.demo.entity.UcWxqySuite;
import com.changyi.chy.demo.service.UcWxqySuiteService;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}