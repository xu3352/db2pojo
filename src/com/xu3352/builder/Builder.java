package com.xu3352.builder;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.xu3352.config.SetupConfig;
import com.xu3352.config.TemplateMapping;
import com.xu3352.core.BuildFactory;
import com.xu3352.jdbc.AbstractDaoSupport;
import com.xu3352.util.DateUtil;
import com.xu3352.util.MyUtils;

/**
 * Builder Entry
 * @author xuyl
 * @date 2013-1-7
 */
public class Builder {
	/** freemarker factory */
	private static BuildFactory factory = new BuildFactory();
	/** config instance */
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
                // 模板文件
                String template = MyUtils.getTemplatePath(m);

                // 输出文件
                // String output = MyUtils.getOutPutPath(m, tableName);
                String output = m.buildOutputPath(tableName);

                // 参数数据
                Map<String, Object> data = factory.buildParams(m, tableName);

                // freemaker批量生产代码
                 factory.build(template, data, output);
			}
		}
	}

    /** 清理 target 目录 */
    private void clean() {
        String targetDir = SetupConfig.getTargetDir();
        MyUtils.rm(new File(targetDir));
        System.out.println("clear dir:" + targetDir);
    }

	/** 程序执行入口 */
	public static void main(String[] args) {
        System.out.println(DateUtil.getCurrentTime() + " - build start ...");
        Builder builder = new Builder();

        // 每次运行前清理 target 目录
        builder.clean();

        // 开始批量生成代码
        builder.db2pojoEntry();
		System.out.println(DateUtil.getCurrentTime() + " - Congratulations! Your code generate successfully....^_^.....");
	}
}
