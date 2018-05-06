package com.yasinyt.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;

@EnableAutoConfiguration
@SpringBootApplication
public class AdminApplication {

	public static void main(String[] args) {
		 SpringApplication.run(AdminApplication.class, args);
		 MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
	}
}
