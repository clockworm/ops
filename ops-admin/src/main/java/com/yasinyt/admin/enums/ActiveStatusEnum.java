package com.yasinyt.admin.enums;

import lombok.Getter;

@Getter
public enum ActiveStatusEnum implements CodeEnum {
	
	DISABLE(0,"禁用"),
	ACTIVE(1,"启用"),
	;

	/** 返回编码*/
	private Integer code;
	/** 返回信息*/
	private String message;

	ActiveStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

}
