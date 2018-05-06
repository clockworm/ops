package com.yasinyt.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yasinyt.admin.dao.UserLoginInfoDao;
import com.yasinyt.admin.entity.User;
import com.yasinyt.admin.entity.UserLoginInfo;
import com.yasinyt.admin.service.UserLoginInfoService;
import com.yasinyt.admin.util.KeyUtil;

@Service
public class UserLoginInfoServiceImpl implements UserLoginInfoService {

	@Autowired
	UserLoginInfoDao loginInfoDao;

	@Override
	public int insert(User user,String ip,String sysName) {
		UserLoginInfo loginInfo = new UserLoginInfo();
		loginInfo.setUserId(user.getId());
		loginInfo.setUserName(user.getUserName());
		loginInfo.setId(KeyUtil.genUniqueID());
		loginInfo.setSystemName(sysName);
		loginInfo.setLoginIp(ip);
		return loginInfoDao.insertSelective(loginInfo);
	}

}
