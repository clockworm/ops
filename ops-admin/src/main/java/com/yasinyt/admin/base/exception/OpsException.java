package com.yasinyt.admin.base.exception;


import com.yasinyt.admin.enums.ResultEnum;

import lombok.Getter;

/**
 * @detail 全局业务异常处理
 * @author TangLingYun
 */
@Getter
public class OpsException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	private Integer code;

    public OpsException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public OpsException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
