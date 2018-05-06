package com.yasinyt.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavController {
	
	@GetMapping("main")
	public String main() {
		return "sys/main";
	}
	@GetMapping("admin")
	public String admin() {
		return "sys/admin";
	}
	@GetMapping("temp")
	public String temp() {
		return "sys/temp";
	}
	@GetMapping("larryfont")
	public String larryfont() {
		return "sys/larryfont";
	}
	@GetMapping("404")
	public String error404() {
		return "sys/404";
	}
	@GetMapping("animate")
	public String animate() {
		return "sys/animate";
	}
	
}
