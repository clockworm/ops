package com.yasinyt.admin.base.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.yasinyt.admin.base.annotation.CheckUserName;
import com.yasinyt.admin.util.ValidatorUtil;

public class UserNameValidator implements ConstraintValidator<CheckUserName, String> {

	/** 是否必传 */
	private boolean required = false;

	@Override
	/** 得到获取注解值 初始化个人参数 */
	public void initialize(CheckUserName constraintAnnotation) {
		required = constraintAnnotation.required();
	}

	@Override
	/** 是否通过 */
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (required)  return ValidatorUtil.isUserName(value);
		if (StringUtils.isBlank(value)) return true;
		return ValidatorUtil.isUserName(value);
	}
}