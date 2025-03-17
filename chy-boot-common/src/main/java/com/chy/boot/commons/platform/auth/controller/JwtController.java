package com.chy.boot.commons.platform.auth.controller;

import com.chy.boot.commons.api.R;
import com.chy.boot.commons.component.log.LogStyle;
import com.chy.boot.commons.platform.auth.component.PassToken;
import com.chy.boot.commons.platform.auth.entity.AuthParam;
import com.chy.boot.commons.platform.auth.entity.AuthResponse;
import com.chy.boot.commons.platform.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * jwt控制器.
 *
 * @author YuRuizhi
 * @since 2020.1.8
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "接口鉴权管理", description = "接口鉴权管理")
public class JwtController {

    @Autowired
    AuthService authService;


    @Operation(summary = "获取授权token接口", description = "访问业务所有接口都需要在header中带上access_token,可重复使用，过期时间2个小时，每日请求上线2000次，必须缓存起来，禁止每次使用都请求新的")
    @LogStyle(beforeDesc = "获取token:{0}", afterDesc = "获取token成功: {}")
    @ResponseBody
    @PassToken
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public R<AuthResponse> getToken(@RequestBody @Validated AuthParam authParam) {

        return R.data(authService.getToken(authParam.getAccount(), authParam.getPassword()));
    }

}
