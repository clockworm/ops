package com.yasinyt.system.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import lombok.Cleanup;
import lombok.SneakyThrows;

public class FileUtil extends FileUtils{


	@SneakyThrows
	public static void downLoad(HttpServletResponse response,String filePath,String titleName){
		File file = new File(filePath);
		response.reset();  
		response.setContentType("application/octet-stream");  
		response.setCharacterEncoding("utf-8");
		response.setContentLength((int) FileUtil.sizeOf(file));
		response.setHeader("Content-disposition", "attachment;filename=" + new String(titleName.getBytes("UTF-8"),"iso-8859-1"));
		byte[] buff = new byte[1024];
		@Cleanup OutputStream ouputStream = response.getOutputStream();
		@Cleanup BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		int i = 0;
		while ((i = bis.read(buff)) != -1) {
			ouputStream.write(buff, 0, i);
			ouputStream.flush();
		}
	}
}
