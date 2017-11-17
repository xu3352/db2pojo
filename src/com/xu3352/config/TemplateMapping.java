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

    // ===================== 不建议使用了(已被 output_path 字段替代了) =====================
    @Deprecated
    private String packagePath;			// default calc from dir
    @Deprecated
    private String dir;
    @Deprecated
    private String rpadding = "";		// padding the end of file name
    @Deprecated
    private String lpadding = "";		// padding the start of file name
    @Deprecated
    private String suffix = "java";		// default java

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

	/**
	 * if packagePath is null, calculate result value of dir filed
	 * @author xuyl
	 * @date 2013-1-8
     * @param tableName
	 */
	public String buildPackage(String tableName) {
        if (StringUtil.isNotBlank(output_path)) return this.buildPackageNew(tableName);

		if (StringUtil.isNotBlank(packagePath)) {
            String packageStr = packagePath;
            String modelName = MyUtils.getModelName(tableName, "/");
            packageStr = StringUtil.assignValue(packageStr, "project", SetupConfig.getInstance().getProject());
            packageStr = StringUtil.assignValue(packageStr, "model", modelName);
            packageStr = StringUtil.assignValue(packageStr, "alias", SetupConfig.getInstance().getAlias());
            return packageStr;
		}
		return buildDir(tableName).replaceAll("[\\/]", ".");
	}

	/**
	 * replace parameter of '${project}' and '${model}' and '${alias}'
	 * @author xuyl
	 * @date 2013-1-8
     * @param tableName
	 */
    public String buildDir(String tableName) {
        if (StringUtil.isBlank(dir)) return "";

        SetupConfig config = SetupConfig.getInstance();
        String path = dir;
        String modelName = MyUtils.getModelName(tableName, "/");
        path = StringUtil.assignValue(path, "project", config.getProject());
        path = StringUtil.assignValue(path, "model", modelName);
        path = StringUtil.assignValue(path, "alias", config.getAlias());

        path = path.replaceAll("\\.", "/");
        return path;
    }

    /** 文件名生成 */
    public String buildFileName(String tableName) {
        return this.getLpadding() + StringUtil.className(tableName) + this.getRpadding() + "." + this.getSuffix();
    }

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getRpadding() {
		return rpadding;
	}

	public void setRpadding(String rpadding) {
		this.rpadding = rpadding;
	}

	public String getLpadding() {
		return lpadding;
	}

	public void setLpadding(String lpadding) {
		this.lpadding = lpadding;
	}

    public String getOutput_path() {
        return output_path;
    }

    public void setOutput_path(String output_path) {
        this.output_path = output_path;
    }

    @Override
	public String toString() {
		return "{template=" + template + ", dir=" + dir + ", suffix=" + suffix + ", lpadding="
				+ lpadding + ", rpadding=" + rpadding + "}";
	}
}
