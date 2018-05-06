package com.yasinyt.admin.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author TangLingYun
 * @detail 资源实体 对应表:Ops_Permission
 */
@Data
@EqualsAndHashCode(of={"id"},doNotUseGetters=true)
public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 主键 */
	private String id;
	/** 名称. */
	private String name;
	/** 资源类型，[menu|button] */
	private String resourceType;
	/** 资源路径. */
	private String url;
	/** 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view */
	private String permission;
	/** 父编号 */
	private String parentId;
	/** 是否启用 */
	private Integer status;
	
	private Date insertTime = new Date();

	private Date updateTime;
	/** 图标*/
	private String icon;
	/** 排序*/
	private Integer sortFlag;
	
	/**用于页面展示 是否被标记*/
	private boolean  mark;
	
}
