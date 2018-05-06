package com.yasinyt.admin.service;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yasinyt.admin.base.BaseTest;
import com.yasinyt.admin.entity.User;

public class UserServiceTest extends BaseTest{
	
	@Autowired
	private UserService userService;

	@Test
	public void testInsert() {
		User user = new User();
		user.setUserName("tanglingyun");
		user.setPassword("123456");
		userService.insert(user);
	}

	@Test
	public void testDeleteById() {
		userService.deleteById("123");
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
		fail("Not yet implemented");
	}

}
