package com.chy.boot.common.component.log.converters;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.chy.boot.common.context.ExecuteContext;

/**
 * 请求id输出
 */
public class RequestIdConverter extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
        String requestId = ExecuteContext.getRequestId(event.getThreadName());
        return requestId == null ? "not_request" : requestId;
    }
}
