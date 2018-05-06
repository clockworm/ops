package com.yasinyt.admin.util;

import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang3.StringUtils;

/**
 * @statement 防止重复提交工具类
 * @author TangLingYun
 */
public class CheckRepetitionUtil {

	/** 防止提交 暂存效验容器 */
	private static Map<String, String> repetition = new WeakHashMap<String, String>();

	/** 代码入侵式重复请求 --注解式代码非入侵式请使用{@link #com.yasinyt.admin.base.annotation.CheckRepetition}*/
	public final static class InvadeCode {

		/** 效验是否重复提交 */
		public static synchronized boolean checkRepetition(String token) {
			if (repetition.containsKey(token)) {
				return true;
			} else {
				repetition.put(token, StringUtils.EMPTY);
			}
			return false;
		}

		/** 业务执行完毕移除当前提交状态 */
		public static void removeToken(String token) {
			repetition.remove(token);
		}
	}
}
