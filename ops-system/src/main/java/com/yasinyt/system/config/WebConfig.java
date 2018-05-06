package com.yasinyt.system.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.yasinyt.system.base.handler.AccessCountLimitHandler;
import com.yasinyt.system.base.handler.UserArgumentResolverHandler;


/**
 * @author TangLingYun
 * @describe MVC拦截器 (请求方法之前,仅支持客户端请求层)
 */
@Configuration
public class WebConfig  implements WebMvcConfigurer{

	@Autowired
	private UserArgumentResolverHandler userArgumentResolverHandler;

	@Autowired
	private AccessCountLimitHandler accessCountLimitHandler;


	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(userArgumentResolverHandler);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(accessCountLimitHandler);
	}
}
