package com.chy.boot.rest.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chy.boot.common.commons.api.R;
import com.chy.boot.common.commons.api.ResultCode;
import com.chy.boot.common.commons.idempotent.annotation.Idempotent;
import com.chy.boot.common.commons.idempotent.service.IdempotentTokenService;
import com.chy.boot.common.commons.component.validate.ValidGroup;
import com.chy.boot.rest.core.entity.Demo;
import com.chy.boot.rest.dto.CreateDemoDTO;
import com.chy.boot.rest.dto.DemoQueryParam;
import com.chy.boot.rest.dto.UpdateDemoDTO;
import com.chy.boot.rest.vo.DemoVO;
import com.chy.boot.service.service.converter.DemoConverter;
import com.chy.boot.service.service.service.DemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
 * Demo控制器 - RESTful风格API
 *
 * @author YuRuizhi
 * @date 2024/3/17
 */
@Tag(name = "Demo管理", description = "提供Demo资源的CRUD操作")
@RestController
@RequestMapping("/api/v1/demos")
public class DemoController {

    private final DemoService demoService;
    private final DemoConverter demoConverter;
    private final IdempotentTokenService idempotentTokenService;

    @Autowired
    public DemoController(DemoService demoService, 
                         DemoConverter demoConverter,
                         IdempotentTokenService idempotentTokenService) {
        this.demoService = demoService;
        this.demoConverter = demoConverter;
        this.idempotentTokenService = idempotentTokenService;
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
            DemoQueryParam queryParam,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        
        Page<Demo> pageParam = new Page<>(page, size);
        IPage<Demo> demoPage = demoService.getPage(pageParam, queryParam);
        
        // 转换为VO
        IPage<DemoVO> voPage = demoPage.convert(demoConverter::convertToVO);
        
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
        Demo demo = demoService.getByIdWithCache(id);
        if (demo == null) {
            return R.fail(ResultCode.NOT_FOUND, "Demo不存在");
        }
        
        return R.data(demoConverter.convertToVO(demo));
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
        
        // 转换并保存
        Demo demo = demoConverter.convertToDO(dto);
        boolean success = demoService.saveWithCache(demo);
        
        if (!success) {
            return R.fail(ResultCode.FAILURE, "保存失败");
        }
        
        return R.data(demoConverter.convertToVO(demo));
    }

    /**
     * 更新Demo
     */
    @Operation(
        summary = "更新Demo",
        description = "更新现有Demo的信息"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "更新成功"),
        @ApiResponse(responseCode = "400", description = "参数错误"),
        @ApiResponse(responseCode = "404", description = "资源不存在")
    })
    @Idempotent(
        timeout = 60,
        timeUnit = TimeUnit.SECONDS,
        prefix = "demo:update:idempotent",
        deleteOnExecution = true
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('DEMO_MANAGE')")
    public R<DemoVO> update(
            @PathVariable String id,
            @Validated @RequestBody UpdateDemoDTO dto) {
        
        // 验证ID一致性
        if (!id.equals(dto.getDemoId())) {
            return R.fail(ResultCode.PARAM_VALID_ERROR, "ID不一致");
        }
        
        // 检查存在性
        Demo existingDemo = demoService.getById(id);
        if (existingDemo == null) {
            return R.fail(ResultCode.NOT_FOUND, "Demo不存在");
        }
        
        // 检查标识码唯一性（如果修改了标识码）
        if (!dto.getDemoCode().equals(existingDemo.getDemoCode()) && 
                demoService.existsByCode(dto.getDemoCode())) {
            return R.fail(ResultCode.PARAM_VALID_ERROR, "标识码已存在");
        }
        
        // 转换并更新
        Demo demo = demoConverter.convertToDO(dto);
        boolean success = demoService.updateByIdWithCache(demo);
        
        if (!success) {
            return R.fail(ResultCode.FAILURE, "更新失败");
        }
        
        // 获取最新数据
        Demo updated = demoService.getByIdWithCache(id);
        return R.data(demoConverter.convertToVO(updated));
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
        Demo existingDemo = demoService.getById(id);
        if (existingDemo == null) {
            return R.fail(ResultCode.NOT_FOUND, "Demo不存在");
        }
        
        boolean success = demoService.removeByIdWithCache(id);
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
        Demo existingDemo = demoService.getById(id);
        if (existingDemo == null) {
            return R.fail(ResultCode.NOT_FOUND, "Demo不存在");
        }
        
        boolean success = demoService.updateStatus(id, status);
        return success ? R.data(true) : R.fail("状态更新失败");
    }

    /**
     * 批量获取Demo
     */
    @Operation(
        summary = "批量获取Demo",
        description = "根据ID列表批量获取Demo"
    )
    @PostMapping("/batch")
    public R<List<DemoVO>> batchGet(@RequestBody List<String> ids) {
        List<Demo> demoList = demoService.listByIds(ids);
        return R.data(demoConverter.convertToVOList(demoList));
    }

    /**
     * 获取幂等性Token
     */
    @Operation(
        summary = "获取幂等性Token",
        description = "获取创建或更新Demo时需要的幂等性Token"
    )
    @GetMapping("/token")
    public R<String> getIdempotentToken(
            @RequestParam(value = "type", defaultValue = "create") String type) {
        
        String prefix = "demo:" + type + ":idempotent";
        // 使用正确的方法签名，默认60秒过期
        String token = idempotentTokenService.createToken(prefix, 60, TimeUnit.SECONDS);
        
        return R.data(token);
    }
} 