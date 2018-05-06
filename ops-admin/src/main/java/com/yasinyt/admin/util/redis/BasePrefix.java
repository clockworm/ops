package com.yasinyt.admin.util.redis;

/**
 * @author TangLingYun
 * @describe redis过期时间和生成key 策略 (保证key不唯一)
 */
public abstract class BasePrefix implements KeyPrefix {
	private static final long serialVersionUID = 1L;
	/** 过期时间 默认0为永不过期 */
	private int expireSeconds;
	/** Key前缀策略 */
	private String keyPrefix;

	public BasePrefix(int expireSeconds, String keyPrefix) {
		this.expireSeconds = expireSeconds;
		this.keyPrefix = keyPrefix;
	}
	
	/**默认过期时间30天*/
	public BasePrefix(String keyPrefix) {
		this.expireSeconds = 60*60*24*30;
		this.keyPrefix = keyPrefix;
	}

	@Override
	public int expireSeconds() {
		return expireSeconds;
	}

	@Override
	public String generateKeyPrefix() {
		String className = getClass().getSimpleName();
		return className + ":" + keyPrefix;
	}

}
