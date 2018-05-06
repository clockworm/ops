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
import com.yasinyt.admin.entity.Permission;
import com.yasinyt.admin.service.PermissionService;
import com.yasinyt.admin.service.RolePermissionService;
import com.yasinyt.admin.util.PageUtil;
import com.yasinyt.admin.util.ResultVOUtil;
import com.yasinyt.admin.web.form.PermissionForm;
import com.yasinyt.admin.web.vo.ResultVO;

/**
 * @detail 角色与资源的关联控制器
 * @author TangLingYun
 */
@Controller
@RequestMapping("role_permission")
public class RolePermissionController {
	@Autowired
	private RolePermissionService rolePermissionService;
	@Autowired
	private PermissionService permissionService;

	@GetMapping("find/{roleId}")
	@RequiresPermission(permisson={"role_permission:findOne"})
	public @ResponseBody ResultVO<?> findRoleByUserId(@PathVariable(name="roleId",required=true) String roleId,Integer status){
		List<Permission> rolePermissions = rolePermissionService.findPermissionsByRoles(new String[]{roleId}, status, PageUtil.NO_USE_PAGE).getList();
		List<Permission> list = permissionService.findAll(new PermissionForm()).getList();
		HashMap<String,Permission> map = new HashMap<String,Permission>();
		list.forEach(e-> map.put(e.getId(), e));
		rolePermissions.forEach(e-> map.get(e.getId()).setMark(true));
		list = new ArrayList<Permission>(map.values());
		return ResultVOUtil.success(list);
	}


	@PostMapping("update/{roleId}")
	@RequiresPermission(permisson={"role_permission:update"})
	public @ResponseBody ResultVO<?> updateRoleByRoleId(@PathVariable(name="roleId",required=true) String roleId,String[] permissionId){
		int i = rolePermissionService.addPermissionByRoleId(roleId, permissionId);
		return ResultVOUtil.success(i);
	}

}
