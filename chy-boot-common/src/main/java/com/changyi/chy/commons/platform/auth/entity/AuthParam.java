package com.changyi.chy.commons.platform.auth.entity;


import com.changyi.chy.commons.component.validate.ValidateMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class AuthParam implements Serializable {

    @ApiModelProperty(value = "平台分配的渠道id", notes = "平台分配的渠道id", required = true, example = "dev-id")
    @NotNull(message = ValidateMessage.NotNull)
    @Size(min = 3, message = ValidateMessage.Size)
    private String id;

    @ApiModelProperty(value = "平台分配的渠道secret", notes = "平台分配的渠道secret", required = true, example = "test-secret")
    @NotNull(message = ValidateMessage.NotNull)
    @Size(min = 3, message = ValidateMessage.Size)
    private String secret;
}
