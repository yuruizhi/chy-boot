package com.changyi.chy.business.demo.controller;

import com.changyi.chy.api.business.dto.DemoDTO;
import com.changyi.chy.business.demo.entity.Demo;
import com.changyi.chy.business.demo.service.DemoService;
import com.changyi.chy.common.api.R;
import com.changyi.chy.persistence.result.PageResult;
import com.changyi.chy.persistence.request.PageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 示例控制器
 *
 * @author YuRuizhi
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/demos")
@RequiredArgsConstructor
@Tag(name = "Demo管理", description = "示例相关接口")
public class DemoController {

    private final DemoService demoService;

    /**
     * 分页查询
     *
     * @param request 分页请求
     * @return 分页结果
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询", description = "分页查询示例列表")
    @PreAuthorize("hasAuthority('business:demo:list')")
    public R<PageResult<DemoDTO>> page(PageRequest request) {
        PageResult<Demo> pageResult = demoService.page(request);
        
        // 转换DTO
        List<DemoDTO> dtoList = pageResult.getRecords().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        PageResult<DemoDTO> dtoPageResult = new PageResult<>();
        BeanUtils.copyProperties(pageResult, dtoPageResult);
        dtoPageResult.setRecords(dtoList);
        
        return R.data(dtoPageResult);
    }

    /**
     * 获取详情
     *
     * @param id ID
     * @return 详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取详情", description = "根据ID获取示例详情")
    @PreAuthorize("hasAuthority('business:demo:query')")
    public R<DemoDTO> getById(@Parameter(description = "示例ID") @PathVariable String id) {
        Demo demo = demoService.getById(id);
        if (demo == null) {
            return R.fail("数据不存在");
        }
        return R.data(convertToDTO(demo));
    }

    /**
     * 新增
     *
     * @param demoDTO 示例信息
     * @return 新增结果
     */
    @PostMapping
    @Operation(summary = "新增示例", description = "新增示例信息")
    @PreAuthorize("hasAuthority('business:demo:add')")
    public R<DemoDTO> add(@Valid @RequestBody DemoDTO demoDTO) {
        Demo demo = convertToEntity(demoDTO);
        R<Demo> result = demoService.insert(demo);
        
        return result.isSuccess() 
                ? R.data(convertToDTO(result.getData())) 
                : R.fail(result.getMsg());
    }

    /**
     * 修改
     *
     * @param id      ID
     * @param demoDTO 示例信息
     * @return 修改结果
     */
    @PutMapping("/{id}")
    @Operation(summary = "修改示例", description = "修改示例信息")
    @PreAuthorize("hasAuthority('business:demo:edit')")
    public R<Boolean> update(
            @Parameter(description = "示例ID") @PathVariable String id,
            @Valid @RequestBody DemoDTO demoDTO) {
        demoDTO.setId(id);
        Demo demo = convertToEntity(demoDTO);
        return demoService.update(demo);
    }

    /**
     * 删除
     *
     * @param id ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除示例", description = "根据ID删除示例")
    @PreAuthorize("hasAuthority('business:demo:remove')")
    public R<Boolean> delete(@Parameter(description = "示例ID") @PathVariable String id) {
        return demoService.deleteById(id);
    }
    
    /**
     * 修改状态
     *
     * @param id     ID
     * @param status 状态
     * @return 修改结果
     */
    @PutMapping("/{id}/status/{status}")
    @Operation(summary = "修改状态", description = "修改示例状态")
    @PreAuthorize("hasAuthority('business:demo:edit')")
    public R<Boolean> changeStatus(
            @Parameter(description = "示例ID") @PathVariable String id,
            @Parameter(description = "状态（1正常 0停用）") @PathVariable Integer status) {
        
        Demo demo = demoService.getById(id);
        if (demo == null) {
            return R.fail("数据不存在");
        }
        
        demo.setStatus(status);
        return demoService.update(demo);
    }
    
    /**
     * 实体转DTO
     *
     * @param demo 实体
     * @return DTO
     */
    private DemoDTO convertToDTO(Demo demo) {
        if (demo == null) {
            return null;
        }
        DemoDTO demoDTO = new DemoDTO();
        BeanUtils.copyProperties(demo, demoDTO);
        return demoDTO;
    }
    
    /**
     * DTO转实体
     *
     * @param demoDTO DTO
     * @return 实体
     */
    private Demo convertToEntity(DemoDTO demoDTO) {
        if (demoDTO == null) {
            return null;
        }
        Demo demo = new Demo();
        BeanUtils.copyProperties(demoDTO, demo);
        return demo;
    }
} 