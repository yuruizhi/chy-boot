package com.changyi.chy.demo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="RepDemo", description="RepDemo")
public class RepDemo implements Serializable {

    @ApiModelProperty(value = "会员id")
    private String id;

    @ApiModelProperty(value = "群脉客户id")
    private String qmMemberid;

    @ApiModelProperty(value = "注册渠道：wechat-mp:微信小程序;h5-H5渠道;qm-群脉")
    private String sourceType;

    @ApiModelProperty(value = "1-注册；0-群脉同步")
    private Integer dataSource;

    @ApiModelProperty(value = "会员注册渠道(不支持更新)")
    private String registerSource;

    @ApiModelProperty(value = "第一次的访问渠道")
    private String accessSource;

    @ApiModelProperty(value = "在中台注册会员时的调用平台h5-channel,ali-channel(不支持更新)")
    private String channelSource;

    @ApiModelProperty(value = "品牌")
    private String brand;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "妈妈状态：1-备孕,2- 怀孕,3- 宝妈，对应群脉status")
    private Integer motherStatus;

    @ApiModelProperty(value = "1-正常，0-删除")
    private Integer status;

    @ApiModelProperty(value = "创建(注册)时间")
    private Long createTime;

    @ApiModelProperty(value = "修改时间")
    private Long updateTime;
}
