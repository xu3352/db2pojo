package com.xu3352.core;

import com.xu3352.util.StringUtil;

/**
 * 数据库列实体
 * 
 * @author Yinglong Xu
 * @date 2012-2-16
 */
public class Column {

	private String type;    // 列数据类型
	private String name;    // 字段名
	private String nameJ;   // 字段名：java风格，驼峰式
	private String remark;  // 字段备注、注释
	private String defaultValue;    // 字段默认值

	private RemarkDict remarkDict;  // 备注字典

	/**
	 * 默认构造
	 */
	public Column() {
	}

	/**
	 * 构造
	 * @param type 
	 * @param name 
	 * @param nameJ 
	 */
	public Column(String type, String name, String nameJ) {
		this.type = type;
		this.name = name;
		this.nameJ = nameJ;
	}
	
	/**
	 * 全构造
	 * @param type
	 * @param name
	 * @param nameJ
	 * @param remark
	 */
	public Column(String type, String name, String nameJ, String remark) {
		this.type = type;
		this.name = name;
		this.nameJ = nameJ;
        this.remark = StringUtil.isBlank(remark) ? nameJ : remark;
        this.remarkDict = new RemarkDict(this.remark);
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameJ() {
		return nameJ;
	}

	public void setNameJ(String nameJ) {
		this.nameJ = nameJ;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
	    if ("CURRENT_TIMESTAMP".equals(defaultValue)) this.defaultValue = "";
    }

    public RemarkDict getRemarkDict() {
        return remarkDict;
    }

    public void setRemarkDict(RemarkDict remarkDict) {
        this.remarkDict = remarkDict;
    }


    /** 是否为日期类型 */
    public boolean getIsDateType() {
        return "Date".equals(type);
    }

    /** 是否为枚举类型 */
    public boolean getIsIntKVType() {
        return remarkDict.getSize() > 1 && "int".equals(type);
    }

    @Override
    public String toString() {
        return "Column{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", nameJ='" + nameJ + '\'' +
                ", remark='" + remark + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", " + remarkDict +
                '}';
    }
}
