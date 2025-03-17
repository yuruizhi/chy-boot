package com.changyi.chy.interfaces.api;

import com.changyi.chy.common.api.ApiResult;
import com.changyi.chy.common.validation.ValidationGroups;
import com.changyi.chy.interfaces.dto.UserCreateDTO;
import com.changyi.chy.interfaces.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    // 假设有一个UserService
    // private final UserService userService;
    
    @PostMapping
    public ApiResult<UserVO> createUser(
            @RequestBody @Validated(ValidationGroups.Create.class) UserCreateDTO userDTO) {
        // 调用服务处理逻辑
        // UserVO userVO = userService.createUser(userDTO);
        // return ApiResult.success(userVO);
        
        // 暂时返回模拟数据
        UserVO userVO = new UserVO();
        userVO.setId(1L);
        userVO.setUsername(userDTO.getUsername());
        userVO.setEmail(userDTO.getEmail());
        userVO.setMobile(userDTO.getMobile());
        return ApiResult.success(userVO);
    }
} 