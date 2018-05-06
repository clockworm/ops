package com.yasinyt.admin.util.redis;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yasinyt.admin.util.JsonUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @detail Redis客户端集成类
 * @author TangLingYun
 */
@Component
public class RedisClient {

	@Autowired
	private JedisPool jedisPool;

	/** 数据从redis取出 */
	public <T> T get(KeyPrefix prefix,Serializable key, Class<T> clazz) {
		Jedis jedis = null;
		try {
			/**生成key策略*/
			String realKey = String.format(prefix.generateKeyPrefix(),key);
			jedis = jedisPool.getResource();
			String value = jedis.get(realKey);
			return JsonUtil.jsonToObject(value, clazz);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/** 数据存入redis */
	public <T> boolean set(KeyPrefix prefix,Serializable key, T value) {
		Jedis jedis = null;
		try {
			/**生成key策略*/
			String realKey = String.format(prefix.generateKeyPrefix(),key);
			String json = JsonUtil.obejctToJson(value,true);
			jedis = jedisPool.getResource();
			jedis.setex(realKey, prefix.expireSeconds(), json);
			return StringUtils.isNotBlank(json);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	
	/** redis Key是否存在 */
	public boolean exists(KeyPrefix prefix,String key) {
		Jedis jedis = null;
		try {
			/**生成key策略*/
			String realKey = String.format(prefix.generateKeyPrefix(),key);
			jedis = jedisPool.getResource();
			return jedis.exists(realKey);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	/** redis Key的值加一 (值必须是int类型 否则其他类型会报错 当key不存在自动默认当0开始计算) */  //set $value$ + 1
	public long incr(KeyPrefix prefix,Serializable key) {
		Jedis jedis = null;
		try {
			/**生成key策略*/
			String realKey = String.format(prefix.generateKeyPrefix(),key);
			jedis = jedisPool.getResource();
			return jedis.incr(realKey);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	/** redis Key的值减一 (必须先知道值必须是int类型 否则其他类型会报错  当key不存在自动默认当0开始计算) */  //set $value$ - 1
	public long decr(KeyPrefix prefix,String key) {
		Jedis jedis = null;
		try {
			/**生成key策略*/
			String realKey = String.format(prefix.generateKeyPrefix(),key);
			jedis = jedisPool.getResource();
			return jedis.decr(realKey);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	
	/** redis 移除Key */ 
	public long delete(KeyPrefix prefix,String key) {
		Jedis jedis = null;
		try {
			/**生成key策略*/
			String realKey = String.format(prefix.generateKeyPrefix(),key);
			jedis = jedisPool.getResource();
			return jedis.del(realKey);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	
	
}
