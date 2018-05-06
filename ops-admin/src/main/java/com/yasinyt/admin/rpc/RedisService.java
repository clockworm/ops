package com.yasinyt.admin.rpc;

import java.io.Serializable;

import com.yasinyt.admin.util.redis.BasePrefix;

/**
 * @detail RPC提供redis接口
 * @author TangLingYun
 */
public interface RedisService {
	public <T> boolean set(BasePrefix prefix,Serializable key, T value);
	public <T> T get(BasePrefix prefix,Serializable key, Class<T> clazz);
	public boolean exists(BasePrefix prefix,String key);
	public long incr(BasePrefix prefix,Serializable key);
	public long decr(BasePrefix prefix,String key) ;
	public long delete(BasePrefix prefix,String key);
}
