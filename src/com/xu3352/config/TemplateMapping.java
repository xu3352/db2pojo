package com.xu3352.config;

import com.xu3352.util.MyUtils;
import com.xu3352.util.StringUtil;

/**
 * 模板文件的生成路径映射关系
 * @author xuyl
 * @date 2013-1-7
 */
public class TemplateMapping {
	private String template;    // 模板
	private String output_path; // 生成代码的文件名(全路径)

    /** 构造输出文件路径 */
    public String buildOutputPath(String tableName) {
        String path = fillOutputPath(tableName);
        // 转换
        path = SetupConfig.getTargetDir() + StringUtil.filePathConvert(path);
        MyUtils.mkdir(path);    // if not exists
        return path;
    }

    /** 使用 output_path 字段解析出报名 */
    public String buildPackageNew(String tableName) {
        String path = fillOutputPath(tableName);
        return StringUtil.packageConvert(path);
    }

    // 填充参数值
    private String fillOutputPath(String tableName) {
        if (StringUtil.isBlank(output_path)) return "";
        String path = output_path;

        String className = StringUtil.className(tableName);     // 表明转类名(驼峰式写法)
        String instance = StringUtil.uncapFirst(className);     // 实例名称:类名首字母小写

        SetupConfig config = SetupConfig.getInstance();
        path = StringUtil.assignValue(path, "project", config.getProject());
        path = StringUtil.assignValue(path, "alias", config.getAlias());
        path = StringUtil.assignValue(path, "class_name", className);
        path = StringUtil.assignValue(path, "instance", instance);
        return path;
    }

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

    public String getOutput_path() {
        return output_path;
    }

    public void setOutput_path(String output_path) {
        this.output_path = output_path;
    }

    @Override
    public String toString() {
        return "TemplateMapping{" +
                "template='" + template + '\'' +
                ", output_path='" + output_path + '\'' +
                '}';
    }
}
