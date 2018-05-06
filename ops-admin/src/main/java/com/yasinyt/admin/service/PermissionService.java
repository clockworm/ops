package com.yasinyt.admin.service;

import java.util.List;

import com.yasinyt.admin.base.service.BaseService;
import com.yasinyt.admin.entity.Permission;
import com.yasinyt.admin.entity.User;
import com.yasinyt.admin.web.vo.MenuVO;

/**
 * @detail 资源业务层接口
 * @author TangLingYun
 */
public interface PermissionService extends BaseService<Permission>{
	 /**获取用户的菜单列表 只做二级菜单处理*/
	 List<MenuVO> findPermissonTreeListByUser(User user);
	 
	 /**查询所有资源*/
	 List<MenuVO> findPermissonTreeList();
}
