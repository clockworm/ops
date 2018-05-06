package com.yasinyt.system.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.yasinyt.admin.web.vo.ResultVO;
import com.yasinyt.admin.web.vo.UserSessionVO;
import com.yasinyt.system.service.AttachmentService;

/**
 * @detail 文件统一控制器
 * @author TangLingYun
 */
@Controller
@RequestMapping("attachment")
public class AttachmentController {
	
	@Autowired
	private AttachmentService attachmentService;

	@PostMapping("upload")
	public @ResponseBody ResultVO<?> upload(UserSessionVO user,@RequestParam("file") CommonsMultipartFile[] files){
		String dirPath = StringUtils.EMPTY;
		return attachmentService.uploadFile(files, dirPath,user.getUser());
	}
	
	@GetMapping("downLoad/{id}")
	public void downLoad(@PathVariable("id") String attachmentId,HttpServletResponse response) {
		attachmentService.downloadFile(response, attachmentId);
	}
}
