package com.yasinyt.admin.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * @detail 自定义注解缓存 # 实现类--> {@link #RedisCacheableAspect}
 * @author TangLingYun
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCacheable {

	/** redis存储主键前缀标识 */
	@AliasFor("value")
	String cacheName() default "";

	@AliasFor("cacheName")
	String value() default "";

	/** redis存储主键 */
	String key() default "";

	/** 过期时间 单位秒 */
	int expiryTimeSecond() default 60;
}
