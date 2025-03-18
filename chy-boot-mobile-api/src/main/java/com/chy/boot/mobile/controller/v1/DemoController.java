package com.chy.boot.mobile.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chy.boot.api.service.DemoService;
import com.chy.boot.api.vo.DemoVO;
import com.chy.boot.common.commons.api.R;
import com.chy.boot.common.commons.api.ResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 移动端Demo控制器
 * 仅提供查询功能，不含管理功能
 *
 * @author YuRuizhi
 * @date 2024/3/18
 */
@Tag(name = "Demo浏览", description = "移动端Demo资源的查询操作")
@RestController
@RequestMapping("/mobile/v1/demos")
public class DemoController {

    private final DemoService demoService;

    @Autowired
    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * 获取Demo列表（分页）
     */
    @Operation(
        summary = "获取Demo列表",
        description = "分页获取Demo列表，支持条件筛选"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "查询成功"),
        @ApiResponse(responseCode = "500", description = "服务器错误")
    })
    @GetMapping
    public R<IPage<DemoVO>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "category", required = false) String category) {
        
        IPage<DemoVO> voPage = demoService.page(page, size, name, code, category);
        return R.data(voPage);
    }

    /**
     * 获取单个Demo
     */
    @Operation(
        summary = "获取单个Demo",
        description = "根据ID获取单个Demo详细信息"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "查询成功"),
        @ApiResponse(responseCode = "404", description = "资源不存在")
    })
    @GetMapping("/{id}")
    public R<DemoVO> getById(@PathVariable String id) {
        DemoVO demo = demoService.getById(id);
        if (demo == null) {
            return R.fail(ResultCode.NOT_FOUND, "Demo不存在");
        }
        
        return R.data(demo);
    }

    /**
     * 根据分类获取Demo
     */
    @Operation(
        summary = "根据分类获取Demo",
        description = "获取指定分类的所有Demo"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "查询成功")
    })
    @GetMapping("/category/{category}")
    public R<List<DemoVO>> getByCategory(@PathVariable String category) {
        List<DemoVO> demoList = demoService.listByCategory(category);
        return R.data(demoList);
    }
} 