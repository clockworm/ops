package com.yasinyt.admin.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yasinyt.admin.entity.Role;
import com.yasinyt.admin.entity.User;

/**
 * @detail 用户与角色 (绑定逻辑层)
 * @author TangLingYun
 */
public interface UserRoleService {

	/**通过角色查询绑定的用户*/
	PageInfo<User> getUsers(String roleId,Page<?> page);

	/**获取用户绑定的角色*/
	PageInfo<Role> getRoles(String userId,Integer status,Page<?> page);
	
	/**用户绑定角色接口*/
	int userAddRoleBinds(String userId,String[] roleId);
	
}
