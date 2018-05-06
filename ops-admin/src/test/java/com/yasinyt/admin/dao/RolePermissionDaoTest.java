package com.yasinyt.admin.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yasinyt.admin.base.BaseTest;

/**
 * @detail 用户与角色绑定单元测试用例
 * @author TangLingYun
 */
@Transactional
public class RolePermissionDaoTest extends BaseTest {

	@Autowired
	private RolePermissionDao rolePermissionDao;
	
	@Test
	public void deletePermissionByRoleId(){
		String roleId = "3";
		rolePermissionDao.deletePermissionByRoleId(roleId);
	}
	
	@Test
	public void roleAddPermissionBinds(){
		String roleId = "1";
		String[] permissionIds = {"1","2","3"};
		rolePermissionDao.roleAddPermissionBinds(roleId, permissionIds);
	}

}
