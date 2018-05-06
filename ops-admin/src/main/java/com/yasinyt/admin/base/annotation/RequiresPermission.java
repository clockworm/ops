package com.yasinyt.admin.base.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @detail 访问接口权限验证(特定的用戶不需要权限直接访问)
 * @author TangLingYun
 */
@Target({ METHOD })
@Retention(RUNTIME)
public @interface RequiresPermission {
	/** 需要的权限 */
	String[] permisson() default {};

	/** 是否指定特定的用戶 默認false */
	boolean isSpecific() default false;

	/** 特定的用戶 默认admin用户 */
	String[] specificUsers() default { "admin" };
}
