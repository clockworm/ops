package com.yasinyt.admin.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yasinyt.admin.dao.RoleDao;
import com.yasinyt.admin.entity.Role;
import com.yasinyt.admin.service.RoleService;
import com.yasinyt.admin.web.form.Page;
import com.yasinyt.admin.web.form.RoleForm;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleDao roleDao;

	@Override
	public int insert(Role role) {
		return roleDao.insert(role);
	}

	@Override
	public int deleteById(String id) {
		return roleDao.deleteByPrimaryKey(id);
	}

	@Override
	public int update(Role role) {
		return roleDao.updateByPrimaryKeySelective(role);
	}

	@Override
	public Role findById(Serializable id) {
		return roleDao.selectByPrimaryKey((String)id);
	}

	@Override
	public PageInfo<Role> findAll(Page page) {
		RoleForm form = (RoleForm)page;
		PageHelper.startPage(form.getPage(),form.getLimit());
		List<Role> list = roleDao.findAll(form);
		return new PageInfo<Role>(list);
	}

}
