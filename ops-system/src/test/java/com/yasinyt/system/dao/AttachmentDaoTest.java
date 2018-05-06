package com.yasinyt.system.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yasinyt.admin.util.KeyUtil;
import com.yasinyt.system.base.BaseTest;
import com.yasinyt.system.entity.Attachment;

public class AttachmentDaoTest extends BaseTest {

	@Autowired
	AttachmentDao attachmentDao;

	@Test
	public void testInsertBatch() {
		File file = new File("E://人人融借款申请书@2017120713130801.pdf");
		String uploader = "tanglingyun";
		String remark = "测试用例";
		List<Attachment> atts = new ArrayList<Attachment>();
		for (int i = 0; i < 5; i++) {
			Attachment attachment = new Attachment(file,"文件名",uploader, remark + i);
			attachment.setId(KeyUtil.genUniqueNumKey());
			atts.add(attachment);
		}
		int i = attachmentDao.insertBatch(atts);
		System.err.println("添加成功条数:" + i);
	}
	
	@Test
	public void testFindById() {
		Attachment attachment = attachmentDao.findById("1521343565309324887");
		System.err.println(attachment);
	}

}
