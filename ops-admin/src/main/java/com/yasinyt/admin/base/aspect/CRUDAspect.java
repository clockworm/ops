package com.yasinyt.admin.base.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

import com.yasinyt.admin.base.exception.OpsException;
import com.yasinyt.admin.enums.ResultEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * @detail 增删改查是否操作成功
 * @author TangLingYun
 */
@Aspect
@Component
@Slf4j
public class CRUDAspect {
	
	@Pointcut("execution(public int com.yasinyt.admin.service.impl.*.*(..))")
	public void verify() {
	}

	@Before("verify()")
	public void before(JoinPoint joinPoint) {
		String args = Arrays.toString(joinPoint.getArgs());
		MethodInvocationProceedingJoinPoint methodJoinPoint = (MethodInvocationProceedingJoinPoint)joinPoint;
		Signature signature = methodJoinPoint.getSignature();
		log.info("调用开始:{},入参:{}",signature,args);
	}
	
	@AfterReturning(pointcut = "verify()",returning = "value")
	public void afterReturn(JoinPoint joinPoint,Object value) {
		MethodInvocationProceedingJoinPoint methodJoinPoint = (MethodInvocationProceedingJoinPoint)joinPoint;
		log.info("调用结束:{},出参:{}",methodJoinPoint.getSignature(),value);
		Integer i = (Integer) value;
		if(i <= 0) throw new OpsException(ResultEnum.CRUD_FAIL);
	}

}
