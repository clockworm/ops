package com.yasinyt.admin.util.redis;

/**
 * @detail 用户资源权限缓存Key生产策略
 * @author TangLingYun
 */
public class UserSessionKey extends BasePrefix {
	private static final long serialVersionUID = 1L;
	/** 默认30天过期时间 */
	private UserSessionKey(String keyPrefix) {
		super(keyPrefix);
	}

	/** 自定义多少秒过期时间 */
	public UserSessionKey(int expireSeconds, String keyPrefix) {
		super(expireSeconds, keyPrefix);
	}

	public final static UserSessionKey generateKeyByToken = new UserSessionKey(60*30,"user_permission_token_%s");
}
