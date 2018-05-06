package com.yasinyt.admin.web.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.yasinyt.admin.entity.Emplyee;
import com.yasinyt.admin.entity.Permission;
import com.yasinyt.admin.entity.Role;
import com.yasinyt.admin.entity.User;

import lombok.Data;
import lombok.ToString;

/**
 * @author TangLingYun
 * @describe 分布式Session对象--用于存储Redis
 */
@Data
@ToString
public class UserSessionVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private User user;
	private Emplyee emlpyee;
	private List<Role> roles;
	private Set<Permission> permissions;
	private List<MenuVO> menuVos;
}
