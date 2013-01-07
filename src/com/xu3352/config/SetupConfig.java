package com.xu3352.config;

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
	private static SetupConfig instance;
	
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
	 * get singleton instance of SetupConfig
	 * @author xuyl
	 * @date 2013-1-7
	 * @return
	 */
	public static SetupConfig getInstance() {
		if (instance == null) {
			instance = new Gson().fromJson(loadJson(), SetupConfig.class);
		}
		return instance;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public DbConfig getDbConfig() {
		return dbConfig;
	}

	public void setDbConfig(DbConfig dbConfig) {
		this.dbConfig = dbConfig;
	}

	public TemplateMapping[] getMappings() {
		return mappings;
	}

	public void setMappings(TemplateMapping[] mappings) {
		this.mappings = mappings;
	}
	
}
