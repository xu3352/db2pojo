package com.sunland.entry;


import java.io.File;
import java.util.List;
import java.util.Map;

import com.sunland.core.BuildFactory;
import com.sunland.jdbc.Dao;
import com.sunland.util.PropertyUtil;
import com.sunland.util.StringUtil;

/**
 * 生成测试
 * @author Yinglong Xu
 * @date 2012-2-17
 */
public class EntryTest {
	/**
	 * 代码生成工厂
	 */
	private static BuildFactory factory = new BuildFactory();
	/**
	 * 目的目录
	 */
	private static String targetDir = "";
	/**
	 * 包路径
	 */
	private static String packagePath = "";
	/**
	 * 测试目录路径
	 */
	private static String testPath = "";
	/**
	 * 基路径
	 */
	private static String basePath = "";
	/**
	 * 模块名称
	 */
	private static String model = "";
	/**
	 * 项目名称
	 */
	private static String modelBase = "";
	/**
	 * 数据库表列表：正常需要生成的表
	 */
	private static List<String> tablesList;
	/**
	 * 忽略表列表
	 */
	private static List<String> denyTablesList;
	/**
	 * 丢弃表列表
	 */
	private static List<String> disabledTablesList;
	/**
	 * 模板文件名列表
	 */
	private static List<String> templateList;
	/**
	 * 输出文件名列表
	 */
	private static List<String> outputFileList;
	/**
	 * 当前系统目录标识符
	 */
	static final String SEPARATOR = File.separator;
	
	// 初始化
	static {
		targetDir = PropertyUtil.getProperty("target.dir");
		packagePath = PropertyUtil.getProperty("package.path");
		testPath = PropertyUtil.getProperty("test.path");
		basePath = convertPackage(packagePath);
		modelBase = targetDir + SEPARATOR + basePath + SEPARATOR;
		String regex = ",";
		// 待调用的表:去除“deny.table.list”列表
		tablesList = PropertyUtil.getPropertyList("table.list", regex);
		denyTablesList = PropertyUtil.getPropertyList("deny.table.list", regex);
		if (tablesList.isEmpty()) {	
			tablesList = new Dao().getAllTableName();
			tablesList.removeAll(denyTablesList);
		}
		disabledTablesList = PropertyUtil.getPropertyList("disabled.table.list", regex); // 只生成pojo和xml的表名
		templateList = PropertyUtil.getPropertyList("freemarker.templates.list", regex); // freemarker模板
		outputFileList = PropertyUtil.getPropertyList("freemarker.outputFile.list", regex); // 输出文件列表
	}
	
	/**
	 * 初始化：准备
	 */
	public static void setUp() {
		if (!(new File(targetDir)).exists()) {
			System.out.println("path=[" + targetDir + "] 路径不存在,退出!");
			System.exit(0);
		}
		mkdir(basePath, "/");
		for (String tableName : tablesList) {
			String tableNameJ = StringUtil.javaStyleOfTableName(tableName);
			model = modelBase + tableNameJ;
			(new File(model)).mkdir();					//模块包名
			String[] groups = new String[] { "pojo", "search", "service", "service" + SEPARATOR + "spi", "action" };
			for (String g : groups) {
				if (disabledTablesList.contains(tableName)) {
					(new File(model + SEPARATOR + "pojo" + SEPARATOR)).mkdir();
					break;
				}
				(new File(model + SEPARATOR + g + SEPARATOR)).mkdir();
			}
		}
		// xml	
		(new File(targetDir + SEPARATOR + "xml" + SEPARATOR)).mkdir();
		// test dir "test/com/scss/"
		mkdir(testPath, "/");
	}
	
	/**
	 * 循环创建Dir
	 * @param longPath 
	 * @param regex 
	 */
	private static void mkdir(String longPath, String regex) {
		String[] basePaths = longPath.split(regex);	//包名
		String pp = SEPARATOR;
		for (String p : basePaths) {
			pp +=  p + SEPARATOR;
			(new File(targetDir + pp)).mkdir();
		}
	}
	
	/**
	 * 根据模板生产需要的文档
	 */
	public static void buildFile() {
		setUp();
		// TODO 循环所有的表
		for (String tableName : tablesList) {
			String tableNameJ = StringUtil.javaStyleOfTableName(tableName);
			factory.setPackagePath(packagePath + "." + tableNameJ); // 包名
			model = modelBase + tableNameJ;
			Map<String, Object> params = factory.getParams(tableName);
			String className = StringUtil.capFirst(tableNameJ);
			// 1.pojo/search/service/action
			for (int i = 0; i < templateList.size(); i++) {
				String templateFile = templateList.get(i);
				String outFile = model + outputFileList.get(i).replaceFirst("\\$\\{1\\}", className);
				if (disabledTablesList.contains(tableName) && (templateFile.equals("pojo.ftl"))) {
					factory.build(templateFile, params, outFile);
					break;
				}
				// junit test
				if (templateFile.contains("test.ftl")) {
					outFile = targetDir + testPath + className + "Test.java";
				}
				factory.build(templateFile, params, outFile);
			}
			// 2.ibatis xml
			String templateFile = "ibatis_xml.ftl";
			String xmlFile = targetDir + SEPARATOR + "xml" + SEPARATOR + tableNameJ + ".xml";
			factory.build(templateFile, params, xmlFile);
			print("完成生成：" + tableName);		// finish build
		}
	}
	
	/**
	 * 打印语句
	 * @param obj
	 */
	private static void print(Object obj) {
		System.out.println(obj);
	}
	
	/**
	 * 替换"."为"/"
	 * @param packagePathStr
	 * @return String
	 */
	private static String convertPackage(String packagePathStr) {
		return packagePathStr.replace(".", "/");
	}
	
	/**
	 * 执行入口
	 * @param args
	 */
	public static void main(String[] args) {
		buildFile();
	}
}
