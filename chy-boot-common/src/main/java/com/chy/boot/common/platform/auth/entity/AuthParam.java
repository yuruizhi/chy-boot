package com.chy.boot.common.platform.auth.entity;


import com.chy.boot.common.component.validate.ValidateMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * 认证参数.
 *
 * @author Henry.yu
 * @date 2020.6.5
 */
@Data
public class AuthParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "账号", title = "账号", required = true, example = "admin")
    @NotNull(message = ValidateMessage.NotNull)
    @Length(min = 3, message = ValidateMessage.MinLength)
    private String account;

    @Schema(name = "密码", title = "密码", required = true, example = "123456")
    @NotNull(message = ValidateMessage.NotNull)
    @Length(min = 3, message = ValidateMessage.MinLength)
    private String password;
}
