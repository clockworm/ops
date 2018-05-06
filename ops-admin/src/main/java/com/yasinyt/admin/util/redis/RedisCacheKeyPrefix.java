package com.yasinyt.admin.util.redis;

/**
 * @detail 用户相关redis对象方法缓存Key生产策略
 * @author TangLingYun
 */
public class RedisCacheKeyPrefix extends BasePrefix{
	
	public RedisCacheKeyPrefix(int expireSeconds, String keyPrefix) {
		super(expireSeconds, keyPrefix);
	}

	private static final long serialVersionUID = 1L;
	
	public final static RedisCacheKeyPrefix redisCacheKey = new RedisCacheKeyPrefix(60,"redis_cache_%s");
}
