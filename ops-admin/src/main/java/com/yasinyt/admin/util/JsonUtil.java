package com.yasinyt.admin.util;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * @author TangLingYun
 * @describe 日志格式化
 */
public class JsonUtil {
	
	/**自定义null适配器*/
	private static final TypeAdapter<String> NULL_TO_STRING_NEED_PRINT = new TypeAdapter<String>() {
		public String read(JsonReader reader) throws IOException {
			if (reader.peek() == JsonToken.NULL) {
				reader.nextNull();
				return "";
			}
			return reader.nextString();
		}
		public void write(JsonWriter writer, String value) throws IOException {
			if (value == null) {
				/** 在这里处理null改为空字符串*/
				writer.value("");
				return;
			}
			writer.value(value);
		}
	};
	
	
	/** 对象格式化JSon输出 */
	public static String toPrintJson(Object object) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		// 逗号换行符
		gsonBuilder.setPrettyPrinting();
		Gson gson = gsonBuilder.create();
		String json = gson.toJson(object);
		return object.getClass().getName() + "=" + json;
	}

	/** Json字符串转Object */
	public static <T> T jsonToObject(String json, Class<T> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(json, clazz);
	}

	/** Object字符串转Json  
	 * (flag标识当值为空属性字段是否忽略 true 忽略 false 不忽略) 
	 * true  当值为空忽略字段打印
	 * false 当值为空字段属性不打印
	 * */
	public static <T> String obejctToJson(T value,boolean flag) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		if(flag) {
			/** 注册自定义String的适配器*/
			gsonBuilder.registerTypeAdapter(String.class, NULL_TO_STRING_NEED_PRINT);
		}
		Gson gson = gsonBuilder.create();
		return gson.toJson(value);
	}
	
}