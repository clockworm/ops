package com.yasinyt.admin.base.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * @detail 接口访问次数控制注解(默认5秒内类接口访问最大次数为5次 默认需要用户登录)
 * @author TangLingYun
 */
@Target({ METHOD })
@Retention(RUNTIME)
public @interface AccessLimit {

	/** 时间范围内 */
	int seconds() default 5;

	/** 最大访问次数 */
	int maxCount() default 5;
}
