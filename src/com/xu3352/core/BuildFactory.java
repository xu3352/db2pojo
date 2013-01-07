package com.xu3352.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.xu3352.jdbc.Dao;
import com.xu3352.util.StringUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * freemarker生成器Test
 * @author Yinglong Xu
 * @date 2012-2-16
 */
public class BuildFactory {
	/**
	 * package路径
	 */
	private String packagePath = "com.test";
	
	/**
	 * 配置属性
	 */
	private static Configuration cfg = new Configuration();
	static {
		setLoadingDir("template");
	}

	/**
	 * 这里我设置模板的根目录
	 * @param dirPath 目录路径
	 */
	public static void setLoadingDir(String dirPath) {
		try {
			cfg.setDirectoryForTemplateLoading(new File(dirPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据模板生成文件
	 * 
	 * @param templateFile 
	 * @param obj 
	 * @param outFile 
	 */
	public void build(String templateFile, Object obj, String outFile) {
		try {
			Template t = cfg.getTemplate(templateFile);
			Writer out = new OutputStreamWriter(new FileOutputStream(outFile), "utf-8");
			t.process(obj, out);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * POJO数据准备
	 * @param tableName 
	 * @return Map 
	 */
	public Map<String, Object> getParams(String tableName) {
		// 数据准备,可以是Map,List或者是实体
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("package_path", packagePath);
		map.put("table_name", tableName);
		map.put("class_name", StringUtil.javaStyleOfTableName(tableName));
		map.put("table_column", new Dao().getGenericColumns(tableName));		// 设置数据
		map.put("sysDate", new Date());
		return map;
	}
	
	/**
	 * 获取包路径
	 * @return String
	 */
	public String getPackagePath() {
		return packagePath;
	}

	/**
	 * 设置包路径
	 * @param packagePath
	 */
	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}
}
