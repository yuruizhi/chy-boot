package com.changyi.chy.commons.platform.auth.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.changyi.chy.commons.exception.DnConsoleException;
import com.changyi.chy.commons.exception.ExceptionStringPool;
import com.changyi.chy.commons.jackson.JsonUtil;
import com.changyi.chy.commons.platform.auth.entity.AuthResponse;
import com.changyi.chy.commons.platform.auth.entity.DanoneChannel;
import com.changyi.chy.commons.platform.auth.service.AuthService;
import com.changyi.chy.commons.platform.auth.service.IDnChannelService;
import com.changyi.chy.commons.util.DigestUtil;
import com.changyi.chy.commons.util.Func;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTAuthService implements AuthService {

    Logger logger = LoggerFactory.getLogger(getClass());

    public static final String key = "@@chang##yi$$zhao&&chu**rui@@";


    @Autowired
    IDnChannelService channelService;

    @Override
    public AuthResponse getToken(String id, String Secret) throws DnConsoleException {

        String channelid = checkSecret(id,Secret);


        Algorithm alg = Algorithm.HMAC256(key);

        Date currentTime = new Date();

        Date expDate = new Date(currentTime.getTime() + 3600*1000L*2);
        // 1 签发Token

        String token = JWT.create()
                .withIssuer("chy-boot") // 发行者
               // .withSubject(id) // 用户身份标识
                .withAudience(channelid) // 用户单位
                .withIssuedAt(currentTime) // 签发时间
                .withExpiresAt(expDate) // 一小时有效期
               // .withJWTId("001") // 分配JWT的ID
               // .withClaim("PublicClaimExample", "You should not pass!") // 定义公共域信息
                .sign(alg);

        logger.debug("生成的Token是:{}",token);

        AuthResponse result = new AuthResponse();
        result.setAccess_token(token);
        result.setExpire(expDate.getTime());

        return result;

    }

    private String checkSecret(String id, String Secret){
        DanoneChannel channel = channelService.getById(id);

        if (Func.notNull(channel)&&channel.getStatus().equals(1)){

            logger.info("渠道不为空{}",JsonUtil.toJson(channel));
            if (!channel.getChannelSecret().equals(DigestUtil.md5Hex(Secret))){
                throw new DnConsoleException(ExceptionStringPool.password_error);
            }
            return channel.getChannelId();
        }else {

            logger.info("渠道为空");
            throw new DnConsoleException(ExceptionStringPool.password_error);
        }
    }
}
