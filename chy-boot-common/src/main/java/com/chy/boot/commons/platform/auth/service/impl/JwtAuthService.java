package com.chy.boot.commons.platform.auth.service.impl;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.chy.boot.commons.exception.DnConsoleException;
import com.chy.boot.commons.exception.ExceptionStringPool;
import com.chy.boot.commons.jackson.JsonUtil;
import com.chy.boot.commons.platform.auth.entity.AuthResponse;
import com.chy.boot.commons.platform.auth.entity.User;
import com.chy.boot.commons.platform.auth.service.AuthService;
import com.chy.boot.commons.platform.auth.service.IUserService;
import com.chy.boot.commons.util.Func;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 *
 */
@Service
public class JwtAuthService implements AuthService {

    Logger logger = LoggerFactory.getLogger(getClass());

    public static final String key = "@@chang##yi$$zhao&&chu**rui@@";


    @Autowired
    IUserService userService;

    @Override
    public AuthResponse getToken(String account, String password) throws DnConsoleException {

        String channelId = checkPwd(account, password);

        Algorithm alg = Algorithm.HMAC256(key);

        Date currentTime = new Date();

        // TODO 考虑动态控制
        Date expDate = new Date(currentTime.getTime() + 3600 * 1000L * 2);

        // 1 签发Token
        String token = JWT.create()
                .withIssuer("chy-boot") // 发行者
                // .withSubject(id) // 用户身份标识
                .withAudience(channelId) // 用户单位
                .withIssuedAt(currentTime) // 签发时间
                .withExpiresAt(expDate) // 一小时有效期
                // .withJWTId("001") // 分配JWT的ID
                // .withClaim("PublicClaimExample", "You should not pass!") // 定义公共域信息
                .sign(alg);

        logger.debug("生成的Token是:{}", token);

        AuthResponse result = new AuthResponse();
        result.setAccessToken(token);
        result.setExpire(expDate.getTime());

        return result;

    }

    private String checkPwd(String account, String password) {

        User user = userService.getByAccount(account);

        if (Func.notNull(user) && user.getStatus().equals(1)) {

            logger.info("用户不为空: {}", JsonUtil.toJson(user));
            /*if (!user.getPassword().equals(DigestUtil.md5Hex(password))) {
                throw new DnConsoleException(ExceptionStringPool.password_error);
            }*/
            return user.getUserId();
        } else {
            throw new DnConsoleException(ExceptionStringPool.ACCOUNT_OR_PASSWORD_ERROR);
        }
    }
}
