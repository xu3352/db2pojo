package com.xu3352.util;

import java.io.File;
import java.util.List;

import com.xu3352.config.SetupConfig;
import com.xu3352.config.TemplateMapping;
import com.xu3352.core.Column;

/**
 * 工具类
 * @author xuyl
 * @date 2013-2-28
 */
public class MyUtils {
	/**
	 * config instance
	 */
	private static SetupConfig config = SetupConfig.getInstance();
	
	/**
	 * freemarker template file path
	 * @author xuyl
	 * @date 2013-1-30
	 * @param m
	 * @return
	 */
	public static String getTemplatePath(TemplateMapping m) {
		return config.getTemplateDir() + File.separator + m.getTemplate();
	}
	
	/**
	 * model name of project.(tableName in java style )
	 * @author xuyl
	 * @date 2013-1-8
	 * @param tableName
	 * @return
	 */
	public static String getModelName(String tableName, String separator) {
        return StringUtil.javaStyleOfTableName(tableName);
	}

	/**
	 * mkdir by path if not exist
	 * @author xuyl
	 * @date 2013-1-7
	 * @param filePath
	 */
	public static void mkdir(String filePath) {
		int index = filePath.lastIndexOf("\\");
		int index2 = filePath.lastIndexOf("/");
		if (index + index2 == -2) return;
		index = index > index2 ? index : index2;
		if (index != -1 && !new File(filePath.substring(0, index)).exists()) {
			System.out.println("mkdir - "+ filePath.substring(0, index) );
			new File(filePath.substring(0, index)).mkdirs();
		}
	}

    /** 删除文件/文件夹 */
    public static boolean rm(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = rm(new File(dir, children[i]));
                if (!success) return false;
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    /**
     * 列里是否包含某个类型了
     * @author xuyl
     * @date 2013-1-8
     * @param columns
     * @param type
     * @return
     */
    public static boolean typeContains(List<Column> columns, String type) {
        for (Column c : columns) {
            if (c.getType().equals(type)) return true;
        }
        return false;
    }
}
