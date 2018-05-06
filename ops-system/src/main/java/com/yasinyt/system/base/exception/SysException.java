package com.yasinyt.system.base.exception;


import com.yasinyt.system.enums.ResultEnum;

import lombok.Getter;

/**
 * @detail 全局业务异常处理
 * @author TangLingYun
 */
@Getter
public class SysException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	private Integer code;

    public SysException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SysException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
