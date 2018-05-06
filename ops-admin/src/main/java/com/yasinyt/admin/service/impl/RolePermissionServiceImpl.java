package com.yasinyt.admin.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yasinyt.admin.dao.RolePermissionDao;
import com.yasinyt.admin.entity.Permission;
import com.yasinyt.admin.entity.Role;
import com.yasinyt.admin.service.RolePermissionService;


@Service
public class RolePermissionServiceImpl implements RolePermissionService {
	
	@Autowired
	RolePermissionDao rolePermissionDao;
	
	@Override
	public PageInfo<Role> getRoles(String permissionId,Page<?> page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<Role> list = rolePermissionDao.getRoles(permissionId);
		return new PageInfo<Role>(list);
	}

	@Override
	@Deprecated
	public PageInfo<Permission> getPermissions(String roleId, Integer status, Page<?> page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<Permission> list = rolePermissionDao.getPermissions(roleId, status);
		return new PageInfo<Permission>(list);
	}
	
	@Override
	public PageInfo<Permission> findPermissionsByRoles(String[] roleIds,Integer status,Page<?> page) {
		if(roleIds.length==0) return new PageInfo<Permission>(Collections.emptyList());
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<Permission> list = rolePermissionDao.findPermissionsByRoles(roleIds,status);
		return new PageInfo<Permission>(list);
	}

	@Override
	@Transactional
	public int addPermissionByRoleId(String roleId, String[] permissionIds) {
		int i = rolePermissionDao.deletePermissionByRoleId(roleId);
		if(permissionIds.length > 0){
			i = rolePermissionDao.roleAddPermissionBinds(roleId,permissionIds);
		}
		return i;
	}

}
