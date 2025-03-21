package com.chy.boot.rest.controller;

import com.chy.boot.common.annotation.ApiVersion;
import com.chy.boot.common.api.AbstractBaseController;
import com.chy.boot.common.api.R;
import com.chy.boot.common.component.log.LogStyle;
import com.chy.boot.common.platform.auth.component.PassToken;
import com.chy.boot.service.request.ReqDemo;
import com.chy.boot.service.response.RespDemo;
import com.chy.boot.service.service.DemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 描述: Demo控制器
 * @author Henry.Yu
 * @date 2023/09/05
 */
@Tag(name = "示例相关接口", description = "Demo示例相关接口")
@RestController
@RequestMapping("/api/demo")
@Validated
public class DemoController extends AbstractBaseController {

    @Resource
    private DemoService demoService;

    /**
     * 处理示例请求
     */
    @PostMapping("/process")
    @Operation(summary = "处理示例请求", description = "处理示例请求并返回响应")
    @LogStyle(beforeDesc = "处理示例请求:{0}", afterDesc = "处理示例请求响应:{}")
    @PassToken
    public R<RespDemo> processRequest(@RequestBody @Validated ReqDemo reqDemo) {
        logger.info("处理示例请求: {}", reqDemo);
        return demoService.processRequest(reqDemo);
    }

    /**
     * 获取一条示例数据
     */
    @GetMapping("/hitokoto")
    @Operation(summary = "获取一条示例数据", description = "从远程API获取一条示例数据")
    @LogStyle(beforeDesc = "获取一条示例数据", afterDesc = "获取一条示例数据响应:{}")
    @PassToken
    public R<RespDemo> getOneHitokoto() {
        logger.info("获取一条示例数据");
        RespDemo respDemo = demoService.getOneHitokoto();
        return R.data(respDemo);
    }

    /**
     * API版本示例，v2版本
     */
    @ApiVersion(2)
    @GetMapping("/v{version}/test")
    @Operation(summary = "API版本示例", description = "演示API版本控制")
    @LogStyle(beforeDesc = "API版本示例访问", afterDesc = "API版本示例响应:{}")
    @PassToken
    public R<String> apiVersionTest() {
        logger.info("API v2版本示例访问");
        return R.data("这是Demo API v2版本的示例");
    }

    /**
     * API版本示例，v3版本
     */
    @ApiVersion(3)
    @GetMapping("/v{version}/advanced")
    @Operation(summary = "高级API版本示例", description = "演示高级版本API功能")
    @LogStyle(beforeDesc = "高级API版本示例访问", afterDesc = "高级API版本示例响应:{}")
    @PassToken
    public R<String> advancedVersionTest() {
        logger.info("API v3版本示例访问");
        return R.data("这是Demo API v3版本的高级功能示例");
    }
}