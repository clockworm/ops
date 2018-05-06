package com.yasinyt.admin.base.handler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.yasinyt.admin.base.exception.AuthorizeException;
import com.yasinyt.admin.util.CookieUtil;
import com.yasinyt.admin.util.redis.RedisClient;
import com.yasinyt.admin.util.redis.TokenKey;
import com.yasinyt.admin.util.redis.UserSessionKey;
import com.yasinyt.admin.web.vo.UserSessionVO;


/**
 * @detail 如果用户登录  控制层第一个参数为User类型的 自动赋值 (参数自构建)
 * @author TangLingYun
 */
@Component
public class UserArgumentResolverHandler implements HandlerMethodArgumentResolver {

	@Autowired
	private RedisClient redisClient;

	/** 如果请求controller中带有该参数的  进行调用 {@link #resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) }方法*/
	@Override
	public boolean supportsParameter(MethodParameter arg0) {
		Class<?> clazz = arg0.getParameterType();
		return clazz == UserSessionVO.class;
	}

	/**参数注入 赋值*/
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
		String token = request.getParameter(TokenKey.TOKEN_KEY);
		if (StringUtils.isBlank(token)) {
			Cookie cookie = CookieUtil.getCookie(request, TokenKey.TOKEN_KEY);
			if (cookie == null) throw new AuthorizeException();
			token = cookie.getValue();
		}
		UserSessionVO userSessionVO = redisClient.get(UserSessionKey.generateKeyByToken,token, UserSessionVO.class);
		if(userSessionVO == null) throw new AuthorizeException();
		redisClient.set(UserSessionKey.generateKeyByToken,token, userSessionVO);
		CookieUtil.setCookie(response, TokenKey.TOKEN_KEY, token, TokenKey.generateKeyByToken.expireSeconds());
		return userSessionVO;
	}

}
