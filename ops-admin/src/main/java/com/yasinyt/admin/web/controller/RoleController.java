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
import com.yasinyt.admin.entity.Role;
import com.yasinyt.admin.service.RoleService;
import com.yasinyt.admin.util.BeanUtil;
import com.yasinyt.admin.util.KeyUtil;
import com.yasinyt.admin.util.ResultVOUtil;
import com.yasinyt.admin.web.form.RoleForm;
import com.yasinyt.admin.web.vo.ResultVO;

@Controller
@RequestMapping("role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@GetMapping("list")
	@RequiresPermission(permisson = { "role:list" })
	public String list() {
		return "sys/role/list";
	}

	@PostMapping("list")
	@RequiresPermission(permisson = { "role:list" })
	public @ResponseBody ResultVO<?> list(RoleForm form) {
		return ResultVOUtil.page(roleService.findAll(form));
	}

	@GetMapping("find/{id}")
	@RequiresPermission(permisson = { "role:findOne" })
	public @ResponseBody ResultVO<?> findOne(@PathVariable(name = "id", required = true) String id) {
		Role role = roleService.findById(id);
		return ResultVOUtil.success(role);
	}

	@PostMapping("update")
	@RequiresPermission(permisson = { "role:update" })
	public @ResponseBody ResultVO<?> update(@RequestParam(name = "id", required = true) String id, Role role) {
		Role r = roleService.findById(id);
		BeanUtil.copyPropertiesIgnoreNull(role, r);
		roleService.update(r);
		return ResultVOUtil.success();
	}

	@PostMapping("add")
	@RequiresPermission(permisson = { "role:add" })
	public @ResponseBody ResultVO<?> add(Role role) {
		role.setId(KeyUtil.genUniqueID());
		roleService.insert(role);
		return ResultVOUtil.success();
	}

}
