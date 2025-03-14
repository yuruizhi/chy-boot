package com.changyi.chy.commons.context;


import com.changyi.chy.commons.api.IResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContextCodeDefined implements IResultCode {

	/**
	 * 成功
	 */
	SUCCESS(200, "成功"),
	/**
	 * 未知异常
	 */
	ERR_UNKOWEN(500,"未知异常"),
	/**
	 * 上下文初始化错误
	 */
	CONTEXT_INIT_ERROR(500,"上下文初始化失败"),
	/**
	 * 未找到channel信息
	 */
	NOT_FIND_EN(500,"未找到channel信息");

	private final int code;
	private final String message;
}