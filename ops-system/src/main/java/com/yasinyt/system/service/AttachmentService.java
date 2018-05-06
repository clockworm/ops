package com.yasinyt.system.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.yasinyt.admin.entity.User;
import com.yasinyt.admin.web.vo.ResultVO;

/**
 * @detail 附件逻辑层
 * @author TangLingYun
 */
public interface AttachmentService {

	/**文件上传*/
	ResultVO<?> uploadFile(CommonsMultipartFile[] files,String dirPath,User user);
	
	/**文件下载*/
	void downloadFile(HttpServletResponse response,String attachmentId);
}
