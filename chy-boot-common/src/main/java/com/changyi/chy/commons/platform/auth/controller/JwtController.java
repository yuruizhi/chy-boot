package com.changyi.chy.commons.platform.auth.controller;

import com.changyi.chy.commons.api.R;
import com.changyi.chy.commons.component.log.LogStyle;
import com.changyi.chy.commons.platform.auth.component.PassToken;
import com.changyi.chy.commons.platform.auth.entity.AuthParam;
import com.changyi.chy.commons.platform.auth.entity.AuthResponse;
import com.changyi.chy.commons.platform.auth.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * jwt控制器.
 *
 * @author Henry.yu
 * @since 2020.1.8
 */
@RestController
@RequestMapping("/auth")
@Api(value = "接口鉴权", description = "接口鉴权管理", tags = "接口鉴权管理")
public class JwtController {

    @Autowired
    AuthService authService;


    @ApiOperation(value = "获取授权token接口", notes = "访问业务所有接口都需要在header中带上access_token,可重复使用，过期时间2个小时，每日请求上线2000次，必须缓存起来，禁止每次使用都请求新的")
    @LogStyle(beforeDesc = "获取token:{0}", afterDesc = "获取token成功: {}")
    @ResponseBody
    @PassToken
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public R<AuthResponse> getToken(@RequestBody @Validated AuthParam authParam) {

        return R.data(authService.getToken(authParam.getAccount(), authParam.getPassword()));
    }

}
