package com.yasinyt.admin.enums;

import lombok.Getter;

@Getter
public enum SystemLoginEmun implements CodeEnum {
	
	AMDIN(0,"admin权限系统"),
	SYSTEM(1,"sysetem业务系统"),
	;

	/** 返回编码*/
	private Integer code;
	/** 返回信息*/
	private String message;

	SystemLoginEmun(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
