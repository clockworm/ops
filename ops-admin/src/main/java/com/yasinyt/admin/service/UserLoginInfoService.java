package com.yasinyt.admin.service;

import com.yasinyt.admin.entity.User;

/**
 * @detail 用户登录信息业务接口
 * @author TangLingYun
 */
public interface UserLoginInfoService {
	int insert(User user,String ip,String systemName);
}
