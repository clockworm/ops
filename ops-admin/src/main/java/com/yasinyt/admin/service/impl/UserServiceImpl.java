package com.yasinyt.admin.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yasinyt.admin.base.annotation.RedisCacheable;
import com.yasinyt.admin.dao.UserDao;
import com.yasinyt.admin.entity.User;
import com.yasinyt.admin.service.UserService;
import com.yasinyt.admin.util.KeyUtil;
import com.yasinyt.admin.util.MD5Util;
import com.yasinyt.admin.web.form.Page;
import com.yasinyt.admin.web.form.UserForm;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public User login(String userName, String password) {
		return userDao.findByUserNameAndPassword(userName, password);
	}

	@Override
	@RedisCacheable(cacheName="findByUserName", key="#userName",expiryTimeSecond=10)
	public User findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

	@Override
	public User findById(Serializable id) {
		return userDao.selectByPrimaryKey((String)id);
	}

	@Override
	public PageInfo<User> findAll(Page page) {
		UserForm form = (UserForm) page;
		PageHelper.startPage(form.getPage(),form.getLimit());
		List<User> list = userDao.findAll(form);
		return new PageInfo<User>(list);
	}

	@Override
	public int insert(User user) {
		user.setId(KeyUtil.genUniqueID());
		String salt = KeyUtil.genUniqueNumKey();
		user.setSalt(salt);
		String dbPass = MD5Util.formPassToDBPass(user.getPassword(), salt);
		user.setPassword(dbPass);
		return userDao.insert(user);
	}

	@Override
	public int deleteById(String id) {
		return userDao.deleteByPrimaryKey(id);
	}

	@Override
	public int update(User user) {
		user.setInsertTime(null);
		user.setUpdateTime(new Date());
		return userDao.updateByPrimaryKeySelective(user);
	}


}
