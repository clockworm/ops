package com.yasinyt.admin.base.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yasinyt.admin.base.annotation.RequiresPermission;
import com.yasinyt.admin.base.constant.CookieConstant;
import com.yasinyt.admin.base.exception.AuthorizeException;
import com.yasinyt.admin.base.exception.OpsException;
import com.yasinyt.admin.entity.Permission;
import com.yasinyt.admin.entity.User;
import com.yasinyt.admin.enums.ActiveStatusEnum;
import com.yasinyt.admin.enums.ResultEnum;
import com.yasinyt.admin.util.ArrayUtil;
import com.yasinyt.admin.util.ClassUtil;
import com.yasinyt.admin.util.CookieUtil;
import com.yasinyt.admin.util.redis.RedisClient;
import com.yasinyt.admin.util.redis.UserSessionKey;
import com.yasinyt.admin.web.vo.UserSessionVO;

/**
 * @detail 权限验证AOP
 * @author TangLingYun
 */
@Aspect
@Component
public class PermissionAspect {

	@Autowired
	private RedisClient redisClient;


	@Pointcut("@annotation(com.yasinyt.admin.base.annotation.RequiresPermission)")
	public void verify() {
	}

	@Before("verify()")
	public void doVerify(JoinPoint point) {
		Method method = ClassUtil.getMethod(point);
		RequiresPermission requiresPermission = method.getAnnotation(RequiresPermission.class);
		if(requiresPermission==null) return;
		String[] permisson = requiresPermission.permisson();
		UserSessionVO userSession = getUserSession();
		if(userSession == null) throw new AuthorizeException();
		User user = userSession.getUser();
		if(requiresPermission.isSpecific() && Arrays.asList(requiresPermission.specificUsers()).contains(user.getUserName())) return;
		Set<Permission> permissions = userSession.getPermissions();
		String[] userpermissions = permissions.stream().filter(e -> ActiveStatusEnum.ACTIVE.getCode().equals(e.getStatus())).map(Permission::getPermission).collect(Collectors.toSet()).toArray(new String[0]);
		String[] intersect = ArrayUtil.intersect(userpermissions, permisson);
		if(intersect.length != permisson.length) throw new OpsException(ResultEnum.NOT_AUTHORIZATION);
	}

	/**获取当前用户的权限session*/
	private UserSessionVO getUserSession(){
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN_KEY);
		if(cookie==null) throw new AuthorizeException();
		UserSessionVO userSessionVO = redisClient.get(UserSessionKey.generateKeyByToken,cookie.getValue(), UserSessionVO.class);
		return userSessionVO;
	}

}
