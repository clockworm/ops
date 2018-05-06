package com.yasinyt.admin.service;
	
/**
 * @detail 用户登入登出 业务接口
 * @author TangLingYun
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yasinyt.admin.entity.User;
import com.yasinyt.admin.web.form.UserForm;

public interface LoginService {

	/**登录接口*/
	boolean login(HttpServletResponse response,HttpServletRequest request, UserForm userForm);

	/**注销接口*/
	void logout(User user,HttpServletRequest request, HttpServletResponse response);
}
