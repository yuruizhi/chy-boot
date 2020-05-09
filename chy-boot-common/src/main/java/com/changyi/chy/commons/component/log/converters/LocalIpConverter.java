package com.changyi.chy.commons.component.log.converters;


import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 请求ip输出
 *

 */
public class LocalIpConverter extends ClassicConverter {

    private static String localIp = "127.0.0.1";
    static {
        try {
            localIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ignored) {

        }
    }

    @Override
    public String convert(ILoggingEvent event) {
        return localIp;
    }
}
