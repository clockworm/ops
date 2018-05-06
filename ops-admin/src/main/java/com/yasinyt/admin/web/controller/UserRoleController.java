package com.yasinyt.admin.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yasinyt.admin.base.annotation.RequiresPermission;
import com.yasinyt.admin.entity.Role;
import com.yasinyt.admin.service.RoleService;
import com.yasinyt.admin.service.UserRoleService;
import com.yasinyt.admin.util.PageUtil;
import com.yasinyt.admin.util.ResultVOUtil;
import com.yasinyt.admin.web.form.RoleForm;
import com.yasinyt.admin.web.vo.ResultVO;

/**
 * @detail 用户与角色关联控制器
 * @author TangLingYun
 */
@Controller
@RequestMapping("user_role")
public class UserRoleController {

	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private RoleService roleService;

	@GetMapping("find/{userId}")
	@RequiresPermission(permisson={"user_role:findOne"})
	public @ResponseBody ResultVO<?> findRoleByUserId(@PathVariable(name="userId",required=true) String userId,Integer status){
		List<Role> userRoles = userRoleService.getRoles(userId, status, PageUtil.NO_USE_PAGE).getList();
		List<Role> roles = roleService.findAll(new RoleForm()).getList();
		HashMap<String,Role> map = new HashMap<String,Role>();
		roles.forEach(e-> map.put(e.getId(), e));
		userRoles.forEach(e-> map.get(e.getId()).setMark(true));
		roles = new ArrayList<Role>(map.values());
		return ResultVOUtil.success(roles);
	}


	@PostMapping("update/{userId}")
	@RequiresPermission(permisson={"user_role:update"})
	public @ResponseBody ResultVO<?> updateRoleByRoleId(@PathVariable(name="userId",required=true) String userId,String[] roleId){
		int i = userRoleService.userAddRoleBinds(userId, roleId);
		return ResultVOUtil.success(i);
	}


}
