package com.jumper.angel.frame.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.jumper.angel.frame.common.PregnantStage;

/**
 * 日期工具类
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-10-12
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class DateUtil {
	
	private final static Logger logger = Logger.getLogger(DateUtil.class);
	
	public static final String DATE_FORMAT_PATTERN_1 = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
	
	/**
	 * 日期相加
	 * @version 1.0
	 * @createTime 2016-10-12,下午5:11:00
	 * @updateTime 2016-10-12,下午5:11:00
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param date 时间格式：yyyy-MM-dd HH:mm:ss
	 * @param day 天数
	 * @return
	 */
    public static Date addDate(Date date, int day) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
        return c.getTime();
    }
    
    public static long getMillis(Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }
    
    public static String getTimeByHour(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }
    
    /**
     * Date格式化成字符串
     * @version 1.0
     * @createTime 2016-10-14,上午11:51:16
     * @updateTime 2016-10-14,上午11:51:16
     * @createAuthor fangxilin
     * @updateAuthor
     * @updateInfo (此处输入修改内容,若无修改可不写.)
     * @param date
     * @param format
     * @return
     */
    public static String converStringDate(Date date, String format) {
		if(date == null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
    /**
     * 字符串转换成Date
     * @version 1.0
     * @createTime 2016-10-19,上午10:16:13
     * @updateTime 2016-10-19,上午10:16:13
     * @createAuthor fangxilin
     * @updateAuthor
     * @updateInfo (此处输入修改内容,若无修改可不写.)
     * @param data
     * @param format
     * @return
     */
    public static Date convertDate(String data, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date langDate;
		try {
			langDate = sdf.parse(data);
			return langDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
    /**
     * 日期相减得到天数
     * @version 1.0
     * @createTime 2016-10-17,下午4:53:07
     * @updateTime 2016-10-17,下午4:53:07
     * @createAuthor qinxiaowei
     * @updateAuthor
     * @updateInfo (此处输入修改内容,若无修改可不写.)
     * @param beginDateStr 起始时间
     * @param endDateStr 结束时间
     * @return
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
    	long day=0;
    	if(StringUtils.isEmpty(beginDateStr) || StringUtils.isEmpty(endDateStr)){
    		return day;
    	}
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");    
        java.util.Date beginDate;
        java.util.Date endDate;
        try {
            beginDate = format.parse(beginDateStr);
            endDate= format.parse(endDateStr);    
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);    
            //System.out.println("相隔的天数="+day);   
        } catch (ParseException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }   
        return day;
    }
    
    public static void main(String[] args) {
    	try {
//			System.out.println(getDaySub("2017-02-09","2017-11-16"));
			int week[] = getPregnantWeek(TimeUtils.convertDate("2016-09-29", "yyyy-MM-dd"), TimeUtils.convertDate("2016-04-08", "yyyy-MM-dd"));
			System.out.println(week[0]+"周"+week[1]+"天"+" 总天数："+week[2]);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
    
    /**
	 * 计算怀孕了几周 、孕周计算
	 * 
	 * @param date
	 *            预产期
	 * @return 数组 [周，天，离预产期的天数]
	 * @throws ParseException
	 */
	public static int[] getPregnantWeek(Date date, Date nowDate) throws ParseException {
		int[] data = new int[3];
	    //传递过来的时间大于预产期
	    if(nowDate.after(date)){
	    	return new int[] {40, 0, 280};
	    }
	    long days = getDaySub(TimeUtils.converStringDate(nowDate, "yyyy-MM-dd"), TimeUtils.converStringDate(date, "yyyy-MM-dd"));
	    //long days = getcomparedatedays(TimeUtils.converStringDate(date, "yyyy-MM-dd HH:mm:ss"), TimeUtils.converStringDate(nowDate, "yyyy-MM-dd 00:00:00"));
	    if (days > 280){
	    	return new int[] { 0, 0, 0 };
	    }
	    //days = days-1;
	    int week = (int)(days / 7L);
	    int day = (int)((week + 1) * 7 - days);
	    if(day == 7){
	    	day = 0;
	    	week-=1;
	    }
	    data[0] = (39 - week);
	    data[1] = day;
	    data[2] = ((int)days);
	    return data;
	}
	
	/**
	 * v4.1.5计算怀孕了几周 、孕周计算
	 * @version v.1.0
	 * @createTime 2017年5月12日,下午6:00:49
	 * @updateTime 2017年5月12日,下午6:00:49
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param date 预产期
	 * @param nowDate app端传递过来的时间
	 * @return
	 * @throws ParseException
	 */
	public static int[] getPregnantWeek_415(Date date, Date nowDate) throws ParseException {
		int[] data = new int[3];
		//预产期+21
		date = TimeUtils.getCurrentStartTime(21, date);
	    //传递过来的时间大于预产期
	    if(nowDate.after(date)){
	    	return new int[] {43, 0, 301};
	    }
	    long days = getDaySub(TimeUtils.converStringDate(nowDate, "yyyy-MM-dd"), TimeUtils.converStringDate(date, "yyyy-MM-dd"));
	    if (days > 301){
	    	return new int[] {0, 0, 0};
	    }
	    int week = (int)(days / 7L);
	    int day = (int)((week + 1) * 7 - days);
	    if(day == 7){
	    	day = 0;
	    	week -= 1;
	    }
	    data[0] = (42 - week);
	    data[1] = day;
	    //距预产期的天数，由于要显示43周数据所以该天数要减21天
	    data[2] = ((int)days - 21);
	    return data;
	}
	
	/**
	 * 显示时间，如果与当前时间差别小于一天，则自动用**秒(分，小时)前，如果大于一天则用format规定的格式显示
	 * @version 1.0
	 * @createTime 2016-12-5,下午4:32:46
	 * @updateTime 2016-12-5,下午4:32:46
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param ctime 时间
	 * @param format 格式
	 * @return
	 */
	public static String showTime(Date ctime, String format) {
		String r = "";
		if(ctime==null)return r;
		if(format==null)format="yyyy-MM-dd HH:mm";
	
		long nowtimelong = System.currentTimeMillis();
		long ctimelong = ctime.getTime();
		long result = Math.abs(nowtimelong - ctimelong);
		//一分钟内
		if (result < 60000) {
			long seconds = result / 1000;
			r = seconds + "秒钟前";
		} else if (result >= 60000 && result < 3600000) {// 一小时内
			long seconds = result / 60000;
			r = seconds + "分钟前";
		} else if (result >= 3600000 && result < 86400000) {// 一天内
			long seconds = result / 3600000;
			r = seconds + "小时前";
		} else {// 日期格式
			r = new SimpleDateFormat(format).format(ctime);
		}
		return r;
	}

	/**
	 * 时间戳类型格式化
	 * @param dateTime 时间戳
	 * @param format 格式
	 * @return
	 */
	public static String longToString(long dateTime, String format){
		try {
			SimpleDateFormat sdf=new SimpleDateFormat(format);
			return sdf.format(dateTime);
		} catch (Exception e) {
			logger.info("时间戳类型格式化 Msg = "+e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 计算当前周属于哪个阶段
	 * @param type 当前用户角色类型 0：怀孕中，1：已有宝宝
	 * @param week 当前孕周
	 * @return int PregnantStage中的孕期阶段
	 */
	public static int getPregnantStageByWeek(int type, int week){
		/** 怀孕中 **/
		if(type == 0){
			if(week >= PregnantStage.getObject(1).getWeek()[0] && week <= PregnantStage.getObject(1).getWeek()[1]){
				return 1;
			}else if(week >= PregnantStage.getObject(2).getWeek()[0] && week <= PregnantStage.getObject(2).getWeek()[1]){
				return 2;
			}else{
				return 3;
			}
		}else{
			if(week >= PregnantStage.getObject(4).getWeek()[0] && week <= PregnantStage.getObject(4).getWeek()[1]){
				return 4;
			}else if(week >= PregnantStage.getObject(5).getWeek()[0] && week <= PregnantStage.getObject(5).getWeek()[1]){
				return 5;
			}else{
				return 6;
			}
		}
	}
}
