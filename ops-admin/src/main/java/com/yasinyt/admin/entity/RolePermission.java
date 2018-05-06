package com.yasinyt.admin.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author TangLingYun
 * @detail 角色与资源关系实体 对应表:Ops_Role_Permission
 */
@Data
public class RolePermission implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<Role> roles;
	private List<Permission> permissions;
}
