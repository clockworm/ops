package com.yasinyt.admin.rpc.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import com.yasinyt.admin.rpc.RedisService;
import com.yasinyt.admin.util.redis.BasePrefix;
import com.yasinyt.admin.util.redis.RedisClient;

@Service
@MotanService
public class RedisServiceImpl implements RedisService {
	
	@Autowired
	private RedisClient redisClient;

	@Override
	public <T> boolean set(BasePrefix prefix, Serializable key, T value) {
		return redisClient.set(prefix, key, value);
	}

	@Override
	public <T> T get(BasePrefix prefix, Serializable key, Class<T> clazz) {
		return redisClient.get(prefix, key, clazz);
	}

	@Override
	public boolean exists(BasePrefix prefix, String key) {
		return redisClient.exists(prefix, key);
	}

	@Override
	public long incr(BasePrefix prefix, Serializable key) {
		return redisClient.incr(prefix, key);
	}

	@Override
	public long decr(BasePrefix prefix, String key) {
		return redisClient.decr(prefix, key);
	}

	@Override
	public long delete(BasePrefix prefix, String key) {
		return redisClient.delete(prefix, key);
	}

}
