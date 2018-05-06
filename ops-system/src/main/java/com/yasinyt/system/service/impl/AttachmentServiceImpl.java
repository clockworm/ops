package com.yasinyt.system.service.impl;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.yasinyt.admin.entity.User;
import com.yasinyt.admin.util.KeyUtil;
import com.yasinyt.admin.web.vo.ResultVO;
import com.yasinyt.system.base.exception.SysException;
import com.yasinyt.system.dao.AttachmentDao;
import com.yasinyt.system.entity.Attachment;
import com.yasinyt.system.enums.ResultEnum;
import com.yasinyt.system.service.AttachmentService;
import com.yasinyt.system.util.FileUtil;
import com.yasinyt.system.util.ResultVOUtil;

import lombok.SneakyThrows;

/**
 * @detail 附件文件的上传下载等功能实现
 * @author TangLingYun
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

	@Autowired
	private AttachmentDao attachmentDao;

	@Override @SneakyThrows
	public ResultVO<?> uploadFile(CommonsMultipartFile[] files,String dirPath,User user) {
		FileUtil.forceMkdir(new File(dirPath));
		List<Attachment> atts = Collections.emptyList();
		for (CommonsMultipartFile commonsMultipartFile : files) {
			String fileName = StringUtils.substringBeforeLast(commonsMultipartFile.getOriginalFilename(), ".");
			String postfix = StringUtils.substringAfterLast(commonsMultipartFile.getOriginalFilename(), ".");
			String fileId = KeyUtil.genUniqueNumKey();
			String filePath = dirPath.concat("/").concat(fileId).concat(postfix);
			File file = new File(filePath);
			commonsMultipartFile.transferTo(file);
			Attachment attachment = new Attachment(file,fileName, user.getUserName(), "备注");
			attachment.setId(fileId);
			atts.add(attachment);
		}
		attachmentDao.insertBatch(atts);
		return ResultVOUtil.success();
	}

	@Override
	public void downloadFile(HttpServletResponse response,String attachmentId) {
		Attachment attachment = attachmentDao.findById(attachmentId);
		if(attachment == null) throw new SysException(ResultEnum.NOT_FOUND_FILE);
		String filePath = attachment.getFilePath();
		String titleName = attachment.getFileName().concat(".").concat(attachment.getFileType());
		FileUtil.downLoad(response, filePath, titleName);
	}

}
