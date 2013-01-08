package com.xu3352.config;

/**
 * 模板文件的生成路径映射关系
 * @author xuyl
 * @date 2013-1-7
 */
public class TemplateMapping {
	private String template;
	private String dir;
	private String suffix = "java";		// default java
	private String rpadding = "";		// padding the end of file name 
	private String lpadding = "";		// padding the start of file name 
	
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

	@Override
	public String toString() {
		return "{template=" + template + ", dir=" + dir + ", suffix=" + suffix + ", lpadding="
				+ lpadding + ", rpadding=" + rpadding + "}";
	}

}
