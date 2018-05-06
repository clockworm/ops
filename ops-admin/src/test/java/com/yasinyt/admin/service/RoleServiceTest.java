package com.yasinyt.admin.service;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.yasinyt.admin.base.BaseTest;
import com.yasinyt.admin.entity.Role;
import com.yasinyt.admin.util.JsonUtil;
import com.yasinyt.admin.web.form.RoleForm;

public class RoleServiceTest extends BaseTest{

	@Autowired
	private RoleService roleService;
	
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
		PageInfo<Role> list  = null;
		list = roleService.findAll(new RoleForm());
		System.err.println(JsonUtil.toPrintJson(list.getList()));
	}

}
