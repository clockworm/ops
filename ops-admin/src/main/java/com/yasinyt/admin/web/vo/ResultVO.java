package com.yasinyt.admin.web.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResultVO<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 状态码 0成功 大于0为异常 */
	private Integer code;
	/** 返回的信息 */
	private String message;
	/** 返回的数据 */
	private T data;
	
	private Long count;
	
	private Integer page;
	
	private Integer totalPage;

}