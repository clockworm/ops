package com.yasinyt.admin.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author TangLingYun
 * @detail 角色实体 对应表:Ops_role
 */
@Data
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 主键 */
	private String id;
	/** 角色标识 */
	private String name;
	/** 角色描述 */
	private String description;
	/** 是否可用,如果不可用将不会添加给用户 */
	private Integer status;

	private Date insertTime = new Date();

	private Date updateTime;
	
	/**用于页面展示 是否被标记*/
	private boolean mark;
}
