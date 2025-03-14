package com.changyi.chy.commons.platform.auth.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

/**
 * 认证返回值.
 *
 * @author YuRuizhi
 * @date 2020.6.5
 */
@Data
public class AuthResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "请求其他接口时，需要放入header中，用来鉴权")
    private String accessToken;

    @Schema(description = "access_token过期时间-Unix毫秒时间戳")
    private Long expire;
}
