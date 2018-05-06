package com.yasinyt.admin.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.yasinyt.admin.base.exception.AuthorizeException;
import com.yasinyt.admin.base.exception.OpsException;
import com.yasinyt.admin.entity.Permission;
import com.yasinyt.admin.entity.Role;
import com.yasinyt.admin.entity.User;
import com.yasinyt.admin.enums.ActiveStatusEnum;
import com.yasinyt.admin.enums.ResultEnum;
import com.yasinyt.admin.enums.SystemLoginEmun;
import com.yasinyt.admin.service.LoginService;
import com.yasinyt.admin.service.PermissionService;
import com.yasinyt.admin.service.RolePermissionService;
import com.yasinyt.admin.service.UserLoginInfoService;
import com.yasinyt.admin.service.UserRoleService;
import com.yasinyt.admin.service.UserService;
import com.yasinyt.admin.util.CookieUtil;
import com.yasinyt.admin.util.IPUtil;
import com.yasinyt.admin.util.JsonUtil;
import com.yasinyt.admin.util.KeyUtil;
import com.yasinyt.admin.util.MD5Util;
import com.yasinyt.admin.util.PageUtil;
import com.yasinyt.admin.util.redis.RedisClient;
import com.yasinyt.admin.util.redis.TokenKey;
import com.yasinyt.admin.util.redis.UserSessionKey;
import com.yasinyt.admin.web.form.UserForm;
import com.yasinyt.admin.web.vo.MenuVO;
import com.yasinyt.admin.web.vo.UserSessionVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RedisClient redisClient;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private RolePermissionService rolePermissionService;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private UserLoginInfoService loginInfoService;
	
	@Override
	public boolean login(HttpServletResponse response,HttpServletRequest request, UserForm userForm) {
		User user = userService.findByUserName(userForm.getUsername());
		if(user == null) throw new OpsException(ResultEnum.USER_NOT_REGISTER);
		if(!ActiveStatusEnum.ACTIVE.getCode().equals(user.getStatus()))throw new OpsException(ResultEnum.USER_DISABLE);
		String salt = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(userForm.getPassword(), salt);
		String dbPass = user.getPassword();
		if(!StringUtils.equals(calcPass,dbPass)) throw new OpsException(ResultEnum.LOGIN_FAIL);
		String token = KeyUtil.genUniqueID();
		List<Role> roles = userRoleService.getRoles(user.getId(),ActiveStatusEnum.ACTIVE.getCode(),PageUtil.NO_USE_PAGE).getList();
		UserSessionVO userSession = new UserSessionVO();
		Set<Permission> set = new HashSet<Permission>();
		String[] roleIds = roles.stream().map(Role::getId).collect(Collectors.toSet()).toArray(new String[0]);
		PageInfo<Permission> permissionsByRoles = rolePermissionService.findPermissionsByRoles(roleIds, ActiveStatusEnum.ACTIVE.getCode(), PageUtil.NO_USE_PAGE);
		set.addAll(permissionsByRoles.getList());
		List<MenuVO> list = permissionService.findPermissonTreeListByUser(user);
		userSession.setMenuVos(list);
		userSession.setUser(user);
		userSession.setRoles(roles);
		userSession.setPermissions(set);
		log.info("用户:{},拥有的角色列表:{}",user.getUserName(),JsonUtil.toPrintJson(roles));
		log.info("用户:{},拥有的权限列表:{}",user.getUserName(),JsonUtil.toPrintJson(set));
		CookieUtil.setCookie(response, TokenKey.TOKEN_KEY, token, UserSessionKey.generateKeyByToken.expireSeconds());
		redisClient.set(UserSessionKey.generateKeyByToken, token, userSession);
		loginInfoService.insert(user, IPUtil.getIpAddr(request), SystemLoginEmun.AMDIN.getMessage());
		return true;
	}

	@Override
	public void logout(User user,HttpServletRequest request, HttpServletResponse response) {
		String token = CookieUtil.getCookie(request, TokenKey.TOKEN_KEY).getValue();
		CookieUtil.setCookie(response, TokenKey.TOKEN_KEY,null, 0);
		redisClient.delete(UserSessionKey.generateKeyByToken, token);
		log.info("用户:{}退出系统成功",user.getUserName());
		throw new AuthorizeException();
	}

}
