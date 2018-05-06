package com.yasinyt.admin.util;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.val;

/**
 * @author TangLingYun
 * @describe Cookie工具类 写回客户端 做token认证
 */
public class CookieUtil {

	/**
	 * 设置Cookie
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	/**
	 * 获取Cookie
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Map<String, Cookie> map = readCookieMap(request);
		if(map.containsKey(name)){
			return map.get(name);
		}else{
			return  null;
		}
	}

	/** Cookie转化Map*/
	private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<>();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (val cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}
