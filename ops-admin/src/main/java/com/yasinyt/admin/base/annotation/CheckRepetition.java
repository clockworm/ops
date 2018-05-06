package com.yasinyt.admin.base.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.yasinyt.admin.enums.BusinessTypeEnum;

/**
 * @detail 利用Redis做防止重复提交或请求 (tokenAgr 为参数的列表中的参数名“支持对象属性引用” ：#user.id或#userName)
 * @author TangLingYun
 */
@Target({ METHOD })
@Retention(RUNTIME)
public @interface CheckRepetition {
	
	/**需要控制检验的唯一ID*/
	String tokenAgr();
	
	/**需要处理的当前业务类型*/
	BusinessTypeEnum businessType() default BusinessTypeEnum.COMMON;

}
