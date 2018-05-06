package com.yasinyt.system.service.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.yasinyt.admin.rpc.RedisService;
import com.yasinyt.admin.util.redis.BasePrefix;
import com.yasinyt.system.service.RedisClient;

@Service
public class RedisClientImpl implements RedisClient{

	@MotanReferer(basicReferer = "motantestClientBasicConfig")
	RedisService redisService;
	
	@Override
	public <T> boolean set(BasePrefix prefix, Serializable key, T value) {
		return redisService.set(prefix, key, value);
	}

	@Override
	public <T> T get(BasePrefix prefix, Serializable key, Class<T> clazz) {
		return redisService.get(prefix, key, clazz);
	}

	@Override
	public boolean exists(BasePrefix prefix, String key) {
		return redisService.exists(prefix, key);
	}

	@Override
	public long incr(BasePrefix prefix, Serializable key) {
		return redisService.incr(prefix, key);
	}

	@Override
	public long decr(BasePrefix prefix, String key) {
		return redisService.decr(prefix, key);
	}

	@Override
	public long delete(BasePrefix prefix, String key) {
		return redisService.delete(prefix, key);
	}

}
