package com.yasinyt.admin.util.redis;

/**
 * @detail token认证缓存Key生产策略
 * @author TangLingYun
 */
public class TokenKey extends BasePrefix{
	private static final long serialVersionUID = 1L;
	/**默认30天过期时间*/
	private TokenKey(String keyPrefix) {
		super(keyPrefix);
	}
	
	/**自定义多少秒过期时间*/
	public TokenKey(int expireSeconds, String keyPrefix) {
		super(expireSeconds, keyPrefix);
	}

	public final static String TOKEN_KEY = "token";
	
	/**默认token过期时间为2小时*/
	public final static TokenKey generateKeyByToken = new TokenKey(60*60*2,"token_%s");

}
