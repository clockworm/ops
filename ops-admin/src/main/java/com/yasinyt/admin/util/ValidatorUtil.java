package com.yasinyt.admin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author TangLingYun
 * @describe 效验工具库
 */
public class ValidatorUtil {
	
	/** 手机号码表达式*/
	private static final Pattern MOBILE_PATTERN = Pattern.compile("1\\d{10}");
	/** 用户名表达式*/
	private static final Pattern USERNAME_PATTERN = Pattern.compile("[0-9a-zA-Z_]{3,18}");
	
	/** 手机号码是否正确*/
	public static boolean isMobile(String mobile) {
		if(StringUtils.isBlank(mobile)) return false;
		Matcher matcher = MOBILE_PATTERN.matcher(mobile);
		return matcher.matches();
	}
	
	/** 用户名是否合法*/
	public static boolean isUserName(String username) {
		if(StringUtils.isBlank(username)) return false;
		Matcher matcher = USERNAME_PATTERN.matcher(username);
		return matcher.matches();
	}

}
