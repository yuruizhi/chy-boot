package com.chy.boot.admin.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chy.boot.common.commons.annotation.RequireAdmin;
import com.chy.boot.api.dto.request.CreateDemoDTO;
import com.chy.boot.api.dto.request.UpdateDemoDTO;
import com.chy.boot.api.service.DemoService;
import com.chy.boot.api.vo.DemoVO;
import com.chy.boot.common.commons.api.R;
import com.chy.boot.common.commons.api.ResultCode;
import com.chy.boot.common.commons.idempotent.annotation.Idempotent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 管理后台Demo控制器
 *
 * @author YuRuizhi
 * @date 2024/3/18
 */
@Tag(name = "Demo管理", description = "管理后台Demo资源的CRUD操作")
@RestController
@RequestMapping("/api/v1/demos")
@RequireAdmin("Demo管理功能仅限后台管理访问")
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
     * 创建Demo
     */
    @Operation(
        summary = "创建Demo",
        description = "创建一个新的Demo"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "创建成功"),
        @ApiResponse(responseCode = "400", description = "参数错误")
    })
    @Idempotent(
        timeout = 60,
        timeUnit = TimeUnit.SECONDS,
        prefix = "demo:create:idempotent",
        deleteOnExecution = true
    )
    @PostMapping
    @PreAuthorize("hasAuthority('DEMO_MANAGE')")
    public R<DemoVO> create(@Validated @RequestBody CreateDemoDTO dto) {
        // 检查标识码唯一性
        if (demoService.existsByCode(dto.getDemoCode())) {
            return R.fail(ResultCode.PARAM_VALID_ERROR, "标识码已存在");
        }
        
        DemoVO demo = demoService.create(dto);
        return R.data(demo);
    }

    /**
     * 更新Demo
     */
    @Operation(
        summary = "更新Demo",
        description = "更新现有Demo信息"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "更新成功"),
        @ApiResponse(responseCode = "404", description = "资源不存在"),
        @ApiResponse(responseCode = "400", description = "参数错误")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('DEMO_MANAGE')")
    public R<DemoVO> update(
            @PathVariable String id,
            @Validated @RequestBody UpdateDemoDTO dto) {
        
        // 检查存在性
        DemoVO existingDemo = demoService.getById(id);
        if (existingDemo == null) {
            return R.fail(ResultCode.NOT_FOUND, "Demo不存在");
        }
        
        // 确保ID一致
        if (!id.equals(dto.getDemoId())) {
            return R.fail(ResultCode.PARAM_VALID_ERROR, "ID不匹配");
        }
        
        DemoVO updatedDemo = demoService.update(dto);
        return R.data(updatedDemo);
    }

    /**
     * 删除Demo
     */
    @Operation(
        summary = "删除Demo",
        description = "根据ID删除Demo"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "删除成功"),
        @ApiResponse(responseCode = "404", description = "资源不存在")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DEMO_MANAGE')")
    public R<Boolean> delete(@PathVariable String id) {
        // 检查存在性
        DemoVO existingDemo = demoService.getById(id);
        if (existingDemo == null) {
            return R.fail(ResultCode.NOT_FOUND, "Demo不存在");
        }
        
        boolean success = demoService.removeById(id);
        return success ? R.data(true) : R.fail("删除失败");
    }

    /**
     * 更新Demo状态
     */
    @Operation(
        summary = "更新Demo状态",
        description = "更新Demo的状态"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "更新成功"),
        @ApiResponse(responseCode = "404", description = "资源不存在")
    })
    @PutMapping("/{id}/status/{status}")
    @PreAuthorize("hasAuthority('DEMO_MANAGE')")
    public R<Boolean> updateStatus(
            @PathVariable String id,
            @PathVariable Integer status) {
        
        // 检查存在性
        DemoVO existingDemo = demoService.getById(id);
        if (existingDemo == null) {
            return R.fail(ResultCode.NOT_FOUND, "Demo不存在");
        }
        
        boolean success = demoService.updateStatus(id, status);
        return success ? R.data(true) : R.fail("状态更新失败");
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