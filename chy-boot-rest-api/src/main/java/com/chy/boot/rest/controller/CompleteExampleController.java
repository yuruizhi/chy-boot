package com.chy.boot.rest.controller;

import com.chy.boot.common.annotation.ApiVersion;
import com.chy.boot.common.api.AbstractBaseController;
import com.chy.boot.common.api.R;
import com.chy.boot.common.component.log.LogStyle;
import com.chy.boot.common.component.validate.ValidGroup;
import com.chy.boot.common.platform.auth.component.PassToken;
import com.chy.boot.common.platform.auth.component.UserLoginToken;
import com.chy.boot.service.request.ReqDemo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 完整示例控制器，展示API版本控制、参数校验和日志记录
 *
 * @author Example
 * @date 2023-06-01
 */
@Tag(name = "完整示例", description = "完整功能示例相关接口")
@RestController
@RequestMapping("/v{version}/example")
@ApiVersion(1)
@Validated
public class CompleteExampleController extends AbstractBaseController {

    /**
     * 不需要登录的简单GET请求示例
     */
    @PassToken
    @GetMapping("/simple")
    @Operation(summary = "简单示例接口", description = "无需登录的简单GET示例")
    @LogStyle(beforeDesc = "访问简单示例接口", afterDesc = "简单示例接口访问结果: {}")
    public R<String> simpleExample() {
        logger.info("记录简单示例接口访问日志");
        return R.data("这是一个简单的示例响应");
    }

    /**
     * 需要登录的带路径参数的GET请求示例，API v1版本
     */
    @UserLoginToken
    @GetMapping("/detail/{id}")
    @Operation(summary = "获取详情示例接口", description = "需要登录的带路径参数的GET示例")
    @LogStyle(beforeDesc = "查询ID为:{0}的详情", afterDesc = "查询ID为:{0}的详情结果: {}")
    public R<String> getDetail(@PathVariable @NotBlank(message = "ID不能为空") String id) {
        logger.info("查询ID为:{}的详情", id);
        return R.data("ID: " + id + " 的详情数据");
    }

    /**
     * 需要登录的带查询参数的GET请求示例，API v2版本
     */
    @ApiVersion(2)
    @UserLoginToken
    @GetMapping("/search")
    @Operation(summary = "搜索示例接口", description = "需要登录的带查询参数的GET示例，API v2版本")
    @LogStyle(beforeDesc = "执行搜索，关键词:{0}", afterDesc = "搜索结果: {}")
    public R<String> search(
            @Parameter(description = "关键词") @RequestParam(required = false) String keyword,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer size) {
        logger.info("执行搜索，关键词:{}，页码:{}，每页数量:{}", keyword, page, size);
        return R.data("搜索结果，关键词: " + keyword + ", 页码: " + page + ", 每页数量: " + size);
    }

    /**
     * 需要登录的POST请求带请求体示例，API v1版本
     */
    @UserLoginToken
    @PostMapping("/add")
    @Operation(summary = "添加数据示例接口", description = "需要登录的POST带请求体示例")
    @LogStyle(beforeDesc = "添加数据，请求:{0}", afterDesc = "添加数据结果: {}")
    public R<ReqDemo> addData(@RequestBody @Validated({ValidGroup.Add.class}) ReqDemo reqDemo) {
        logger.info("添加数据，请求参数:{}", reqDemo);
        // 实际业务处理，这里简单返回请求参数
        return R.data(reqDemo);
    }

    /**
     * 需要登录的PUT请求带请求体示例，API v1版本
     */
    @UserLoginToken
    @PutMapping("/update")
    @Operation(summary = "更新数据示例接口", description = "需要登录的PUT带请求体示例")
    @LogStyle(beforeDesc = "更新数据，请求:{0}", afterDesc = "更新数据结果: {}")
    public R<ReqDemo> updateData(@RequestBody @Validated({ValidGroup.Update.class}) ReqDemo reqDemo) {
        logger.info("更新数据，请求参数:{}", reqDemo);
        // 实际业务处理，这里简单返回请求参数
        return R.data(reqDemo);
    }

    /**
     * 需要登录的DELETE请求示例，API v1版本
     */
    @UserLoginToken
    @DeleteMapping("/{id}")
    @Operation(summary = "删除数据示例接口", description = "需要登录的DELETE示例")
    @LogStyle(beforeDesc = "删除ID为:{0}的数据", afterDesc = "删除ID为:{0}的数据结果: {}")
    public R<Boolean> deleteData(@PathVariable @NotBlank(message = "ID不能为空") String id) {
        logger.info("删除ID为:{}的数据", id);
        // 实际删除操作，这里简单返回成功
        return R.data(true);
    }

    /**
     * 不需要登录的高级版本接口示例，API v3版本
     */
    @ApiVersion(3)
    @PassToken
    @GetMapping("/advanced")
    @Operation(summary = "高级版本接口示例", description = "API v3版本的高级功能示例")
    @LogStyle(beforeDesc = "访问高级版本接口", afterDesc = "高级版本接口访问结果: {}")
    public R<String> advancedFeature() {
        logger.info("访问高级版本接口");
        return R.data("这是API v3版本的高级功能");
    }
} 