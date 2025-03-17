package com.changyi.chy.api.business.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


import java.io.Serializable;

/**
 * 示例请求对象
 *
 * @author YuRuizhi
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "示例请求对象")
public class DemoRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    @Schema(description = "手机号")
    private String mobile;

    /**
     * 小程序openId
     */
    @Schema(description = "小程序openId")
    private String openId;

    /**
     * 小程序unionId
     */
    @Schema(description = "小程序unionId")
    private String unionId;
} 