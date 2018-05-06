package com.yasinyt.admin.util.redis;

/**
 * @detail Redis防止重复提交关键ID的redis前缀
 * @author TangLingYun
 */
public class CheckRepetitionKey extends BasePrefix{
	
	private static final long serialVersionUID = 1L;
	
	public CheckRepetitionKey(int expireSeconds, String keyPrefix) {
		super(expireSeconds, keyPrefix);
	}

}
