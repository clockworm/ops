package com.yasinyt.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yasinyt.admin.util.ResultVOUtil;
import com.yasinyt.admin.web.vo.ResultVO;
import com.yasinyt.admin.web.vo.UserSessionVO;

@Controller
@RequestMapping("menu")
public class MenuController {
	
	@PostMapping("list")
	public @ResponseBody ResultVO<?> menu(UserSessionVO userSession){
		return ResultVOUtil.success(userSession.getMenuVos());
	}
}
