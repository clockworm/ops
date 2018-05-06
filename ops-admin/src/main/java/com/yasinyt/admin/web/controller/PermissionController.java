package com.yasinyt.admin.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yasinyt.admin.base.annotation.RequiresPermission;
import com.yasinyt.admin.entity.Permission;
import com.yasinyt.admin.service.PermissionService;
import com.yasinyt.admin.util.BeanUtil;
import com.yasinyt.admin.util.KeyUtil;
import com.yasinyt.admin.util.ResultVOUtil;
import com.yasinyt.admin.web.form.PermissionForm;
import com.yasinyt.admin.web.vo.ResultVO;

/**
 * @detail 资源控制器
 * @author TangLingYun
 */
@Controller
@RequestMapping("permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

	@GetMapping("list")
	public String list() {
		return "sys/perm/list";
	}
	
	@PostMapping("list")
	@RequiresPermission(permisson={"permission:list"})
	public @ResponseBody ResultVO<?> list(PermissionForm form) {
		return ResultVOUtil.success(permissionService.findPermissonTreeList());
	}

	@GetMapping("find/{id}")
	@RequiresPermission(permisson={"permission:findOne"})
	public @ResponseBody ResultVO<?> findOne(@PathVariable(name="id",required=true) String id) {
		Permission permission = permissionService.findById(id);
		return ResultVOUtil.success(permission);
	}
	
	@PostMapping("update")
	@RequiresPermission(permisson={"permission:update"})
	public @ResponseBody ResultVO<?>  update(@RequestParam(name="id",required=true)String id,Permission per){
		Permission permission = permissionService.findById(id);
		BeanUtil.copyPropertiesIgnoreNull(per, permission);
		permissionService.update(permission);
		return ResultVOUtil.success();
	}
	
	@PostMapping("add")
	@RequiresPermission(permisson={"permission:add"})
	public @ResponseBody ResultVO<?>  add(Permission per){
		per.setId(KeyUtil.genUniqueID());
		permissionService.insert(per);
		return ResultVOUtil.success();
	}

}
