package com.chy.boot.commons.platform.auth.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户对象.
 *
 * @author YuRuizhi
 * @since 2020.6.5
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String account;

    private String password;

    private String nickname;

    /**
     * 1.正常  2.删除
     */
    private Integer status;

    public User (String userId, String account, String password, String nickname) {
        this.userId = userId;
        this.account = account;
        this.password = password;
        this.nickname = nickname;
        this.status = 1;
    }


}
