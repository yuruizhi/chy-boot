package org.chy.boot.system.controller;

import org.chy.boot.api.system.dto.UserDTO;
import org.chy.boot.common.api.R;
import org.chy.boot.persistence.request.PageRequest;
import org.chy.boot.persistence.result.PageResult;
import org.chy.boot.system.entity.User;
import org.chy.boot.system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户控制器
 *
 * @author YuRuizhi
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "用户管理", description = "用户相关接口")
public class UserController {

    private final UserService userService;

    /**
     * 分页查询用户列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询用户列表", description = "分页查询用户列表")
    @PreAuthorize("hasAuthority('system:user:list')")
    public R<PageResult<UserDTO>> page(PageRequest request) {
        PageResult<User> pageResult = userService.page(request);
        
        // 转换为DTO
        List<UserDTO> userDTOList = pageResult.getRecords().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        // 创建新的分页结果
        PageResult<UserDTO> result = new PageResult<>();
        BeanUtils.copyProperties(pageResult, result, "records");
        result.setRecords(userDTOList);
        
        return R.data(result);
    }

    /**
     * 获取用户详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取用户详情", description = "根据ID获取用户详情")
    @PreAuthorize("hasAuthority('system:user:query')")
    public R<UserDTO> getById(@Parameter(description = "用户ID") @PathVariable String id) {
        User user = userService.getById(id);
        return user != null ? R.data(convertToDTO(user)) : R.fail("用户不存在");
    }

    /**
     * 创建用户
     */
    @PostMapping
    @Operation(summary = "创建用户", description = "创建新用户")
    @PreAuthorize("hasAuthority('system:user:add')")
    public R<UserDTO> create(@Valid @RequestBody UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        R<User> result = userService.createUser(user);
        return result.isSuccess() ? R.data(convertToDTO(result.getData())) : R.fail(result.getCode(), result.getMsg());
    }

    /**
     * 更新用户
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新用户", description = "更新用户信息")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public R<Boolean> update(@Parameter(description = "用户ID") @PathVariable String id, 
                             @Valid @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        User user = convertToEntity(userDTO);
        return userService.updateUser(user);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户", description = "根据ID删除用户")
    @PreAuthorize("hasAuthority('system:user:remove')")
    public R<Boolean> delete(@Parameter(description = "用户ID") @PathVariable String id) {
        return userService.deleteUser(id);
    }

    /**
     * 修改用户状态
     */
    @PutMapping("/{id}/status/{status}")
    @Operation(summary = "修改用户状态", description = "修改用户状态（启用/禁用）")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public R<Boolean> changeStatus(@Parameter(description = "用户ID") @PathVariable String id,
                                 @Parameter(description = "状态(1:正常,0:禁用)") @PathVariable Integer status) {
        return userService.changeStatus(id, status);
    }

    /**
     * 重置密码
     */
    @PutMapping("/{id}/password")
    @Operation(summary = "重置密码", description = "重置用户密码")
    @PreAuthorize("hasAuthority('system:user:resetPwd')")
    public R<Boolean> resetPassword(@Parameter(description = "用户ID") @PathVariable String id,
                                  @Parameter(description = "新密码") @RequestParam String password) {
        return userService.resetPassword(id, password);
    }

    /**
     * 实体转DTO
     */
    private UserDTO convertToDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        // 密码不返回
        userDTO.setPassword(null);
        return userDTO;
    }

    /**
     * DTO转实体
     */
    private User convertToEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }
} 