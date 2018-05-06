package com.yasinyt.admin.dao;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yasinyt.admin.base.BaseTest;
import com.yasinyt.admin.entity.Role;
import com.yasinyt.admin.entity.User;
import com.yasinyt.admin.service.UserRoleService;
import com.yasinyt.admin.util.KeyUtil;
import com.yasinyt.admin.util.MD5Util;

@Transactional
public class UserDaoTest extends BaseTest {

	@Autowired
	UserDao userDao;
	@Autowired
	UserRoleService userRoleService;
	@Autowired
	RolePermissionDao rolePermissionDao;

	@Test
	public void testDeleteByPrimaryKey() {
		userDao.deleteByPrimaryKey("a053e9e9e7e44ace96d01f8f1e494d4c");
	}

	@Test
	public void testInsert() {
		User user = new User();
		user.setId(KeyUtil.genUniqueID());
		user.setEmplyeeId(KeyUtil.genUniqueID());
		user.setHead("/usr/local/jdk/1.jpg");
		String salt = MD5Util.genUniqueSalt();
		user.setSalt(salt);
		user.setPassword(MD5Util.formPassToDBPass("123456", salt));
		user.setStatus(1);
		user.setInsertTime(new Date());
		user.setUpdateTime(new Date());
		user.setUserName(KeyUtil.genUniqueNumKey());
		userDao.insert(user);
	}

	@Test
	public void testInsertSelective() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectByPrimaryKey() {
	}

	@Test
	public void testUpdateByPrimaryKeySelective() {
	}

	@Test
	public void testUpdateByPrimaryKey() {
	}

	@Test
	public void testFindByUserName() {
		User user = userDao.findByUserName("tanglingyun");
		PageInfo<Role> roles = userRoleService.getRoles(user.getId(),null,new Page<>(1,2));
		List<Role> list = roles.getList();
		list.forEach(e-> System.err.println(e));
		System.err.println(roles.getPages());
	}

}
