package com.yasinyt.admin.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author TangLingYun
 * @detail 用户与角色关系实体 对应表:Ops_User_Role
 */
@Data
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<User> users;
	private List<Role> roles;
}
