package com.changyi.chy.commons.platform.auth.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 认证返回值.
 *
 * @author Henry.yu
 * @date 2020.6.5
 */
@Data
public class AuthResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "请求其他接口时，需要放入header中，用来鉴权")
    private String accessToken;

    @ApiModelProperty(notes = "access_token过期时间-Unix毫秒时间戳")
    private Long expire;
}
