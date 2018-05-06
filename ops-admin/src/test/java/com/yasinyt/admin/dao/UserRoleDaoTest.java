package com.yasinyt.admin.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yasinyt.admin.base.BaseTest;

/**
 * @detail 用户与角色绑定单元测试用例
 * @author TangLingYun
 */
public class UserRoleDaoTest extends BaseTest {

	@Autowired
	private UserRoleDao userRoleDao;
	
	@Test
	public void addUserRoleBinds(){
		String userId = "1";
		String[] roleIds = {"1","2","3"};
		userRoleDao.userAddRolesBinds(userId, roleIds);
	}
	
	
	@Test
	public void deleteRolesByUserId(){
		String userId = "1";
		userRoleDao.deleteRolesByUserId(userId);
	}

}
