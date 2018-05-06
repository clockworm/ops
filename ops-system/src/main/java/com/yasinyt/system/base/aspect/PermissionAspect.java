package com.yasinyt.system.base.aspect;

import java.lang.reflect.Field;
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
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yasinyt.admin.base.annotation.RequiresPermission;
import com.yasinyt.admin.base.exception.AuthorizeException;
import com.yasinyt.admin.entity.Permission;
import com.yasinyt.admin.entity.User;
import com.yasinyt.admin.enums.ActiveStatusEnum;
import com.yasinyt.admin.util.ArrayUtil;
import com.yasinyt.admin.util.CookieUtil;
import com.yasinyt.admin.util.redis.UserSessionKey;
import com.yasinyt.admin.web.vo.UserSessionVO;
import com.yasinyt.system.base.exception.SysException;
import com.yasinyt.system.enums.ResultEnum;
import com.yasinyt.system.service.RedisClient;

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
		RequiresPermission requiresPermission = getRequiresPermission(point);
		if(requiresPermission==null) return;
		String[] permisson = requiresPermission.permisson();
		UserSessionVO userSession = getUserSession();
		if(userSession == null) throw new AuthorizeException();
		User user = userSession.getUser();
		if(requiresPermission.isSpecific() && Arrays.asList(requiresPermission.specificUsers()).contains(user.getUserName())) return;
		Set<Permission> permissions = userSession.getPermissions();
		String[] userpermissions = permissions.stream().filter(e -> ActiveStatusEnum.ACTIVE.getCode().equals(e.getStatus())).map(Permission::getPermission).collect(Collectors.toSet()).toArray(new String[0]);
		String[] intersect = ArrayUtil.intersect(userpermissions, permisson);
		if(intersect.length != permisson.length) throw new SysException(ResultEnum.NOT_AUTHORIZATION);
	}

	/**获取需要查询的权限*/
	private RequiresPermission getRequiresPermission(JoinPoint point) {
		MethodInvocationProceedingJoinPoint methodPoint = (MethodInvocationProceedingJoinPoint) point;
		try {
			Field proxy = methodPoint.getClass().getDeclaredField("methodInvocation");
			proxy.setAccessible(true);
			ReflectiveMethodInvocation j = (ReflectiveMethodInvocation) proxy.get(methodPoint);
			Method method = j.getMethod();
			return method.getAnnotation(RequiresPermission.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**获取当前用户的权限session*/
	private UserSessionVO getUserSession(){
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		Cookie cookie = CookieUtil.getCookie(request,"token");
		if(cookie==null) throw new AuthorizeException();
		UserSessionVO vo = redisClient.get(UserSessionKey.generateKeyByToken,cookie.getValue(),UserSessionVO.class);
		if(vo == null) throw new AuthorizeException();
		return vo;
	}

}
