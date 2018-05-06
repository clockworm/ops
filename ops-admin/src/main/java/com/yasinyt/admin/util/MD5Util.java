package com.yasinyt.admin.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author TangLingYun
 * @describe 明文加密
 */
public class MD5Util {

	/** MD5加密 */
	public static String md5(String str) {
		return DigestUtils.md5Hex(str);
	}

	/** 生成随机盐值 */
	public static String genUniqueSalt() {
		return KeyUtil.genUniqueNumKey();
	}

	/** 表单MD5换数据库MD5 明文加密成数据库MD5 saltDb数据库随机盐值 */
	public static String formPassToDBPass(String formPass, String salt) {
		String str = "" + salt.charAt(1) + salt.charAt(4) + salt.charAt(0) + formPass + salt.charAt(2) + salt.charAt(3);
		return md5(str);
	}

}
