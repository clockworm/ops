package com.yasinyt.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yasinyt.admin.dao.UserRoleDao;
import com.yasinyt.admin.entity.Role;
import com.yasinyt.admin.entity.User;
import com.yasinyt.admin.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	UserRoleDao userRoleDao;
	
	@Override
	public PageInfo<User> getUsers(String roleId,Page<?> page) {
		PageHelper.startPage(page.getPageNum(),page.getPageSize());
		List<User> list = userRoleDao.getUsers(roleId);
		return new PageInfo<User>(list);
	}

	@Override
	public PageInfo<Role> getRoles(String userId,Integer status,Page<?> page) {
		PageHelper.startPage(page.getPageNum(),page.getPageSize());
		List<Role> list = userRoleDao.getRoles(userId,status);
		return new PageInfo<Role>(list);
	}

	@Override
	@Transactional
	public int userAddRoleBinds(String userId, String[] roleIds) {
		int i = userRoleDao.deleteRolesByUserId(userId);
		if(roleIds.length > 0){
			i = userRoleDao.userAddRolesBinds(userId,roleIds);
		}
		return i;
	}

}
