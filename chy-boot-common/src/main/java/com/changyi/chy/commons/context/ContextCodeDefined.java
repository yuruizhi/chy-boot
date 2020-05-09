package com.changyi.chy.commons.context;


import com.changyi.chy.commons.component.comstant.IMessageEnum;

public enum ContextCodeDefined implements IMessageEnum {

	SUCCESS("成功"),
	ERR_UNKOWEN("未知异常"),
	CONTEXT_INIT_ERROR("上下文初始化失败"),
	NOT_FIND_EN("未找到channel信息");

	private String msg;

	ContextCodeDefined(String msg) {
		this.msg = msg;
	}

	@Override
	public String getValue() {
		return this.name();
	}

	@Override
	public String getDesc() {
		return this.msg;
	}
}