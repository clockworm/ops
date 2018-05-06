package com.yasinyt.admin.rpc.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import com.yasinyt.admin.entity.Permission;
import com.yasinyt.admin.entity.Role;
import com.yasinyt.admin.entity.User;
import com.yasinyt.admin.enums.ActiveStatusEnum;
import com.yasinyt.admin.enums.ResultEnum;
import com.yasinyt.admin.rpc.LoginAuthenticationService;
import com.yasinyt.admin.service.RolePermissionService;
import com.yasinyt.admin.service.UserLoginInfoService;
import com.yasinyt.admin.service.UserRoleService;
import com.yasinyt.admin.service.UserService;
import com.yasinyt.admin.util.JsonUtil;
import com.yasinyt.admin.util.KeyUtil;
import com.yasinyt.admin.util.MD5Util;
import com.yasinyt.admin.util.PageUtil;
import com.yasinyt.admin.util.ResultVOUtil;
import com.yasinyt.admin.util.redis.RedisClient;
import com.yasinyt.admin.util.redis.UserSessionKey;
import com.yasinyt.admin.web.form.UserForm;
import com.yasinyt.admin.web.vo.ResultVO;
import com.yasinyt.admin.web.vo.UserSessionVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@MotanService
public class LoginAuthenticationServiceImpl implements LoginAuthenticationService{

	@Autowired
	private UserService userService;
	@Autowired
	private RedisClient redisClient;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private RolePermissionService rolePermissionService;
	@Autowired
	private UserLoginInfoService loginInfoService;

	@Override
	public ResultVO<?> loginAuthentication(UserForm userForm){
		User user = userService.findByUserName(userForm.getUsername());
		if(user == null) return ResultVOUtil.error(ResultEnum.USER_NOT_REGISTER);
		if(!ActiveStatusEnum.ACTIVE.getCode().equals(user.getStatus())) return ResultVOUtil.error(ResultEnum.USER_DISABLE);
		String salt = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(userForm.getPassword(), salt);
		String dbPass = user.getPassword();
		if(!StringUtils.equals(calcPass,dbPass)) return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
		String token = KeyUtil.genUniqueID();
		List<Role> roles = userRoleService.getRoles(user.getId(),ActiveStatusEnum.ACTIVE.getCode(),PageUtil.NO_USE_PAGE).getList();
		UserSessionVO userSession = new UserSessionVO();
		Set<Permission> set = new HashSet<Permission>();
		String[] roleIds = roles.stream().map(Role::getId).collect(Collectors.toSet()).toArray(new String[0]);
		PageInfo<Permission> permissionsByRoles = rolePermissionService.findPermissionsByRoles(roleIds, ActiveStatusEnum.ACTIVE.getCode(), PageUtil.NO_USE_PAGE);
		set.addAll(permissionsByRoles.getList());
		userSession.setUser(user);
		userSession.setRoles(roles);
		userSession.setPermissions(set);
		log.info("RPC远程认证用户:{},拥有的角色列表:{}",user.getUserName(),JsonUtil.toPrintJson(roles));
		log.info("RPC远程认证用户:{},拥有的权限列表:{}",user.getUserName(),JsonUtil.toPrintJson(set));
		redisClient.set(UserSessionKey.generateKeyByToken, token, userSession);
		loginInfoService.insert(user, userForm.getIp(), userForm.getSystemName());
		return ResultVOUtil.success(token);
	}

	@Override
	public ResultVO<?> userSession(String token) {
		UserSessionVO vo = redisClient.get(UserSessionKey.generateKeyByToken, token, UserSessionVO.class);
		if(vo!=null){
			redisClient.set(UserSessionKey.generateKeyByToken, token, vo);
		}
		return  ResultVOUtil.success(vo);
	}

	@Override
	public void logout(User user, String token) {
		redisClient.delete(UserSessionKey.generateKeyByToken, token);
		log.info("PRC远程用户：{} 退出系统",user.getUserName());
	}

}
