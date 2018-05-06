package com.yasinyt.admin.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZipUtil {

	private static final int BUFFER = 1024;

	/** 开始压缩 */
	public static void zipFiles(String zipPath,String needZipDir) {
		try {
			/** 创建压缩文件 */
			File zipFile = new File(zipPath);
			// 开始压缩
			File file = new File(needZipDir);
			if (!file.exists()) throw new RuntimeException(needZipDir + "不存在！");
			FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
			CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());
			try (ZipOutputStream out = new ZipOutputStream(cos)) {
				String basedir = "";
				compress(file, out, basedir);
			}
		} catch (Exception e) {
			log.error("文件压缩出错 请检查配置文件目录是否手动创建:{}", e);
		}
		log.info("打包压缩完毕:{}", zipPath);
	}

	/** 压缩逻辑处理 */
	private static void compress(File file, ZipOutputStream out, String basedir) {
		/* 判断是目录还是文件 */
		if (file.isDirectory()) {
			log.info("压缩：" + basedir + file.getName());
			compressDirectory(file, out, basedir);
		} else {
			log.info("压缩：" + basedir + file.getName());
			compressFile(file, out, basedir);
		}
		// 删除缓存
		file.delete();
	}

	/** 压缩一个目录 */
	private static void compressDirectory(File dir, ZipOutputStream out, String basedir) {
		if (!dir.exists()) return;
		File[] files = dir.listFiles();
		if(files == null) return;
		for (int i = 0,length = files.length; i < length; i++) {
			/* 递归 */
			compress(files[i], out, basedir + dir.getName() + "/");
		}
	}

	/** 压缩一个文件 */
	private static void compressFile(File file, ZipOutputStream out, String basedir) {
		if (!file.exists()) return;
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			ZipEntry entry = new ZipEntry(basedir + file.getName());
			out.putNextEntry(entry);
			int count;
			byte data[] = new byte[BUFFER];
			while ((count = bis.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			bis.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
