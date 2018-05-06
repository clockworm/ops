package com.yasinyt.admin.base.aspect;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yasinyt.admin.base.annotation.RedisCacheable;
import com.yasinyt.admin.util.ClassUtil;
import com.yasinyt.admin.util.redis.RedisCacheKeyPrefix;
import com.yasinyt.admin.util.redis.RedisClient;

import lombok.SneakyThrows;

/**
 * @detail 自定义注解缓存AOP实现类
 * @author TangLingYun
 */
@Aspect
@Component
public class RedisCacheableAspect {

	@Autowired
	RedisClient redisClient;

	@Pointcut("@annotation(com.yasinyt.admin.base.annotation.RedisCacheable)")
	public void verify() {
	}

	@Around("verify()")
	public @SneakyThrows Object around(ProceedingJoinPoint joinPoint) {

		// 获取截点的方法的参数键值对->封装成map对象
		Method method = ClassUtil.getMethod(joinPoint);
		Map<String, Object> map = ClassUtil.encapsulationMethodParameters(joinPoint);

		// 获取方法上的注解->处理key的redis存储空间命名(以注解key作为主键)
		RedisCacheable cacheable = method.getAnnotation(RedisCacheable.class);
		String prefix = StringUtils.isBlank(cacheable.value()) ? cacheable.cacheName() : cacheable.value();
		RedisCacheKeyPrefix keyPrefix = new RedisCacheKeyPrefix(cacheable.expiryTimeSecond(), prefix.concat("_%s"));
		String key = cacheable.key().replaceAll("#", StringUtils.EMPTY);
		String[] split = key.split("\\.");
		Object keyValue = map.get(split[0]);
		for (int i = 1, count = split.length; i < count; i++) {
			/** 反射对象调用get方法 获取属性值 */
			keyValue = MethodUtils.invokeExactMethod(keyValue, "get".concat(StringUtils.capitalize(split[i])));
		}

		// 判断是否缓存失效
		boolean exists = redisClient.exists(keyPrefix, keyValue.toString());
		if (exists) {
			@SuppressWarnings("unchecked") Object object = redisClient.get(keyPrefix, keyValue.toString(),((MethodSignature) joinPoint.getSignature()).getReturnType());
			return object;
		} else {
			Object object = joinPoint.proceed(joinPoint.getArgs());
			redisClient.set(keyPrefix, keyValue.toString(), object);
			return object;
		}
	}

}
