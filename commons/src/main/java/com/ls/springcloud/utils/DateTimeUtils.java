package com.ls.springcloud.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName DateTimeUtils
 * @Description
 * @Author lushuai
 * @Date 2019/11/13 10:20
 */
@Slf4j
public class DateTimeUtils {

    /**
     * String 根据格式转为Date
     * @param date
     * @param format
     * @return
     */
    public static Date getDate(String date,String format){
        date = date.trim();
        SimpleDateFormat format1;
        Date ret = null;
        try
        {
            format1 = new SimpleDateFormat(format);
            format1.setLenient(false);
            ret = format1.parse(date);
        }
        catch(Exception ex){
            log.error("{}", ex);
        }
        return ret;
    }

    /**
     * Sting 转 Date
     * @param date
     * @return
     */
    public static Date getDate(String date){
        int len8 = 8;
        int len10 = 10;
        int len14 = 14;
        int len17 = 17;
        int len19 = 19;
        if(date.length() == len8){
            return getDate(date,"yyyyMMdd");
        }
        else if(date.length() == len10){
            if(date.indexOf(FixValue.SIGN_SUBTRACTION) > 0){
                return getDate(date,"yyyy-MM-dd");
            }
            else if(date.indexOf(FixValue.SIGN_DIVISION) > 0){
                return getDate(date,"yyyy/MM/dd");
            }
        }
        else if(date.length() == len14){
            return getDate(date,"yyyyMMddHHmmss");
        }
        else if(date.length() == len17){
            return getDate(date,"yyyyMMdd HH:mm:ss");
        }
        else if(date.length() == len19){
            if(date.indexOf(FixValue.SIGN_SUBTRACTION) > 0){
                return getDate(date,"yyyy-MM-dd HH:mm:ss");
            }
            else if(date.indexOf(FixValue.SIGN_DIVISION) > 0){
                return getDate(date,"yyyy/MM/dd HH:mm:ss");
            }
        }
        return null;
    }

    public static String getString(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * 判断date日期是否过期(与当前时刻比较)
     *
     * @param date
     * @return
     */
    public static Boolean isTimeExpired(Date date) {
        String timeStr = getString(date);
        return isBeforeNow(timeStr);
    }

    /**
     * 判断date日期是否过期(与当前时刻比较)
     *
     * @param timeStr
     * @return
     */
    public static Boolean isTimeExpired(String timeStr) {

        return isBeforeNow(timeStr);
    }

    /**
     * 判断timeStr是否在当前时刻之前
     *
     * @param timeStr
     * @return
     */
    private static Boolean isBeforeNow(String timeStr) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar1.setTime(getDate(timeStr));
        return calendar.before(calendar1);
    }

    public static void main(String[] args) {
        System.out.println(isBeforeNow("2019-10-01"));
    }

    /**
     * 日期加天数
     *
     * @param date
     * @param days
     * @return
     */
    public static Date plusDays(Date date, int days) {
        return plusOrMinusDays(date, days, 0);
    }

    /**
     * 日期减天数
     *
     * @param date
     * @param days
     * @return
     */
    public static Date minusDays(Date date, int days) {
        return plusOrMinusDays(date, days, 1);
    }

    /**
     * 加减天数
     *
     * @param date
     * @param days
     * @param type 0:加天数 1:减天数
     * @return
     */
    private static Date plusOrMinusDays(Date date, int days, Integer type) {
        if (null == date) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        DateTime dateTime = new DateTime(date);
        if (type == 0) {
//            dateTime = dateTime.plusDays(days);
            calendar.set(Calendar.DATE, days);
        } else {
            calendar.set(Calendar.DATE, days);
//            dateTime = dateTime.minusDays(days);
        }
        return calendar.getTime();
//        return dateTime.toDate();
    }

    /**
     * 日期加分钟
     *
     * @param date
     * @param minutes
     * @return
     */
    public static Date plusMinutes(Date date, int minutes) {
        return plusOrMinusMinutes(date, minutes, 0);
    }

    /**
     * 日期减分钟
     *
     * @param date
     * @param minutes
     * @return
     */
    public static Date minusMinutes(Date date, int minutes) {
        return plusOrMinusMinutes(date, minutes, 1);
    }

    /**
     * 加减分钟
     *
     * @param date
     * @param minutes
     * @param type    0:加分钟 1:减分钟
     * @return
     */
    private static Date plusOrMinusMinutes(Date date, int minutes, Integer type) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        DateTime dateTime = new DateTime(date);
        if (type == 0) {
            calendar.set(Calendar.MINUTE, minutes);
//            dateTime = dateTime.plusMinutes(minutes);
        } else {
            calendar.set(Calendar.MINUTE, minutes);
//            dateTime = dateTime.minusMinutes(minutes);
        }
        return calendar.getTime();
//        return dateTime.toDate();
    }

    /**
     * 日期加月份
     *
     * @param date
     * @param months
     * @return
     */
    public static Date plusMonths(Date date, int months) {
        return plusOrMinusMonths(date, months, 0);
    }

    /**
     * 日期减月份
     *
     * @param date
     * @param months
     * @return
     */
    public static Date minusMonths(Date date, int months) {
        return plusOrMinusMonths(date, months, 1);
    }

    /**
     * 加减月份
     *
     * @param date
     * @param months
     * @param type   0:加月份 1:减月份
     * @return
     */
    private static Date plusOrMinusMonths(Date date, int months, Integer type) {
        if (null == date) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (type == 0) {
            calendar.set(Calendar.MONTH, months);
        } else {
            calendar.set(Calendar.MONTH, months);
        }

        return calendar.getTime();
    }

    /**
     * 判断target是否在开始和结束时间之间
     *
     * @param target
     * @param startTime
     * @param endTime
     * @return
     */
//    public static Boolean isBetweenStartAndEndTime(Date target, Date startTime, Date endTime) {
//        if (null == target || null == startTime || null == endTime) {
//            return false;
//        }
//
//        DateTime dateTime = new DateTime(target);
//        return dateTime.isAfter(startTime.getTime()) && dateTime.isBefore(endTime.getTime());
//    }

}
