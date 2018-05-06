package com.yasinyt.admin.lombok;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import lombok.Cleanup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@EqualsAndHashCode(of={"id"},doNotUseGetters=true)
public class LombokTest {
	private String id;
	private String name;

	public static void main(String[] args) {
		notNullExample("123");
	}

	/**对象比较*/
	public static void equalsTest(){
		LombokTest user1 = new LombokTest();
		LombokTest user2 = new LombokTest();
		log.info("{}",user1.equals(user2));
	}

	/**代码val变量自匹配class类型*/
	public static void foreachTest(){
		val list = new ArrayList<LombokTest>();
		val user1 = new LombokTest();
		val user2 = new LombokTest();
		user1.setId("123");
		user2.setId("456");
		list.add(user1);
		list.add(user2);
		list.forEach(e->{
			e.setId(e.getId().concat("7788"));
			log.info("{}",e.getId());
		});
	}

	/**异常处理 自动关闭流*/
	public @SneakyThrows static void copyFileTest(){
		val dirPath = "E://test";
		FileUtils.forceMkdir(new File(dirPath));
		val sourcefile = new File(dirPath.concat("//a.txt"));
		val destfile = new File(dirPath.concat("//b.txt"));
		@Cleanup val input = new FileInputStream(sourcefile);
		@Cleanup val output = new FileOutputStream(destfile);
		byte[] buf = new byte[1024];        
		int bytesRead;        
		while ((bytesRead = input.read(buf)) > 0) {
			output.write(buf, 0, bytesRead);
		}
		//FileUtils.copyFile(sourcefile,new File(dirPath.concat("//c.txt")));
	}

	/**参数为null 则掏NullPointerException异常*/
	public static void notNullExample(@NonNull String string) {
		log.info("{}",string);
	}

}
