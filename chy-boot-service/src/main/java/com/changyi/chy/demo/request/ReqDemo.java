package com.changyi.chy.demo.request;


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
@ApiModel(value="ReqDemo", description="ReqDemo")
public class ReqDemo implements Serializable {

    @ApiModelProperty(value = "手机号", notes = "不可与openId/unionId同时为空")
    private String mobile;

    @ApiModelProperty(value = "小程序openId", notes = "不可与手机号/unionId同时为空")
    private String openId;

    @ApiModelProperty(value = "小程序unionId", notes = "不可与openId/手机号同时为空")
    private String unionId;


}
