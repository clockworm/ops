package com.yasinyt.admin.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yasinyt.admin.base.annotation.AccessLimit;
import com.yasinyt.admin.service.LoginService;
import com.yasinyt.admin.util.ResultVOUtil;
import com.yasinyt.admin.util.VerifyCodeUtils;
import com.yasinyt.admin.web.form.UserForm;
import com.yasinyt.admin.web.vo.ResultVO;
import com.yasinyt.admin.web.vo.UserSessionVO;

@Controller
@RequestMapping("common")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@GetMapping("login")
	public String index(Model m) {
		return "login";
	}

	@PostMapping("login")
	@AccessLimit
	public @ResponseBody ResultVO<?> login(HttpServletResponse response, HttpServletRequest request,@Valid UserForm userForm) {
		boolean login = loginService.login(response, request, userForm);
		return ResultVOUtil.success(login);
	}

	@GetMapping("logout")
	public void logout(UserSessionVO userSession, HttpServletRequest request, HttpServletResponse response) {
		loginService.logout(userSession.getUser(), request, response);
	}

	@GetMapping("index")
	public String index() {
		return "index";
	}

	@GetMapping("getYzm")
	public void getYzm(HttpServletResponse response, HttpServletRequest request) throws IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		// 存入会话session
		HttpSession session = request.getSession(true);
		// 生成验证码流
		session.setAttribute("verifyCode", verifyCode.toLowerCase());
		int w = 156, h = 41;
		VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
	}

	// FIXME 临时菜单获取
	@PostMapping("menudatas")
	public String menudatas() {
		return "data/menudatas";
	}
}
