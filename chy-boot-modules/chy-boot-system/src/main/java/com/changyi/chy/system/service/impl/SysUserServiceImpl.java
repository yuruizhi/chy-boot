package org.chy.boot.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changyi.chy.common.auth.entity.User;
import com.changyi.chy.common.auth.service.UserService;
import com.changyi.chy.common.util.DigestUtil;
import com.changyi.chy.common.util.Func;
import com.changyi.chy.system.entity.SysUser;
import com.changyi.chy.system.mapper.SysUserMapper;
import com.changyi.chy.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统用户服务实现
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService, UserService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final SysUserMapper userMapper;

    @Override
    public User getUserById(String userId) {
        // 从数据库获取SysUser对象
        SysUser sysUser = this.getById(userId);
        if (sysUser == null) {
            return null;
        }
        
        // 转换为通用User对象
        return convertToUser(sysUser);
    }

    @Override
    public User getUserByUsername(String username) {
        // 从数据库获取SysUser对象
        SysUser sysUser = this.findByUsername(username);
        if (sysUser == null) {
            return null;
        }
        
        // 转换为通用User对象
        return convertToUser(sysUser);
    }
    
    @Override
    public User validateUser(String username, String password) {
        if (Func.isBlank(username) || Func.isBlank(password)) {
            return null;
        }
        
        SysUser sysUser = this.findByUsername(username);
        if (sysUser == null) {
            return null;
        }
        
        // 验证密码 - 密码采用MD5加密存储
        String encodedPassword = DigestUtil.md5Hex(password);
        if (!Func.equals(encodedPassword, sysUser.getPassword())) {
            logger.info("用户密码不匹配: {}", username);
            return null;
        }
        
        return convertToUser(sysUser);
    }

    @Override
    public List<SysUser> queryAllByLimit(int offset, int limit) {
        return userMapper.queryAllByLimit(offset, limit);
    }

    @Override
    public SysUser findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public boolean updatePassword(String userId, String oldPassword, String newPassword) {
        SysUser user = this.getById(userId);
        if (user == null) {
            return false;
        }
        
        // 验证旧密码
        String encodedOldPassword = DigestUtil.md5Hex(oldPassword);
        if (!Func.equals(encodedOldPassword, user.getPassword())) {
            return false;
        }
        
        // 更新密码
        user.setPassword(DigestUtil.md5Hex(newPassword));
        return this.updateById(user);
    }
    
    /**
     * 将系统用户实体转换为通用用户实体
     */
    private User convertToUser(SysUser sysUser) {
        if (sysUser == null) {
            return null;
        }
        
        User user = new User();
        user.setId(sysUser.getId());
        user.setUsername(sysUser.getUsername());
        user.setPassword(sysUser.getPassword());
        user.setRealName(sysUser.getRealName());
        user.setEmail(sysUser.getEmail());
        user.setPhone(sysUser.getPhone());
        user.setStatus(sysUser.getStatus());
        user.setCreateTime(sysUser.getCreateTime());
        user.setUpdateTime(sysUser.getUpdateTime());
        
        return user;
    }
} 