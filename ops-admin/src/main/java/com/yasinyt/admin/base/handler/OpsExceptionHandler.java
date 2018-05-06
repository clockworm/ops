package com.yasinyt.admin.base.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.weibo.api.motan.exception.MotanServiceException;
import com.yasinyt.admin.base.exception.AuthorizeException;
import com.yasinyt.admin.base.exception.OpsException;
import com.yasinyt.admin.base.exception.ResponseSpecialException;
import com.yasinyt.admin.enums.ResultEnum;
import com.yasinyt.admin.util.ResultVOUtil;
import com.yasinyt.admin.web.vo.ResultVO;

import lombok.extern.slf4j.Slf4j;

/**
 * @detail 异常控制处理器
 * @author TangLingYun
 */
@ControllerAdvice
@Slf4j
public class OpsExceptionHandler {

	/** 通用异常控制器 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResultVO<?> handlerException(HttpServletRequest request, Exception e) {
		if (e instanceof BindException) {
			BindException bdEx = (BindException) e;
			List<ObjectError> errors = bdEx.getAllErrors();
			ObjectError error = errors.get(0);
			return ResultVOUtil.fail("参数错误:".concat(error.getDefaultMessage()));
		}else if(e instanceof HttpRequestMethodNotSupportedException) {
			return ResultVOUtil.fail("请求方式错误:"+e.getClass().getSimpleName());
		}
		log.error("异常控制器异常信息:{}",e);
		return ResultVOUtil.error(ResultEnum.SERVER_ERROR.getCode(), ResultEnum.SERVER_ERROR.getMessage());
	}
	
	/** 拦截登录异常 控制器 */
	@ExceptionHandler(value = AuthorizeException.class)
	public ModelAndView handlerAuthorizeException() {
		log.warn("token失效或没有登录,跳转到登录页面");
		return new ModelAndView("redirect:" + "/common/login");
	}

	/** RPC异常控制器 */
	@ExceptionHandler(value = MotanServiceException.class)
	public @ResponseBody ResultVO<?> handlerMotanServiceException(MotanServiceException e) {
		log.error("motan-rpc异常:{}",e.getMessage());
		return ResultVOUtil.error(e.getErrorCode(), e.getMessage());
	}
	
	/** 业务异常控制器 */
	@ExceptionHandler(value = OpsException.class)
	public @ResponseBody ResultVO<?> handlerSellException(OpsException e) {
		log.warn("业务异常:{}",e.getMessage());
		return ResultVOUtil.error(e.getCode(), e.getMessage());
	}
	
	/** 特殊业务异常控制器 返回HTTP 头消息状态为403 */
	@ExceptionHandler(value = ResponseSpecialException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public @ResponseBody void handlerSellException() {
	}
}
