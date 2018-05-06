package com.yasinyt.admin.web.form;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper=true)
public class PermissionForm extends Page{
	private static final long serialVersionUID = 1L;
	/** 名称. */
	private String name;
	/** 资源类型 */
	private String resourceType;
	/** 是否启用 */
	private Integer status;
}
