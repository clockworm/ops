package com.yasinyt.admin.web.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class RoleForm extends Page {
	private static final long serialVersionUID = 1L;
	/** 角色标识 */
	private String name;
	/** 是否可用,如果不可用将不会添加给用户 */
	private Integer status;
}
