package com.yasinyt.admin.enums;

import lombok.Getter;

@Getter
public enum BusinessTypeEnum  implements CodeEnum {
	
	LOGIN(0,"登录业务"),
	LOGOUT(1,"注销业务"),
	ORDER(2,"订单业务"),
	COMMON(3,"公共业务"),
	;

	/** 返回编码*/
	private Integer code;
	/** 返回信息*/
	private String message;

	BusinessTypeEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

}
