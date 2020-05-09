package com.changyi.chy.commons.api;

import com.changyi.chy.commons.context.ExecuteContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 接口基类
 *
 *@author wuwh
 *@date 2020/1/22
 */
@RequestMapping(AbstractBaseController.REST_URL_PREFIX)
public abstract class AbstractBaseController {

    protected static final String REST_URL_PREFIX = "/"+ ExecuteContext.MEMBER_BASE_API_PREFIX + "/{brand}/";
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
