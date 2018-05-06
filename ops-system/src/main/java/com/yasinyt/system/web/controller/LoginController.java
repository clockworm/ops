package com.yasinyt.system.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yasinyt.admin.base.annotation.AccessLimit;
import com.yasinyt.admin.base.annotation.CheckRepetition;
import com.yasinyt.admin.base.annotation.RequiresPermission;
import com.yasinyt.admin.enums.BusinessTypeEnum;
import com.yasinyt.admin.web.form.UserForm;
import com.yasinyt.admin.web.vo.ResultVO;
import com.yasinyt.admin.web.vo.UserSessionVO;
import com.yasinyt.system.service.LoginService;
import com.yasinyt.system.util.ResultVOUtil;

@Controller
@RequestMapping("common")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@GetMapping("login")
	public String index(Model m) {
		return "/login";
	}
	
	@PostMapping("login")
	@CheckRepetition(tokenAgr="#userForm.username",businessType=BusinessTypeEnum.LOGIN)
	public @ResponseBody ResultVO<?> login(HttpServletResponse response,HttpServletRequest request,@Valid UserForm userForm){
		loginService.login(response,request,userForm);
		return ResultVOUtil.success();
	}

	@PostMapping("main")
	@AccessLimit
	@RequiresPermission(permisson={"user:del"})
	public @ResponseBody ResultVO<?> main(){
		return ResultVOUtil.success();
	}
	
	@GetMapping("logout")
	public void logout(UserSessionVO vo,HttpServletRequest request,HttpServletResponse response) {
		loginService.logout(vo, request, response);
	}

}
