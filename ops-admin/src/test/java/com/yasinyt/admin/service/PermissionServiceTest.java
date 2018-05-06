package com.yasinyt.admin.service;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.yasinyt.admin.base.BaseTest;
import com.yasinyt.admin.entity.Permission;
import com.yasinyt.admin.entity.User;
import com.yasinyt.admin.util.JsonUtil;
import com.yasinyt.admin.web.form.PermissionForm;
import com.yasinyt.admin.web.vo.MenuVO;

public class PermissionServiceTest extends BaseTest {

	@Autowired
	private PermissionService permissionService;
	
	@Test
	public void testInsert() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteById() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindById() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAll() {
		PageInfo<Permission> list  = null;
		list = permissionService.findAll(new PermissionForm());
		System.err.println(JsonUtil.toPrintJson(list.getList()));
	}
	
	@Test 
	public void testFindMenuByUser(){
		User user = new User();
		user.setId("1");
		List<MenuVO> list = permissionService.findPermissonTreeListByUser(user);
		System.err.println(JsonUtil.toPrintJson(list));
	}

}
