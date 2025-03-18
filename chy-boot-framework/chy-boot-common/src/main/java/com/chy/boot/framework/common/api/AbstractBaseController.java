package com.chy.boot.framework.common.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 接口基类
 *
 * @author YuRuizhi
 * @date 2020.1.22
 */
@RequestMapping(AbstractBaseController.REST_URL_PREFIX)
public abstract class AbstractBaseController {

    protected static final String REST_URL_PREFIX = "/";
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
