package com.danbay.utils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static final String YMD1 = "yyyy-MM-dd";
    public static final String YMD2 = "yyyyMMdd";
    public static final String YMD3 = "yyyy年MM月dd日";
    public static final String YMDHMS1 = "yyyy-MM-dd HH:mm:ss";
    public static final String YMDHMS2 = "yyyyMMddHHmmss";
    public static final String YMDHMS3 = "yyyy/MM/dd HH:mm:ss";
    public static final String YMDH = "yyyyMMddHH";

    public static final String[] PATTERNS = { YMDHMS1, YMDHMS2, YMD1, YMD2 };

    /**
     * 按指定格式取得当前时间字符串
     *
     * @return String
     */
    public static String now(String pattern) {
        return DateTime.now().toString(org.joda.time.format.DateTimeFormat.forPattern(pattern));
    }

    /**
     * yyyy-MM-dd HH:mm:ss 字符串转为DateTime
     *
     * @param d
     * @return
     */
    public static DateTime parse(String d) {
        return parse(d, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 字符串转为DateTime
     *
     * @param d
     * @param pattern
     * @return
     */
    public static DateTime parse(String d, String pattern) {
        return DateTimeFormat.forPattern(pattern).parseDateTime(d);
    }

    /**
     * 将字符串时间从一种格式转换为另一种格式
     *
     * @param d
     * @param fromPattern
     * @param toPattern
     * @return
     */
    public static String strTranslate(String d, String fromPattern, String toPattern) {
        return parse(d, fromPattern).toString(toPattern);
    }

    public static LocalDate parse2LocalDate(String d, String pattern) {
        return parse(d, pattern).toLocalDate();
    }

    public static LocalDate parse2LocalDate(String d) {
        return parse(d, YMDHMS1).toLocalDate();
    }

    public static Date parseDate(String dateStr) {
        Date date = null;
        try {
            date = org.apache.commons.lang3.time.DateUtils.parseDate(dateStr, PATTERNS);
        } catch (Exception ex) {
        }
        return date;
    }

    /**
     * getBeginTimeOfHour:《获取某小时的开始时间》. <br/>
     *
     * @author Zhouych
     * @param date
     * @return
     */
    public static Date getBeginTimeOfHour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * getBeginTimeOfDay:《获取date这天的开始时间》. <br/>
     *
     * @author Zhouych
     * @param date
     * @return
     */
    public static Date getBeginTimeOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * getEndTimeOfDay:《获取date这天的结束时间》. <br/>
     *
     * @author Zhouych
     * @param date
     * @return
     */
    public static Date getEndTimeOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * getBeginDateOfMonth:《获取当月的开始时间》. <br/>
     *
     * @author Zhouych
     * @param date
     * @return
     */
    public static Date getBeginTimeOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * getEndTimeOfMonth:《获取某月的结束时间》. <br/>
     *
     * @author Zhouych
     * @param date
     * @return
     */
    public static Date getEndTimeOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //下个月开始时间-1毫秒=这个月结束时间
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.MILLISECOND, -1);
        return cal.getTime();
    }

    /**
     * getBeginTimeOfYear:《获取某年的开始时间》. <br/>
     *
     * @author Zhouych
     * @param date
     * @return
     */
    public static Date getBeginTimeOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * getBeginTimeOfYear:《获取某年的结束时间》. <br/>
     *
     * @author Zhouych
     * @param date
     * @return
     */
    public static Date getEndTimeOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, 1);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.MILLISECOND, -1);
        return cal.getTime();
    }

    /**
     * getYesterdayBegin:《获取昨天零点》. <br/>
     *
     * @author Zhouych
     * @return
     */
    public static Date getYesterdayBeginTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * getYesterdayBegin:《获取昨天23点59分59秒》. <br/>
     *
     * @author Zhouych
     * @return
     */
    public static Date getYesterdayFinalTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

}
