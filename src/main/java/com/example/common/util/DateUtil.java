package com.example.common.util;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 关于时间的工具类
 */
public class DateUtil {
    /**
     * 获得当前日期的yyMMdd格式的字符串数据
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
        return simpleDateFormat.format(new Date());
    }
}
