package com.yasinyt.admin.base.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.yasinyt.admin.base.validator.UserNameValidator;

/**
 * @datail 参数校验器 用户名是否合法
 * @author TangLingYun
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { UserNameValidator.class })
public @interface CheckUserName {

	boolean required();

	String message() default "用户名不合法";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
