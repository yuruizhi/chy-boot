package com.changyi.chy.interfaces.dto;

import com.changyi.chy.common.validation.ValidationGroups;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateDTO {
    
    @NotBlank(message = "用户名不能为空", groups = {ValidationGroups.Create.class})
    @Size(min = 4, max = 20, message = "用户名长度需在4-20之间", groups = {ValidationGroups.Create.class})
    private String username;
    
    @NotBlank(message = "密码不能为空", groups = {ValidationGroups.Create.class})
    @Size(min = 6, max = 32, message = "密码长度需在6-32之间", groups = {ValidationGroups.Create.class})
    private String password;
    
    @Email(message = "邮箱格式不正确", groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String email;
    
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确", groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String mobile;
} 