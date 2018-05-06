package com.yasinyt.system.enums;

import com.yasinyt.admin.enums.CodeEnum;

import lombok.Getter;

@Getter
public enum ResultEnum implements CodeEnum {

	SUCCESS(0, "成功"), 
	ACCESS_LIMIT(445,"请求过于频繁"),
	NOT_AUTHORIZATION(0101,"权限不足"),
	SERVER_ERROR(411,"服务端异常,请稍后重试"),
	NOT_FOUND_FILE(5404,"文件不存在");
	
	/** 返回编码*/
	private Integer code;
	/** 返回信息*/
	private String message;

	ResultEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
