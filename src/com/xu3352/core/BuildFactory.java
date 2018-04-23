package com.xu3352.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.xu3352.config.SetupConfig;
import com.xu3352.config.TemplateMapping;
import com.xu3352.jdbc.AbstractDaoSupport;
import com.xu3352.util.DateUtil;
import com.xu3352.util.MyUtils;
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
	private static final Map<String, Map<String, Object>> CACHE = new ConcurrentHashMap<String, Map<String, Object>>();
	private static SetupConfig config = SetupConfig.getInstance();
	private static AbstractDaoSupport dao = AbstractDaoSupport.getInstance();
	
	/**
	 * 配置属性
	 */
	private static Configuration cfg = new Configuration();

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
     * POJO数据准备 (freemaker模版的数据)
     * @param tableName
     * @return Map
     */
    public Map<String, Object> buildParams(TemplateMapping mapping, String tableName) {
        String packagePath = mapping.buildPackageNew(tableName);

        // 数据做缓存，不然一个表每个套模都会查下一次
        if (CACHE.containsKey(tableName)) {
            Map<String, Object> map = CACHE.get(tableName);
            // 每个表有多套模板，所以每次都重置
            map.put("package_path", packagePath);
            return map;
        }

        // 数据准备,可以是Map,List或者是实体
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("package_path", packagePath);

        // 数据库相关：表名, 所有的列信息
        List<Column> columns = dao.queryColumns(tableName);
        map.put("table_name", tableName);
        map.put("table_column", columns);

        // 表名对应的(Model)模块类名，实例名称
        String className = StringUtil.className(tableName);     // 表明转类名(驼峰式写法)
        String instance = StringUtil.uncapFirst(className);     // 实例名称:类名首字母小写
        map.put("class_name", className);
        map.put("instance", instance);

        // globle config field
        map.put("project", config.getProject());
        map.put("alias", config.getAlias());
        map.put("author", config.getAuthor());
        map.put("dbName", config.getDbConfig().getDbName());    // 数据库名(sql语句生成时可能用到)

        // 其他：当前日期 ／ 表里是否包含日期字段(特殊字符处理)
        map.put("hasDateColumn", MyUtils.typeContains(columns, "Date"));
        map.put("sysDate", DateUtil.getCurrentDate());

        CACHE.put(tableName, map);
        return map;
    }
}
