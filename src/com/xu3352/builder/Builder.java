package com.xu3352.builder;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.xu3352.config.SetupConfig;
import com.xu3352.config.TemplateMapping;
import com.xu3352.core.BuildFactory;
import com.xu3352.jdbc.AbstractDaoSupport;
import com.xu3352.util.MyUtils;

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
		List<String> tablesList = AbstractDaoSupport.getInstance().queryAllTables();
		for (TemplateMapping m : mappings) {
			// iterator all databases tables.
			for (String tableName : tablesList) {
				String packagePath = m.buildPackage(config.getProject(), MyUtils.getModelName(tableName, "."));
				Map<String, Object> data = factory.getParams(tableName, packagePath);
				factory.build(MyUtils.getTemplatePath(m), data, MyUtils.getOutPutPath(m, tableName));
			}
		}
	}

    /**
     * 清理 target 目录
     */
    private void clean() {
        String targetDir = SetupConfig.USER_DIR + SetupConfig.SEPARATOR + "target" + SetupConfig.SEPARATOR;
        MyUtils.rm(new File(targetDir));
        System.out.println("clear dir:" + targetDir);
    }

	/**
	 * main entry
	 * @param args
	 */
	public static void main(String[] args) {
        Builder builder = new Builder();
        builder.clean();
        builder.db2pojoEntry();
		System.out.println("Congratulations! Your code generate successfully....^_^.....");
	}
}
