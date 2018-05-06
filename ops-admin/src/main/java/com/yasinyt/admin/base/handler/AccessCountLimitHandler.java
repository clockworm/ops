package com.yasinyt.admin.base.handler;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yasinyt.admin.base.annotation.AccessLimit;
import com.yasinyt.admin.enums.ResultEnum;
import com.yasinyt.admin.util.IPUtil;
import com.yasinyt.admin.util.JsonUtil;
import com.yasinyt.admin.util.ResultVOUtil;
import com.yasinyt.admin.util.redis.RedisClient;
import com.yasinyt.admin.util.redis.UserKey;

import lombok.extern.slf4j.Slf4j;

/**
 * @detail 接口防刷(用于登录之后的接口)
 * @author TangLingYun
 */
@Component
@Slf4j
public class AccessCountLimitHandler extends HandlerInterceptorAdapter {

	@Autowired
	private RedisClient redisClient;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			AccessLimit limit = hm.getMethodAnnotation(AccessLimit.class);
			if (limit == null) return true;
			String url = request.getRequestURI();
			UserKey userKey = new UserKey(limit.seconds(), "access_limit_" + url + "_%s");
			String ip = IPUtil.getIpAddr(request);
			Integer accessCount = redisClient.get(userKey, ip, Integer.class);
			if (accessCount == null || accessCount == 0) {
				redisClient.set(userKey, ip, 1);
			} else if (accessCount < limit.maxCount()) {
				redisClient.incr(userKey, ip);
			} else {
				response.setContentType("application/json;charset=UTF-8");
				try (OutputStream out = response.getOutputStream()) {
					log.info("当前IP操作过于频繁:{}",ip);
					out.write(JsonUtil.obejctToJson(ResultVOUtil.fail(ResultEnum.ACCESS_LIMIT.getMessage()), false).getBytes("UTF-8"));
					out.flush();
				}
				return false;
			}
		}
		return true;
	}

}
