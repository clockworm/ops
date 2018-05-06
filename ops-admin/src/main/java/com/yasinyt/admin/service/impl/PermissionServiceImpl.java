package com.yasinyt.admin.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yasinyt.admin.dao.PermissionDao;
import com.yasinyt.admin.entity.Permission;
import com.yasinyt.admin.entity.User;
import com.yasinyt.admin.service.PermissionService;
import com.yasinyt.admin.web.form.Page;
import com.yasinyt.admin.web.form.PermissionForm;
import com.yasinyt.admin.web.vo.MenuVO;

@Service
public class PermissionServiceImpl implements PermissionService{
	
	@Autowired
	PermissionDao  permissionDao;

	@Override
	public int insert(Permission permission) {
		return permissionDao.insert(permission);
	}

	@Override
	public int deleteById(String id) {
		return permissionDao.deleteByPrimaryKey(id);
	}

	@Override
	public int update(Permission permission) {
		return permissionDao.updateByPrimaryKeySelective(permission);
	}

	@Override
	public Permission findById(Serializable id) {
		return permissionDao.selectByPrimaryKey((String)id);
	}

	@Override
	public PageInfo<Permission> findAll(Page page) {
		PermissionForm form = (PermissionForm)page;
		PageHelper.startPage(form.getPage(),form.getLimit());
		List<Permission> list = permissionDao.findAll(form);
		return new PageInfo<Permission>(list);
	}

	@Override
	public  List<MenuVO> findPermissonTreeListByUser(User user) {
		List<Permission> list = permissionDao.findMenuByUser(user.getId());
		return genTree(list);
	}
	
	private List<MenuVO> genTree(List<Permission> list) {
		List<Permission> systemMenuList = list.stream().filter(e->e.getResourceType().equals("system")).collect(Collectors.toList());
		List<Permission> mainMenuList = list.stream().filter(e->e.getResourceType().equals("menu")).collect(Collectors.toList());
		List<Permission> subMenuList = list.stream().filter(e->e.getResourceType().equals("list")).collect(Collectors.toList());
		MenuVO mianMenu = null;
		List<MenuVO> menuList = new ArrayList<MenuVO>();
		List<MenuVO> systemList = new ArrayList<MenuVO>();
		for (Permission mian : mainMenuList) {
			mianMenu = new MenuVO(mian,subMenuList,false);
			menuList.add(mianMenu);
			for (Permission permission : systemMenuList) {
				if(!StringUtils.equals(mian.getParentId(), permission.getId())) continue;
				MenuVO vo = new MenuVO(permission,Collections.emptyList(),false);
				vo.setChildren(menuList);
				systemList.add(vo);
			}
		}
		return systemList;
	}

	@Override
	public List<MenuVO> findPermissonTreeList() {
		List<Permission> list = permissionDao.findResources();
		return genTree(list);
	}

}
