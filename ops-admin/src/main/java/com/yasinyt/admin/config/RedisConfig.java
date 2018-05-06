package com.yasinyt.admin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.Data;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author TangLingYun
 * @describe 读取application.properties配置文件redis前缀的配置信息// -> 自定义redis配置Bean  //
 */
@Component
@ConfigurationProperties(prefix="redis")
@Data
public class RedisConfig {

	/**redis主机地址*/
	private String host;
	/**redis主机端口*/
	private int port;
	/**连接超时时间(毫秒)*/
	private int timeout;
	/**redis主机连接密码*/
	private String password;
	/**redis最大连接数*/
	private int poolMaxTotal;
	/**redis最大连接空闲数*/
	private int poolMaxIdle;
	/**redis连接等待时间(毫秒)*/
	private int poolMaxWait;
	
	
	@Bean(name = "jedisPool")
	public JedisPool JedisPoolFactory() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(poolMaxIdle);
		jedisPoolConfig.setMaxTotal(poolMaxTotal);
		jedisPoolConfig.setMaxWaitMillis(poolMaxWait * 1000);
		JedisPool pool = new JedisPool(jedisPoolConfig,host,port,timeout * 1000 ,password,0);
		return pool;
	}
	
}
