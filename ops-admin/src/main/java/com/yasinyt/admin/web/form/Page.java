package com.yasinyt.admin.web.form;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public abstract class Page implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 页码，从1开始 */
	int page;
	/** 页面大小 */
	int limit;
}
