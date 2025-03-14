package com.changyi.chy.commons.component.log;

import ch.qos.logback.classic.PatternLayout;
import com.changyi.chy.commons.component.log.converters.LocalIpConverter;
import com.changyi.chy.commons.component.log.converters.RequestIdConverter;


/**
 *
 */
public class MyPatternLayout extends PatternLayout {
    static {
        defaultConverterMap.put("ip", LocalIpConverter.class.getName());
        defaultConverterMap.put("request_id", RequestIdConverter.class.getName());
    }
}
