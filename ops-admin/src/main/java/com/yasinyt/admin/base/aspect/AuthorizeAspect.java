package com.yasinyt.admin.base.aspect;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yasinyt.admin.base.constant.CookieConstant;
import com.yasinyt.admin.base.exception.AuthorizeException;
import com.yasinyt.admin.util.CookieUtil;
import com.yasinyt.admin.util.redis.RedisClient;
import com.yasinyt.admin.util.redis.UserSessionKey;

import lombok.extern.slf4j.Slf4j;

/**
 * @detal AOP是否登录效验
 * @author TangLingYun
 */
@Aspect
@Component
@Slf4j
public class AuthorizeAspect {

	@Autowired
	private RedisClient redisClient;

	@Pointcut("execution(public  * com.yasinyt.admin.web.controller.*.*(..))" + "&& !execution(public  *  com.yasinyt.admin.web.controller.LoginController.*(..))" + "&& !args(com.yasinyt.admin.entity.User,..)")
	public void verify() {
	}

	@Before("verify()")
	public void doVerify() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		//查询Cookie
		Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN_KEY);
		if(cookie==null){
			log.warn("[登录效验] 服务端Cookie未找到token");
			throw new AuthorizeException();
		}
		UserSessionKey userSession = redisClient.get(UserSessionKey.generateKeyByToken,cookie.getValue(),UserSessionKey.class);
		if(userSession == null){
			log.warn("[登录效验] Redis端未找到token");
			throw new AuthorizeException();
		}
	}
}
