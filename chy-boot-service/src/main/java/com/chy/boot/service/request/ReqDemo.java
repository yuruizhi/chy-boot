package com.chy.boot.service.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * demo
 *
 * @author Henry.Yu
 * @since 2020.5.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "ReqDemo", description = "ReqDemo")
public class ReqDemo implements Serializable {

    @Schema(description = "手机号", nullable = true)
    private String mobile;

    @Schema(description = "小程序openId", nullable = true)
    private String openId;

    @Schema(description = "小程序unionId", nullable = true)
    private String unionId;


}
