package com.changyi.chy.commons.component.log.converters;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class TypeConverter extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
        String className = event.getLoggerName();
        String type = "unknown";
        if (className.indexOf("com.changyi") != -1) {
            type = "service";
        }
        return type;
    }
}
