package com.xu3352.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * Created by xuyinglong on 17/9/25.
 */
public class DateUtil {
    static SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat FORMAT_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /** 当前日期 */
    public static String getCurrentDate() {
        return FORMAT_DATE.format(new Date().getTime());
    }

    /** 当前时间 */
    public static String getCurrentTime() {
        return FORMAT_DATETIME.format(new Date().getTime());
    }

    public static void main(String[] args) {
        System.out.println( getCurrentDate() );
        System.out.println( getCurrentTime() );
    }
}
