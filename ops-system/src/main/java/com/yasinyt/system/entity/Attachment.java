package com.yasinyt.system.entity;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import lombok.ToString;
/**
 * @detail 文件附件对象封装属性类
 * @author TangLingYun
 */
@Data
@ToString
public class Attachment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public Attachment(){
		//无参构造
	}
	
	public Attachment(File file,String fileName,String uploader,String remark){
		this.fileName = fileName;
		this.fileSize = FileUtils.sizeOf(file);
		this.fileType = StringUtils.substringAfter(file.getName(), ".");
		this.filePath = file.getPath();
		this.uploadTime = new Date();
		this.uploader = uploader;
		this.remark = remark;
	}
	
	/** 物理主键 */
	private String id;
	/** 文件名 */
	private String fileName;
	/** 文件类型 */
	private String fileType;
	/** 文件大小 */
	private long fileSize;
	/** 文件保存路径 */
	private String filePath;
	/** 上传时间 */
	private Date uploadTime;
	/** 上传者*/
	private String uploader;
	/** 备注-业务用途来源 */
	private String remark;
}
