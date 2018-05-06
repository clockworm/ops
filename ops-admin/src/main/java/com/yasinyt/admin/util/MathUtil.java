package com.yasinyt.admin.util;


/**
 * @author TangLingYun
 * @describe  数字相关工具类 包括小数 浮点数
 */
public class MathUtil {

	private static  final  Double MONEY_RANGE = 0.01;

	/**
	 * 比较2个双精度浮点数 是否相等
	 * @param d1
	 * @param d2
	 * @return 是否相等的结果 true 相等 false 不相等
	 */
	public static boolean equals(Double d1,Double d2){
		double abs = Math.abs(d1 - d2);
		if(abs < MONEY_RANGE){
			return  true;
		}
		return  false;
	}
}
