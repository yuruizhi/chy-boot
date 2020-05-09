package com.changyi.chy.commons.platform.auth.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2020-01-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DanoneChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String channelId;

    private String channelName;

    private String channelSecret;

    private String channelType;

    /**
     * 1.正常  2.删除
     */
    private Integer status;


}
