package com.yasinyt.admin.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.yasinyt.admin.base.annotation.RequiresPermission;
import com.yasinyt.admin.entity.User;
import com.yasinyt.admin.service.UserService;
import com.yasinyt.admin.util.BeanUtil;
import com.yasinyt.admin.util.KeyUtil;
import com.yasinyt.admin.util.ResultVOUtil;
import com.yasinyt.admin.web.form.UserForm;
import com.yasinyt.admin.web.vo.ResultVO;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("list")
	@RequiresPermission(permisson={"user:list"})
	public String index() {
		return "sys/user/list";
	}
	
	@PostMapping("list")
	@RequiresPermission(permisson={"user:list"})
	public @ResponseBody ResultVO<?> list(UserForm form) {
		PageInfo<User> list = userService.findAll(form);
		return ResultVOUtil.page(list);
	}

	@GetMapping("find/{id}")
	@RequiresPermission(permisson={"user:findOne"})
	public @ResponseBody ResultVO<?> findOne(@PathVariable(name="id",required=true) String id) {
		User user = userService.findById(id);
		return ResultVOUtil.success(user);
	}
	
	@PostMapping("update")
	@RequiresPermission(permisson={"user:update"})
	public @ResponseBody ResultVO<?>  update(@RequestParam(name="id",required=true)String id,User user){
		User u = userService.findById(id);
		BeanUtil.copyPropertiesIgnoreNull(user, u);
		userService.update(u);
		return ResultVOUtil.success();
	}
	
	@PostMapping("add")
	@RequiresPermission(permisson={"user:add"})
	public @ResponseBody ResultVO<?>  add(@Valid UserForm form){
		User user = new User();
		form.setId(KeyUtil.genUniqueID());
		BeanUtil.copyPropertiesIgnoreNull(form, user);
		userService.insert(user);
		return ResultVOUtil.success();
	}

}
