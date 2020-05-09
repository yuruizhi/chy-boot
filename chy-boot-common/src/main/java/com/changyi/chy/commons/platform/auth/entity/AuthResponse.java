package com.changyi.chy.commons.platform.auth.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AuthResponse implements Serializable {

    @ApiModelProperty(notes = "请求其他接口时，需要放入header中，用来鉴权")
    private String access_token;

    @ApiModelProperty(notes = "access_token过期时间-Unix毫秒时间戳")
    private Long expire;
}
