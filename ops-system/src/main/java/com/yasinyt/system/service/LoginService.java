package com.yasinyt.system.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yasinyt.admin.web.form.UserForm;
import com.yasinyt.admin.web.vo.UserSessionVO;

public interface LoginService {

	/**登录认证接口*/
	void login(HttpServletResponse response,HttpServletRequest request,UserForm userForm);
	
	/**退出全局系统*/
	void logout(UserSessionVO vo,HttpServletRequest request,HttpServletResponse response);
}
