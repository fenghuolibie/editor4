package com.example.common.util;


import com.example.enums.WeekEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 关于时间的工具类
 */
public class DateUtil {
    /**
     * 获得当前日期的yyMMdd格式的字符串数据
     *
     * @return
     */
    public static String getCurrentDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(date);
    }

    /**
     * 根据yyMMdd格式的字符串获得Date类型的数据
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date getStringDate(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.parse(date);
    }
    /**
     * 获得当前日期的yyyy-MM-dd HH:mm:ss格式的字符串数据
     *
     * @return
     */
    public static String getCurrentDate2(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 根据当前日期算出星期
     * @param date
     * @return
     */
    public static String getWeekByDate(Date date){
        //1代表礼拜天，2代表礼拜一
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayWeek = calendar.get(calendar.DAY_OF_WEEK);
        if (dayWeek == 1) {
            dayWeek = 8;
        }
        return WeekEnum.getByValue(dayWeek).getMessage();
    }

    /**
     * 根据所在日期得到该周所在星期一的日期（yyyyMMdd）
     * @param date
     * @return
     */
    public static String getFirstDayBy(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayWeek = calendar.get(calendar.DAY_OF_WEEK);
        if (dayWeek == 1) {
            dayWeek = 8;
        }
        //当前日期所在星期的第一天
        calendar.add(Calendar.DATE, 2-dayWeek);
        return getCurrentDate(calendar.getTime());
    }

    /**
     * 判断日期格式是否合格(yyyyMMdd)
     */
    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }catch (NullPointerException e){
            convertSuccess = false;
        }
        return convertSuccess;
    }
}
