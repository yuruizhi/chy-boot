package com.changyi.chy.security.auth.service.impl;



import com.changyi.chy.security.auth.entity.User;
import com.changyi.chy.security.auth.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-01-10
 */
@Service
public class UserServiceImpl implements IUserService {

    @Override
    public User getByAccount(String account) {

        return new User("1", "admin", "123456", "超级管理员");
    }
}
