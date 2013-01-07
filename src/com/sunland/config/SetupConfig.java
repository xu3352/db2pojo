package com.sunland.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import com.google.gson.Gson;

/**
 * load configuration from "config.json" file
 * @author xuyl
 * @date 2013-1-7
 */
public class SetupConfig {
	private String project;
	private DbConfig dbConfig;
	private TemplateMapping[] mappings;
	
	/**
	 * private Construction
	 */
	private SetupConfig() {}
	
	/**
	 * load config.json file
	 * @author xuyl
	 * @date 2013-1-7
	 * @return
	 */
	private static String loadJson() {
		StringBuilder sb = new StringBuilder("");
		String path = System.getProperty("user.dir") + "/resources/config.json";
		try {
	        BufferedReader in = new BufferedReader(new FileReader(path));
	        String str = "";
	        while ((str = in.readLine()) != null) {
	        	int contentIndex = str.indexOf("//");		// 处理行注释
	        	if (contentIndex != -1) {
	        		str = str.substring(0, contentIndex);
	        	}
	        	sb.append(str);
	        }
	        in.close();
	    } catch (IOException e) {
	    	System.out.println("找不到配置文件:" + path);
	    }
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return "Setup [project=" + project + ", dbConfig=" + dbConfig + ", mappings=" + Arrays.toString(mappings) + "]";
	}
	
	/**
	 * new instance of SetupConfig class
	 * @author xuyl
	 * @date 2013-1-7
	 * @return
	 */
	public static SetupConfig getInstance() {
		return new Gson().fromJson(loadJson(), SetupConfig.class);
	}
}
