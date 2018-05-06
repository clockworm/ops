package com.yasinyt.admin.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.weibo.api.motan.config.springsupport.AnnotationBean;
import com.weibo.api.motan.config.springsupport.BasicServiceConfigBean;
import com.weibo.api.motan.config.springsupport.ProtocolConfigBean;
import com.weibo.api.motan.config.springsupport.RegistryConfigBean;
import com.weibo.api.motan.util.LoggerUtil;

@Configuration
public class MotanServerConfig implements InitializingBean{

	@Bean
	public AnnotationBean motanAnnotationBean() {
		AnnotationBean motanAnnotationBean = new AnnotationBean();
		motanAnnotationBean.setPackage("com.yasinyt.admin.rpc");
		return motanAnnotationBean;
	}

	@Bean(name = "motanAdmin")
	public ProtocolConfigBean protocolConfig() {
		ProtocolConfigBean config = new ProtocolConfigBean();
		config.setDefault(true);
		config.setName("motan");
		config.setMaxContentLength(1048576);
		return config;
	}

	@Bean(name = "registryConfig")
	public RegistryConfigBean registryConfig() {
		RegistryConfigBean config = new RegistryConfigBean();
		config.setRegProtocol("zookeeper");
		config.setAddress("118.24.49.184:2181,118.24.49.184:2182,118.24.49.184:2183");
		return config;
	}

	@Bean
	public BasicServiceConfigBean baseServiceConfig() {
		BasicServiceConfigBean config = new BasicServiceConfigBean();
		config.setExport("motanAdmin:8002");
		config.setAccessLog(false);
		config.setShareChannel(true);
		config.setGroup("admin-group");
		config.setModule("motan-admin-rpc");
		config.setApplication("ops-admin");
		config.setRegistry("registryConfig");
		config.setAsync(false);
		config.setRequestTimeout(3000);
		return config;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		LoggerUtil.setLogService(new MotanLogService());
	}

}
