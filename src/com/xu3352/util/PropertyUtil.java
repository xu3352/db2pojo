package com.xu3352.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 属性文件读取Util
 * @author Yinglong Xu
 * @date 2012-2-17
 */
public class PropertyUtil {
	/**
	 * 属性文件解析类
	 */
	private static Properties properties = new Properties();
	
	// 初始化：加载属性文件
	static {
		try {
			properties.load(new FileInputStream("system.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取属性值
	 * @param key
	 * @return String
	 */
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	/**
	 * 获取属性值按regex分割后的列表集合
	 * @param key
	 * @param regex
	 * @return List
	 */
	public static List<String> getPropertyList(String key, String regex) {
		String values = getProperty(key);
		if (!values.isEmpty()) {
			return Arrays.asList(values.split(regex));
		}
		return new ArrayList<String>();
	}
	
	/**
	 * 测试入口
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getProperty("db_driverClassName"));
		System.out.println(getProperty("table.list").isEmpty());
		System.out.println(Arrays.asList(getProperty("table.list").split(",")));
	}
}
