package com.yasinyt.system.base.aspect;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yasinyt.admin.base.annotation.CheckRepetition;
import com.yasinyt.admin.util.ClassUtil;
import com.yasinyt.admin.util.redis.CheckRepetitionKey;
import com.yasinyt.system.service.RedisClient;
import com.yasinyt.system.util.ResultVOUtil;

import lombok.SneakyThrows;

/** 代码非入侵式 利用redis缓存 业务是否处理中(达到防重复请求或提交) 需要使用入侵式请使用{@link com.yasinyt.admin.util.CheckRepetitionUtil.InvadeCode}*/
@Aspect
@Component
public class CheckRepetitionAspect {

	@Autowired
	private RedisClient redisClient;

	private final static ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();

	private final static CheckRepetitionKey CHECK_REPETITION_KEY = new CheckRepetitionKey(60 * 30,"CHECK_REPETITION_KEY_%s");


	@Pointcut("@annotation(com.yasinyt.admin.base.annotation.CheckRepetition)")
	public void verify() {
	}

	@Around("verify()")
	public @SneakyThrows Object around(ProceedingJoinPoint joinPoint) {
		Method method = ClassUtil.getMethod(joinPoint);
		Map<String, Object> map =  ClassUtil.encapsulationMethodParameters(joinPoint);
		CheckRepetition checkRepetition = method.getAnnotation(CheckRepetition.class);
		String tokenAgr = checkRepetition.tokenAgr().replaceAll("#", StringUtils.EMPTY);
		String[] split = tokenAgr.split("\\.");
		Object keyValue = map.get(split[0]);
		for (int i = 1, count = split.length; i < count; i++) {
			/** 反射对象调用get方法 获取属性值 */
			keyValue = MethodUtils.invokeExactMethod(keyValue, "get".concat(StringUtils.capitalize(split[i])));
		}
		String key = checkRepetition.businessType().getMessage().concat(JSON.toJSONString(keyValue));

		try{
			//读锁加锁
			rwlock.readLock().lock();
			if (redisClient.exists(CHECK_REPETITION_KEY, key)) return ResultVOUtil.fail("请不要重复提交");
		}finally {
			//读锁释放
			rwlock.readLock().unlock();
		}

		try{
			try{
				//写锁加锁 开始互斥读锁
				rwlock.writeLock().lock();
				redisClient.set(CHECK_REPETITION_KEY, key,StringUtils.EMPTY);
				//开启重入锁 共享读锁(锁降级)
				rwlock.readLock().lock();
			}finally {
				//写锁释放 开始共享读锁
				rwlock.writeLock().unlock();
				rwlock.readLock().unlock();
			}
			return joinPoint.proceed(joinPoint.getArgs());
		}finally {
			redisClient.delete(CHECK_REPETITION_KEY, key);
		}
	}

}