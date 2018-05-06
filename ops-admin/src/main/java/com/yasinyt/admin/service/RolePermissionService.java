package com.yasinyt.admin.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yasinyt.admin.entity.Permission;
import com.yasinyt.admin.entity.Role;

/**
 * @detail 角色与资源 (绑定逻辑层)
 * @author TangLingYun
 */
public interface RolePermissionService {
	
	/**通过资源获取绑定的角色*/
	PageInfo<Role> getRoles(String permissionId,Page<?> page);
	
	/**通过角色获取绑定的资源 请使用 {@link #findPermissionsByRoles(String[], Integer, Page)}方法*/
	@Deprecated
	PageInfo<Permission> getPermissions(String roleId,Integer status,Page<?> page);
	
	/**通过角色获取绑定的资源 批量查询*/
	PageInfo<Permission> findPermissionsByRoles(String[] roleIds,Integer status,Page<?> page);

	/**角色与资源绑定接口*/
	int addPermissionByRoleId(String roleId, String[] permissionIds);

}
