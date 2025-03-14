package com.changyi.chy.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.changyi.chy.persistence.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户表(User)实体类
 *
 * @author YuRuizhi
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class User extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 昵称
     */
    private String nickname;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 头像
     */
    private String avatar;
    
    /**
     * 部门ID
     */
    private String deptId;
    
    /**
     * 最后登录IP
     */
    private String lastLoginIp;
    
    /**
     * 最后登录时间
     */
    private java.time.LocalDateTime lastLoginTime;
} 