package com.yasinyt.system.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.yasinyt.admin.enums.SystemLoginEmun;
import com.yasinyt.admin.rpc.LoginAuthenticationService;
import com.yasinyt.admin.util.CookieUtil;
import com.yasinyt.admin.util.redis.TokenKey;
import com.yasinyt.admin.web.form.UserForm;
import com.yasinyt.admin.web.vo.ResultVO;
import com.yasinyt.admin.web.vo.UserSessionVO;
import com.yasinyt.system.base.exception.SysException;
import com.yasinyt.system.service.LoginService;
import com.yasinyt.system.util.IPUtil;

@Service
public class LoginServiceImpl implements LoginService{

	@MotanReferer(basicReferer = "motantestClientBasicConfig")
	LoginAuthenticationService loginAuthenticationService;
	
	@Override
	public void login(HttpServletResponse response,HttpServletRequest request,UserForm userForm) {
		userForm.setIp(IPUtil.getClientIP(request));
		userForm.setSystemName(SystemLoginEmun.SYSTEM.getMessage());
		ResultVO<?> vo = loginAuthenticationService.loginAuthentication(userForm);
		if(vo.getData() == null) throw new SysException(vo.getCode(), vo.getMessage());
		CookieUtil.setCookie(response, TokenKey.TOKEN_KEY, vo.getData().toString(), TokenKey.generateKeyByToken.expireSeconds());
	}

	@Override
	public void logout(UserSessionVO vo, HttpServletRequest request, HttpServletResponse response) {
		String token = request.getParameter(TokenKey.TOKEN_KEY);
		loginAuthenticationService.logout(vo.getUser(), token);
		CookieUtil.setCookie(response, TokenKey.TOKEN_KEY,null, 0);
	}

}
