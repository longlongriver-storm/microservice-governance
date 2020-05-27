/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
/**
 * ※ 日期的公共类部分
 */
public class DateUtil {
    /**
     * 日期正则表达式
     * regexandpattern_day_level:String[][]
     */
    public static final String[][] regexandpattern_day_level = {
        {"^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}","yyyy-MM-dd HH:mm"},
        {"^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}[.]?\\d{1,2}","yyyy-MM-dd HH:mm:ss"},
        {"\\d{8}", "yyyyMMdd"},
        {"\\d{6}", "yyMMdd"},
        {"^\\d{4}年\\d{1,2}月\\d{1,2}日$", "yyyy年MM月dd日"},
        {"^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd"},
        {"^[a-zA-Z]{3} [a-zA-Z]{3} \\d{1,2} \\d{1,2}:\\d{1,2}:d{1,2}[.]?\\d{1,2} [a-zA-Z]{3} \\d{4}$","EEE MMM dd hh:mm:ss zzz yyyy"},
        {"^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd"},
        {"^\\d{4}\\.\\d{1,2}\\.\\d{1,2}$", "yyyy.MM.dd"},
        {"^\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}[.]?\\d{1,2}$", "MM/dd/yyyy HH:mm:ss"},
        {"^\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}$", "MM/dd/yyyy HH:mm"},
        {"^\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}$", "MM/dd/yyyy HH"},
        {"^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy"}
    };
    /**
     * <pre>
     * Function： getString
     * 将日期转化为指定格式的字符串
     * @param date Date 时间对象
     * @param pattern String 时间格式，可以是年月日时分秒的任意有效组合，如yyyy-MM-dd HH:mm
     * 				   年：yyyy
     *                 月：MM
     *                 日：dd
     *                 时：HH（24小时制）或hh（12小时制）
     *                 分：mm
     *                 秒：ss
     * @return String 日期字符串
     * Modify：      添加date参数为null的判断 是则返回""
     * 修改日期 2007 11 2  修改者:龙金波
     * </pre>
     */
    public static String getString(Date date, String pattern) {
        return getString(date,pattern,Locale.getDefault());
    }
    public static String getString(Date date, String pattern,Locale locale) {
        if(date==null){
            return "";
        }
        DateFormat df = new SimpleDateFormat(pattern,locale);
        return df.format(date);
    }
    /**
     * 将日期转化为默认的yyyy-MM-dd格式的字符串
     * date为null则返回""
     * @param date Date 时间对象
     * @return String 日期字符串
     */
    public static String getString(Date date) {
        return getString(date, "yyyy-MM-dd");
    }
    public static int getSeason(Date date) {
    	Calendar c=Calendar.getInstance();
    	c.setTime(date);
    	int i = c.get(Calendar.MONTH);
        return i/3+1;
    }
    public static int getMonth(Date date) {
    	Calendar c=Calendar.getInstance();
    	c.setTime(date);
    	int i = c.get(Calendar.MONTH);
        return i+1;
    }
    /**
     * 将日期转化为默认的yyyy-MM-dd格式的字符串
     * date为null则返回""
     * @param locale Locale 时间对象
     * @return String 日期字符串
     */
    public static String getFullDateByLocale(Locale locale) {
        Date dt = new Date();
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);
        if ("ja".equals(locale.getLanguage())) {
            df = new SimpleDateFormat("yyyy年MM月dd日 EE曜日", locale);
            return df.format(dt);
        }
        return df.format(dt);
    }
    /**
     * 将日期字符串转换为对应格式的日期对象
     *
     * @param strDate
     *                String 日期字符串,如2007-02-14 14:25
     * @param format
     *                String 日期格式，与日期字符串的格式对应，如yyyy-MM-dd HH:mm 年：yyyy 月：MM 日：dd
     *                时：HH（24小时制）或hh（12小时制） 分：mm 秒：ss
     * @return Date 日期对象
     * @throws ParseException 
     */
    public static Date getDate(String strDate, String format)  {
        DateFormat df = new SimpleDateFormat(format,Locale.getDefault());
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            return getDate(strDate,format,Locale.US);
        }
    }
    public static Date getDate(String strDate, String format,Locale locale){
        DateFormat df = new SimpleDateFormat(format,locale);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Can't parse " + strDate +
                    " using " + format);
        }
    }

    
    /**
     * <pre>
     * Function： getDate
     * Description：  将格式为yyyy-MM-dd的日期字符串转换为日期对象
     * 现支持多种格式，没有匹配的格式返回null
     *   yyyy-MM-dd HH:mm
     *   yyyy-MM-dd HH:mm:ss
     *   EEE MMM dd hh:mm:ss zzz yyyy
     *   yyyyMMdd
     *   yyMMdd
     *   yyyy年MM月dd日
     *   yyyy-MM-dd
     *   yyyy/MM/dd
     *   dd/MM/yyyy
     *   yyyy.MM.dd
     *  @param strDate　日期字符串,如2007-02-14
     *  @return  Date 日期对象
     * Modify：      改为对多种定义格式进行解析，实现方式是利用正则表达式验证后再调用时间格式化函数
     * 修改日期 Oct 25, 2007  修改者:龙金波
     * </pre>
     */
    public static Date getDate(String strDate) {
        try{
            for(int i=0 ; i<regexandpattern_day_level.length ; i++){
                if(strDate.matches(regexandpattern_day_level[i][0])){
                    //System.out.println("i="+i);
                    return getDate(strDate,regexandpattern_day_level[i][1]);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * 获得两个日期对象之间相差的天数
     *
     * @param beginDater Date 开始日期
     * @param endDate Date 结束日期
     * @return long 相差天数，如果beginDate>endDate，返回负数
     */
    public static long getDays(Date beginDate, Date endDate) {
        long days = -1;
        long beginMillisecond = beginDate.getTime();
        long endMillisecond = endDate.getTime();
        long millisecondForDay = 24 * 60 * 60 * 1000;
        days = (long) ((endMillisecond - beginMillisecond) / millisecondForDay);
        return days;
    }
    
    /**
     * 在指定的日期加上或减去天数
     *
     * @param date Date 日期
     * @param day int 天数(正数-加;负数-减)
     * @return Date 修改后的日期
     */
    public static Date addDays(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }
    
    public static void main(String[] args) {
        System.err.println(getString(new Date(), "yyyy-MM-dd"));
        System.err.println(getString(new Date(), "yyyyMMdd"));
        System.err.println(getString(new Date(), "yyyy-MM-dd HH:mm"));
        System.err.println(getString(new Date(), "yyyy-MM-dd hh:mm"));
        System.err.println(getString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        System.err.println(getString(new Date()));
        
        System.err.println("========getDate(String,format)================================");
        System.err.println(getDate("2007-02-22", "yyyy-MM-dd"));
        System.err.println(getDate("20070222", "yyyyMMdd"));
        System.err.println(getDate("2007-02-22 15:23", "yyyy-MM-dd HH:mm"));
        System.err.println(getDate("2007-02-22 15:23:10", "yyyy-MM-dd HH:mm:ss"));
        System.err.println(getDate("2007-02-22 15:23:10.00", "yyyy-MM-dd HH:mm:ss"));
        System.err.println(getDate("2007-02-22 15:23:100", "yyyy-MM-dd HH:mm:ss"));
        System.err.println("===========================================");
        
        System.err.println(getDays(getDate("2007-02-23 20:23:50",
                "yyyy-MM-dd HH:mm:ss"),
                getDate("2037-02-22 15:23:10.0",
                "yyyy-MM-dd HH:mm:ss")));
        
	
	System.out.println(getString(new Date(), "EEE MMM dd hh:mm:ss zzz yyyy"));
	System.out.println(getDate(getString(new Date(), "EEE MMM dd hh:mm:ss zzz yyyy"), "EEE MMM dd hh:mm:ss zzz yyyy"));
//	DateFormat df = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy",Locale.US);
       
           // System.err.println("==="+df.parse(new Date().toString()));
            System.out.println("parse "+getDate(new Date().toString(), "EEE MMM dd hh:mm:ss zzz yyyy"));
	
	System.err.println("=========getDate(String)================================");
        System.err.println(getDate("2007-02-22 15:23:10.0"));
        System.err.println(getDate("Mon Jan 21 17:11:14 CST 2008"));
        System.err.println(getDate("2007-02-22 15:23:100"));
        System.err.println(getDate("2007-02-22 00:00:00.00"));
        System.err.println(getDate("2007-02-22 15:23"));
        System.err.println(getDate("2007-02-22"));
        System.err.println(getDate("04/30/2020 17:27:48"));
        System.err.println(getDate("04/30/2020 17:27"));
        System.err.println(getDate("04/30/2020 17"));
        System.err.println(getDate("04/30/2020"));
        System.err.println("===========================================");
        System.err.println(getString(addDays(new Date(), 10)));
    }
    
  
}
