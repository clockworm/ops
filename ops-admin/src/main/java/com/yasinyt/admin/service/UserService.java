package com.yasinyt.admin.service;

import com.yasinyt.admin.base.service.BaseService;
import com.yasinyt.admin.entity.User;

/**
 * @detail 用户校验业务接口
 * @author TangLingYun
 */
public interface UserService extends BaseService<User> {

	/**登录校驗接口*/
	User login(String userName, String password);
	
	/**通过用户名查询用户信息*/
	User findByUserName(String userName);
}
