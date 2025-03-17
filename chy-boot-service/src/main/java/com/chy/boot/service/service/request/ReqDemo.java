package com.chy.boot.service.service.request;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * demo
 *
 * @author YuRuizhi
 * @since 2020.5.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name="ReqDemo", description="ReqDemo")
public class ReqDemo implements Serializable {

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String mobile;

    @Schema(description = "小程序openId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String openId;

    @Schema(description = "小程序unionId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String unionId;


}
