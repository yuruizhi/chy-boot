package com.changyi.chy.demo.controller;

import com.changyi.chy.commons.annotation.ApiVersion;
import com.changyi.chy.commons.api.R;
import com.changyi.chy.demo.entity.User;
import com.changyi.chy.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

/**
 * 用户表(User)控制层
 *
 * @author YuRuizhi
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("user")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @Operation(summary = "通过ID查询单条数据")
    @ApiVersion(3)
    @GetMapping("/{id}")
    public R<User> getById(@Parameter(description = "用户ID", required = true) @PathVariable Long id) {
        return R.data(this.userService.getById(id));
    }

    /**
     * 分页查询所有数据
     *
     * @param page 页码
     * @param size 每页大小
     * @return 分页数据
     */
    @Operation(summary = "分页查询所有数据")
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public R<List<User>> list(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {
        int offset = (page - 1) * size;
        List<User> list = this.userService.queryAllByLimit(offset, size);
        return R.data(list);
    }

    /**
     * 新增数据
     *
     * @param user 实体
     * @return 新增结果
     */
    @Operation(summary = "新增数据")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public R<Boolean> save(@Valid @RequestBody User user) {
        return R.data(this.userService.save(user));
    }

    /**
     * 修改数据
     *
     * @param user 实体
     * @return 修改结果
     */
    @Operation(summary = "修改数据")
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public R<Boolean> update(@Valid @RequestBody User user) {
        return R.data(this.userService.updateById(user));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除结果
     */
    @Operation(summary = "删除数据")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public R<Boolean> delete(@PathVariable Long id) {
        return R.data(this.userService.removeById(id));
    }
} 