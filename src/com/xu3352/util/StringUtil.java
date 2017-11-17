package com.xu3352.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xu3352.config.SetupConfig;

/**
 * 字符串工具类
 * @author Yinglong Xu
 * @date 2012-2-16
 */
public class StringUtil {
    private static final Pattern PATTERN_NUMBER = Pattern.compile("(\\d+)");

    /**
     * 是否为空字符串
     * @param str
     * @return
     */
    public static Boolean isBlank(String str) {
        return null == str || "".equals(str.trim());
    }

    /**
     * 是否为非空字符串
     * @param str
     * @return
     */
    public static Boolean isNotBlank(String str) {
        return !isBlank(str);
    }

	/**
	 * 首字母大写
	 * @param str
	 * @return String
	 */
	public static String capFirst(String str) {
		String firstC = str.substring(0, 1);
		return str.replaceFirst(firstC, firstC.toUpperCase());
	}
	
	/**
	 * java风格编程：驼峰式命名<br/>
	 * eg:user_name -> userName
	 * @param columnName
	 * @return String
	 */
	public static String javaStyle(String columnName) {
		String patternStr = "(_[a-z])";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(columnName);
		StringBuffer buf = new StringBuffer();
		while (matcher.find()) {
			String replaceStr = matcher.group();
			matcher.appendReplacement(buf, replaceStr.toUpperCase());
		}
		matcher.appendTail(buf);
		return buf.toString().replaceAll("_", "");
	}
	
	/**
	 * 处理：去掉前缀，驼峰式写法
	 * @param tableName
	 * @return String
	 */
	public static String javaStyleOfTableName(String tableName) {
        String tmpName = tableName;
		String prefixs = SetupConfig.getInstance().getIgnorePrefix();
		String[] ps = prefixs.split(",");
		for (int i = 0; i < ps.length; i++) {
			if (tmpName.startsWith(ps[i])) {
                tmpName = tmpName.replaceAll(ps[i], "");
			}
		}
		return StringUtil.javaStyle(tmpName);
	}
	
	/**
	 * pojo class name
	 * @author xuyl
	 * @date 2013-1-7
	 * @param tableName
	 * @return
	 */
	public static String className(String tableName) {
		return capFirst(javaStyleOfTableName(tableName));
	}

    /**
     * 给指定 ${name} 赋值:value
     * @param source
     * @param name
     * @param value
     * @return
     */
    public static String assignValue(String source, String name, String value) {
        if (isBlank(source)) return "";
        if (!source.contains("$")) return source;
        return source.replaceAll("\\$\\{" + name + "\\}", value);
    }

    /**
     * 首字母小写
     * @param className
     * @return
     */
    public static String uncapFirst(String className) {
        if (isBlank(className)) return "";
        String first = className.substring(0, 1).toLowerCase();
        String last = className.substring(1);
        return first + last;
    }

    /**
     * 类型对应的长度 varchar(100) => 100
     * @param mysqlType
     * @return
     */
    public static int typesLength(String mysqlType) {
        Matcher matcher = PATTERN_NUMBER.matcher(mysqlType);
        if (matcher.find()) return Integer.valueOf(matcher.group(1));
        return 0;
    }

    public static void main(String[] args) {
        // System.out.println( uncapFirst("HELLO") );
        System.out.println( typesLength("varchar(100)") );
        System.out.println( typesLength("tinyint(1)") );
        System.out.println( typesLength("text") );
    }
}
