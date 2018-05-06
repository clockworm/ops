package com.yasinyt.system.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.weibo.api.motan.config.springsupport.AnnotationBean;
import com.weibo.api.motan.config.springsupport.BasicRefererConfigBean;
import com.weibo.api.motan.config.springsupport.ProtocolConfigBean;
import com.weibo.api.motan.config.springsupport.RegistryConfigBean;
import com.weibo.api.motan.util.LoggerUtil;
import com.yasinyt.admin.config.MotanLogService;

@Configuration
public class MotanClientConfig implements InitializingBean{
	
	@Bean
	public AnnotationBean motanAnnotationBean() {
		AnnotationBean motanAnnotationBean = new AnnotationBean();
		motanAnnotationBean.setPackage("com.yasinyt.system.service.impl");
		return motanAnnotationBean;
	}

	@Bean(name = "motanAdmin")
	public ProtocolConfigBean protocolConfig1() {
		ProtocolConfigBean config = new ProtocolConfigBean();
		config.setDefault(true);
		config.setName("motan");
		config.setMaxContentLength(1048576);
		return config;
	}

	@Bean(name = "registry")
	public RegistryConfigBean registryConfig() {
		RegistryConfigBean config = new RegistryConfigBean();
		config.setRegProtocol("zookeeper");
		config.setAddress("118.24.49.184:2181,118.24.49.184:2182,118.24.49.184:2183");
		return config;
	}

	@Bean(name = "motantestClientBasicConfig")
	public BasicRefererConfigBean baseRefererConfig() {
		BasicRefererConfigBean config = new BasicRefererConfigBean();
		config.setProtocol("motanAdmin");
		config.setRegistry("registry");
		config.setGroup("admin-group");
		config.setModule("motan-admin-rpc");
		config.setApplication("ops-admin");
		config.setCheck(false);
		config.setAccessLog(false);
		config.setThrowException(true);
		config.setRetries(0);
		return config;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		LoggerUtil.setLogService(new MotanLogService());
	}

}
