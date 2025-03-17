package com.chy.boot.common.commons.support.xss;

import com.chy.boot.common.commons.util.StringPool;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;


import java.io.IOException;

/**
 * XSS过滤
 *
 */
@AllArgsConstructor
public class XssFilter implements Filter {

	private XssProperties xssProperties;
	private XssUrlProperties xssUrlProperties;

	@Override
	public void init(FilterConfig config) {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String path = ((HttpServletRequest) request).getServletPath();
		if (isSkip(path)) {
			chain.doFilter(request, response);
		} else {
			XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
			chain.doFilter(xssRequest, response);
		}
	}

	private boolean isSkip(String path) {
		return (xssUrlProperties.getExcludePatterns().stream().anyMatch(path::startsWith))
			|| (xssProperties.getSkipUrl().stream().map(url -> url.replace("/**", StringPool.EMPTY)).anyMatch(path::startsWith));
	}

	@Override
	public void destroy() {

	}

}
