package com.chy.boot.rest.controller;

import com.chy.boot.common.api.R;
import com.chy.boot.service.remote.AuthService;
import com.chy.boot.service.remote.HttpApi;
import com.chy.boot.service.service.DemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo表控制层
 *
 * @author ZhangHao
 * @since 2021-01-14 14:34:27
 */
@Tag(name = "Demo", description = "Demo相关接口")
@RestController
@RequestMapping("demo")
public class DemoController {

    @Resource
    private DemoService demoService;

    @Resource
    private HttpApi httpApi;

    @Resource
    private AuthService authService;


    @GetMapping("/baidu")
    @Operation(summary = "测试Baidu接口")
    public R baidu() {
        return R.data(httpApi.getOneHitokoto());
    }

    @GetMapping("/test")
    @Operation(summary = "测试接口")
    public R<String> test() {
        return authService.test();
    }
}