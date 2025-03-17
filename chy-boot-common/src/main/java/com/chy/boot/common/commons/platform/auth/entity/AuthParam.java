package com.chy.boot.common.commons.platform.auth.entity;


import com.chy.boot.common.commons.component.validate.ValidateMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 * 认证参数.
 *
 * @author YuRuizhi
 * @date 2020.6.5
 */
@Data
public class AuthParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "账号", required = true, example = "admin")
    @NotNull(message = ValidateMessage.NotNull)
    @Size(min = 3, message = ValidateMessage.MinLength)
    private String account;

    @Schema(description = "密码", required = true, example = "123456")
    @NotNull(message = ValidateMessage.NotNull)
    @Size(min = 3, message = ValidateMessage.MinLength)
    private String password;
}
