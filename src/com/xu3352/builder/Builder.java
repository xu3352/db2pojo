package com.xu3352.builder;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.xu3352.config.SetupConfig;
import com.xu3352.config.TemplateMapping;
import com.xu3352.core.BuildFactory;
import com.xu3352.jdbc.Dao;
import com.xu3352.util.StringUtil;

/**
 * Builder Entry
 * @author xuyl
 * @date 2013-1-7
 */
public class Builder {
	/**
	 * freemarker factory
	 */
	private static BuildFactory factory = new BuildFactory();
	/**
	 * config instance
	 */
	private static SetupConfig config = SetupConfig.getInstance();

	/**
	 * generate from all template files.
	 * @author xuyl
	 * @date 2013-1-7
	 */
	public void db2pojoEntry() {
		// iterator all template file
		TemplateMapping[] mappings = config.getMappings();
		for (TemplateMapping m : mappings) {
			String template = m.getTemplate();
			// iterator all databases tables.
			List<String> tablesList = new Dao().getAllTableName();
			for (String tableName : tablesList) {
				// package of pojo
				factory.setPackagePath(buildDir(m, tableName).replaceAll("[\\/]", "."));
				Map<String, Object> data = factory.getParams(tableName);
				factory.build(template, data, getOutPutPath(m, tableName));
			}
		}
	}
	
	/**
	 * prepare the dir path
	 * @author xuyl
	 * @date 2013-1-7
	 * @param dir
	 * @return
	 */
	private String buildDir(TemplateMapping m, String tableName) {
		return m.getDir()
				.replaceAll("\\$\\{project\\}", config.getProject())
				.replaceAll("\\$\\{model\\}", getModelName(tableName));
	}

	/**
	 * model name of project.(default: tableName in java style )
	 * @author xuyl
	 * @date 2013-1-8
	 * @param tableName
	 * @return
	 */
	private String getModelName(String tableName) {
		// TODO : model group name
		return StringUtil.javaStyleOfTableName(tableName);
	}

	/**
	 * generate output file path.
	 * @author xuyl
	 * @date 2013-1-7
	 * @param m
	 * @param tableName
	 * @return
	 */
	private String getOutPutPath(TemplateMapping m, String tableName) {
		String path = SetupConfig.USER_DIR + SetupConfig.SEPARATOR 
				+ "target" + SetupConfig.SEPARATOR 
				+ buildDir(m, tableName) + SetupConfig.SEPARATOR;
		path += m.getLpadding() + getClassName(tableName) + m.getRpadding() + "." + m.getSuffix();
		mkdir(path);
		return path;
	}
	
	/**
	 * mkdir by path if not exist
	 * @author xuyl
	 * @date 2013-1-7
	 * @param filePath
	 */
	private void mkdir(String filePath) {
		int index = filePath.lastIndexOf('\\');
		if (index == -1) {
			index = filePath.lastIndexOf('/');
		}
		if (index != -1 && !new File(filePath.substring(0, index)).exists()) {
			System.out.println("mkdir - "+ filePath.substring(0, index) );
			new File(filePath.substring(0, index)).mkdirs();
		}
	}

	/**
	 * pojo class name
	 * @author xuyl
	 * @date 2013-1-7
	 * @param tableName
	 * @return
	 */
	private String getClassName(String tableName) {
		return StringUtil.capFirst(StringUtil.javaStyleOfTableName(tableName));
	}
	
	/**
	 * main entry
	 * @param args
	 */
	public static void main(String[] args) {
		new Builder().db2pojoEntry();
		System.out.println("congratulations! your code generate complete....^_^.....");
	}
}
