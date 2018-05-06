package com.yasinyt.admin.util.redis;

import java.io.Serializable;

/**
 * @detail 缓存Key生产策略接口
 * @author TangLingYun
 */
public interface KeyPrefix extends Serializable {

	/**redis存储过期时间*/
	int expireSeconds();

	/**生成redis存储Key前缀*/
	String generateKeyPrefix();

}
