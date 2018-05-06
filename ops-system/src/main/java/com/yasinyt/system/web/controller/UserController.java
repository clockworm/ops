package com.yasinyt.system.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yasinyt.admin.web.vo.ResultVO;
import com.yasinyt.admin.web.vo.UserSessionVO;
import com.yasinyt.system.util.ResultVOUtil;

@Controller
@RequestMapping("user")
public class UserController {
	
	@GetMapping("list")
	public @ResponseBody ResultVO<?> index(UserSessionVO userSession){
		return ResultVOUtil.success(userSession);
	}
}
