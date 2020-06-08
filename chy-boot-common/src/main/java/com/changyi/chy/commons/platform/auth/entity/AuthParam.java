package com.changyi.chy.commons.platform.auth.entity;


import com.changyi.chy.commons.component.validate.ValidateMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @ApiModelProperty(value = "账号", notes = "账号", required = true, example = "admin")
    @NotNull(message = ValidateMessage.NotNull)
    @Length(min = 3, message = ValidateMessage.MinLength)
    private String account;

    @ApiModelProperty(value = "密码", notes = "密码", required = true, example = "123456")
    @NotNull(message = ValidateMessage.NotNull)
    @Length(min = 3, message = ValidateMessage.MinLength)
    private String password;
}
