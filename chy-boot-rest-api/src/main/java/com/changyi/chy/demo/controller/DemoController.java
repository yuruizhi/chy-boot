package com.changyi.chy.demo.controller;


import com.changyi.chy.commons.annotation.ApiVersion;
import com.changyi.chy.commons.api.AbstractBaseController;
import com.changyi.chy.commons.api.R;
import com.changyi.chy.commons.api.ResultCode;
import com.changyi.chy.commons.component.log.LogStyle;
import com.changyi.chy.commons.component.validate.Valid;
import com.changyi.chy.commons.exception.CheckParamException;
import com.changyi.chy.commons.util.StringUtil;
import com.changyi.chy.demo.request.ReqDemo;
import com.changyi.chy.demo.response.RespDemo;
import com.changyi.chy.demo.service.IDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * demo
 *
 * @author Henry.Yu
 * @since 2020.5.8
 */
@RestController
@Api(value = "Demo接口", description = "demo", tags = "demo")
public class DemoController extends AbstractBaseController {

    @Autowired
    public IDemoService demoService;

    @ApiOperation("Demo")
    @ApiVersion(3)
    @RequestMapping(value = "{version}/demo", method = RequestMethod.POST)
    @LogStyle(beforeDesc = "Demo begin:{0}", afterDesc = "Demo end:{}")
    @ResponseBody
    public R<RespDemo> get(@RequestBody ReqDemo params) {
        List<Valid> checkList = checkParams(params);
        if (StringUtil.checkList(checkList)) {
            throw new CheckParamException(ResultCode.PARAM_VALID_ERROR.getMessage(), checkList);
        }
        RespDemo result = demoService.get(params);
        return R.data(result);
    }

    private List<Valid> checkParams(ReqDemo params) {
        List<Valid> checkList = new ArrayList<>();
        if (StringUtil.isBlank(params.getMobile()) &&
                StringUtil.isBlank(params.getOpenId()) &&
                StringUtil.isBlank(params.getUnionId())) {
            Valid valid = new Valid();
            valid.setField("check");
            valid.setMessage("手机号、openId、unionId不能同时为空");
            checkList.add(valid);
        }
        return checkList;
    }
}

