package com.test;

import com.danbay.utils.DateUtils;
import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author yml
 * @Description TestA
 * @date 2018-06-13 16:49
 **/
public class TestA {
    public static void main(String[] args) {
//        Calendar calendar = Calendar.getInstance();
////        calendar.setTimeInMillis(1528300799000L);
//        System.out.println(calendar.getTime());
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
//        try {
//            Date freezeDataDate = simpleDateFormat.parse("180622");
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(freezeDataDate);
//            calendar.set(Calendar.HOUR_OF_DAY, 23);
//            calendar.set(Calendar.MINUTE, 59);
//            calendar.set(Calendar.SECOND, 59);
//            System.out.println(calendar.getTime());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.JUNE, 1);
//        int day = calendar.get(Calendar.DATE);
//        calendar.set(Calendar.DATE, day - 1);
//        calendar.set(Calendar.HOUR_OF_DAY, 16);
//        calendar.set(Calendar.MINUTE, 00);
//        calendar.set(Calendar.SECOND, 00);
        System.out.println(calendar.getTime());

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 16);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);
        DateFormat yyMMdd = new SimpleDateFormat("yyMMdd");
        try {
            calendar2.setTime(yyMMdd.parse("180630"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(calendar2.getTime());

        calendar.setTimeInMillis(1530720000000L);
        System.out.println(calendar.getTime());

        DateTime readTime = DateUtils.parse("20180709235959", DateUtils.YMDHMS2);
        Date readDateBegin = DateUtils.getBeginTimeOfDay(readTime.toDate());
        Date deviceReadTime = DateUtils.getBeginTimeOfDay(new Date());
        Long intervalDays = (deviceReadTime.getTime() - readDateBegin.getTime()) / (1000 * 60 * 60 * 24);
        System.out.println(intervalDays);

        System.out.println(new SimpleDateFormat(DateUtils.YMDHMS2).format(new java.sql.Date(System.currentTimeMillis())));
            calendar2.setTime(null);
            System.out.println(calendar2.getTime());
            System.out.println(calendar2.getTime().compareTo(new java.sql.Date(System.currentTimeMillis())));
    }
}
