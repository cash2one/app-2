package com.jumper.angel.frame.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class TimeUtils {

	/** 获取当前年份 **/
	public static int getCurrentYear(){
		Calendar current = Calendar.getInstance();
		return current.get(Calendar.YEAR);
	}
	/** 获取当前月份 **/
	public static int getCurrentMonth() {
		Calendar current = Calendar.getInstance();
		return current.get(Calendar.MONTH)+1;
	}
	/** 获取当前日期号 **/
	public static int getCurrentDay() {
		Calendar current = Calendar.getInstance();
		return current.get(Calendar.DAY_OF_MONTH);
	}
	
	public static Timestamp getCurrentTime() {
		/*Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");// 可以方便地修改日期格式
		Timestamp time = new Timestamp(new java.util.Date().getTime());*/
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}
 
	public static Timestamp getCurrentTime(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");// 可以方便地修改日期格式
		Timestamp time = null;
		try {
//			System.out.println((dateFormat.parse(date)).getTime());
			time = new Timestamp((dateFormat.parse(date)).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;
	}

	public static Timestamp getTimestampDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		format.setLenient(false);
		try {
			Timestamp ts = new Timestamp(format.parse(date).getTime());
			return ts;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Timestamp getTimestampDate(String date, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		format.setLenient(false);
		try {
			Timestamp ts = new Timestamp(format.parse(date).getTime());
			return ts;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 格式化日期  字符串转date
	 * @version 1.0
	 * @createTime 2016-11-30,下午1:58:23
	 * @updateTime 2016-11-30,下午1:58:23
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param data
	 * @return
	 */
	public static Date convertToDate(String data) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
		Date ret;
		try {
			ret = dateFormat.parse(data);
			return ret;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

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

	public static String formatStringDate(String date, String format) {
		String time = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			time = sdf.format(sdf.parse(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

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
	 * 几秒前，几分钟前，几小时前，几天前，几月前，几年前的java实现
	 */
	private static final long ONE_MINUTE = 60000L;  
    private static final long ONE_HOUR = 3600000L;  
    private static final long ONE_DAY = 86400000L;  
    private static final long ONE_WEEK = 604800000L;  
  
    private static final String ONE_SECOND_AGO = "秒前";  
    private static final String ONE_MINUTE_AGO = "分钟前";  
    private static final String ONE_HOUR_AGO = "小时前";  
    private static final String ONE_DAY_AGO = "天前";  
    private static final String ONE_MONTH_AGO = "月前";  
    private static final String ONE_YEAR_AGO = "年前";  
	public static String getTimeAgo(Date date){
		long delta = new Date().getTime() - date.getTime();  
        if (delta < 1L * ONE_MINUTE) {  
            long seconds = toSeconds(delta);  
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;  
        }  
        if (delta < 45L * ONE_MINUTE) {  
            long minutes = toMinutes(delta);  
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;  
        }  
        if (delta < 24L * ONE_HOUR) {  
            long hours = toHours(delta);  
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;  
        }  
        if (delta < 48L * ONE_HOUR) {  
            return "昨天";  
        }  
        if (delta < 30L * ONE_DAY) {  
            long days = toDays(delta);  
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;  
        }  
        if (delta < 12L * 4L * ONE_WEEK) {  
            long months = toMonths(delta);  
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;  
        } else {  
            long years = toYears(delta);  
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;  
        }  
	}
	private static long toSeconds(long date) {  
        return date / 1000L;  
    }  
  
    private static long toMinutes(long date) {  
        return toSeconds(date) / 60L;  
    }  
  
    private static long toHours(long date) {
        return toMinutes(date) / 60L;  
    }  
  
    private static long toDays(long date) {  
        return toHours(date) / 24L;  
    }  
  
    private static long toMonths(long date) { 
        return toDays(date) / 30L;  
    }  
  
    private static long toYears(long date) {  
        return toDays(date) / 365L;  
    }  
    
    //获取距离当前几年、几个月和几天
    public static long[] getBetweenTime(Date date){
    	 long delta = Math.abs(new Date().getTime() - date.getTime()); 
    	 long years = 0;
    	 long months = 0;
    	 long days = 0;
    	 long[] betweenTime = new long[3];
    	 if (delta >= 365 * ONE_DAY) {  
		     years = toYears(delta);  
		 }
    	 months = (toDays(delta) - 365 * years)/30;
		 days = toDays(delta) - 30 * months - 365 * years; 
		 betweenTime[0] = years;
		 betweenTime[1] = months;
		 betweenTime[2] = days;
		 return betweenTime;
    }

	/**
	 * 格式化时间 Locale是设置语言敏感操作
	 * 
	 * @param formatTime
	 * @return
	 */
	public static String getTimeStampNumberFormat(Timestamp formatTime) {
		SimpleDateFormat m_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				new Locale("zh", "cn"));
		return m_format.format(formatTime);
	}

	public static String getDateFormat(Date date) {
		SimpleDateFormat m_format = new SimpleDateFormat("yyyy-MM-dd",
				new Locale("zh", "cn"));
		return m_format.format(date);
	}

	public static boolean checkLimitTime(Timestamp formatTime1,
			Timestamp formatTime2, int limit_time) {
		SimpleDateFormat timeformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		long t1 = 0L;
		long t2 = 0L;
		try {
			t1 = timeformat.parse(getTimeStampNumberFormat(formatTime1))
					.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			t2 = timeformat.parse(getTimeStampNumberFormat(formatTime2))
					.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 因为t1-t2得到的是毫秒级,所以要初3600000得出小时.算天数或秒同理
		int hours = (int) ((t1 - t2) / 3600000);
		int minutes = (int) (((t1 - t2) / 1000 - hours * 3600) / 60);
		int second = (int) ((t1 - t2) / 1000 - hours * 3600 - minutes * 60);
		int sec = hours * 3600 + minutes * 60 + second;
		if (sec > limit_time)
			return true;
		return false;
	}

	/**
	 * 获取当前日期是星期几<br>
	 * 
	 * @param dt
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	/**
	 * 获取一个星期的第几天
	 * 
	 * @param dt
	 * @return
	 */
	public static int getWeekDays(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return w;
	}

	public static String getInterval(String createtime, String timeStr) { // 传入的时间格式必须类似于2012-8-21
		// 17:53:20这样的格式
		String interval = null;

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		ParsePosition pos = new ParsePosition(0);
		Date d1 = sd.parse(createtime, pos);

		// 用现在距离1970年的时间间隔new
		// Date().getTime()减去以前的时间距离1970年的时间间隔d1.getTime()得出的就是以前的时间与现在时间的时间间隔
		long time = new Date().getTime() - d1.getTime();// 得出的时间间隔是毫秒
		int hour = (int) (time / 3600000);
		if (time / 1000 < 10 && time / 1000 >= 0) {
			// 如果时间间隔小于10秒则显示“刚刚”time/10得出的时间间隔的单位是秒
			interval = "刚刚";
		} else if (hour < 24 && hour > 0) {
			// 如果时间间隔小于24小时则显示多少小时前
			int h = (int) (time / 3600000);// 得出的时间间隔的单位是小时
			interval = h + "小时前";

		} else if (time / 60000 < 60 && time / 60000 > 0) {
			// 如果时间间隔小于60分钟则显示多少分钟前
			int m = (int) ((time % 3600000) / 60000);// 得出的时间间隔的单位是分钟
			interval = m + "分钟前";

		} else if (time / 1000 < 60 && time / 1000 >= 0) {
			// 如果时间间隔小于60秒则显示多少秒前
			int se = (int) ((time % 60000) / 1000);
			interval = se + "秒前";

		} else {
			// 大于24小时，则显示正常的时间，但是不显示秒
			SimpleDateFormat sdf = new SimpleDateFormat(timeStr);
			ParsePosition pos2 = new ParsePosition(0);
			Date d2 = sdf.parse(createtime, pos2);
			interval = sdf.format(d2);
		}
		return interval;
	}

	public static String getInterval(String createtime) {
		return getInterval(createtime, "yyyy-MM-dd HH:mm");
	}

	/**
	 * 获取当天开始时间 ex:2015-03-03 00:00:00
	 * 
	 * @param day
	 *  当前时间往后多少天
	 * @return
	 */
	public static Date getCurrentStartTime(int day, Date date) {
		Calendar todayStart = Calendar.getInstance();
		if (date != null) {
			todayStart.setTime(date);
		}
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		todayStart.add(Calendar.DATE, day);
		return todayStart.getTime();
	}

	/**
	 * 获取当前结束时间 ex:2015-03-03 23:59:59
	 * 
	 * @param day
	 *            当前时间往后多少天
	 * @return
	 */
	public static Date getCurrentEndTime(int day, Date date) {
		Calendar todayStart = Calendar.getInstance();
		if (date != null) {
			todayStart.setTime(date);
		}
		todayStart.set(Calendar.HOUR_OF_DAY, 23);
		todayStart.set(Calendar.MINUTE, 59);
		todayStart.set(Calendar.SECOND, 59);
		todayStart.set(Calendar.MILLISECOND, 59);
		todayStart.add(Calendar.DATE, day);
		return todayStart.getTime();
	}

	/**
	 * 
	 * @param dateStr
	 *            默认 yyyy/MM/dd HH:mm
	 * @return
	 */
	public static boolean isValidDate(String dateStr, String fomart) {
		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat(fomart);
		try {
			format.setLenient(false);
			format.parse(dateStr);
		} catch (ParseException e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}

	/**
	 * 
	 * @param dateStr
	 * @param flag
	 *            0:"yyyy-MM-dd HH:mm:ss";1:"yyyy-MM-dd";2:"yyyy/MM/dd"
	 * @return
	 */
	public static boolean isValidDate(String dateStr, int flag) {
		if (flag == 0) {
			return isValidDate(dateStr, "yyyy-MM-dd HH:mm:ss");
		} else if (flag == 1) {
			return isValidDate(dateStr, "yyyy-MM-dd");
		} else if (flag == 2) {
			return isValidDate(dateStr, "yyyy/MM/dd");
		}
		return false;

	}

	public static String genericFormatDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(new Date());
	}

	/**
	 * 得到某年某月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {

		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));

		cal.set(Calendar.HOUR_OF_DAY, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		cal.set(Calendar.MILLISECOND, 00);

		return cal.getTime();
	}

	/**
	 * 得到某年某月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {

		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.set(Calendar.DAY_OF_MONTH, 1);

		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 59);

		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);

		return cal.getTime();

	}
	
	public static Timestamp getBefourOrAfterDay(int day){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, day);
		String strDate = converStringDate(c.getTime(), "yyyy-MM-dd HH:mm:ss");
		Timestamp time = getTimestampDate(strDate, "yyyy-MM-dd HH:mm:ss");
		return time;
	}
	
	public static String getBefourOrAfterHour(Timestamp time,int hour){
		long milliseconds = hour*60*60*1000;
		String strDate = converStringDate(new Date(time.getTime() + milliseconds), "yyyy/MM/dd HH:mm:ss");
		return strDate;
	}
	
	public static boolean compareTimeByHour(Timestamp time, int hour){
		if(time == null){
			return true;
		}
		Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.HOUR_OF_DAY, hour);
	    Timestamp compareTime = new Timestamp(calendar.getTimeInMillis());
	    if (time.after(compareTime)){
	    	return true;
	    };
	    return false;
	}
	
	public static boolean compareTime(Timestamp time, int day){
		Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.DAY_OF_MONTH, day);
	    Timestamp compareTime = new Timestamp(calendar.getTimeInMillis());
	    if (time.after(compareTime)){
	    	return true;
	    };
	    return false;
	}
	
	/**
	 * 获取当前时间推后多少天
	 * @param day
	 * @param date
	 * @return
	 */
	public static Date getCurrentTimeByDay(int day, Date date) {
		Calendar todayStart = Calendar.getInstance();
		if (date != null) {
			todayStart.setTime(date);
		}
		todayStart.add(Calendar.DATE, day);
		return todayStart.getTime();
	}
	/**
	 * 获取当前时间加分钟
	 * @param minute
	 * @return
	 */
	public static Date getCurrentTimeByMinute(int minute) {
		Calendar todayStart = Calendar.getInstance();
		todayStart.add(Calendar.MINUTE, minute);
		return todayStart.getTime();
	}
	/**
	 *	检测日期格式是否正确  
	 * @param time 日期 不包含时间 /和-都可以
	 * @return
	 */
	public static boolean checkTimeFormat(String time)
	{
		Pattern p;
		if(time.contains("-"))
		{
			p = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\-\\s]?((((0?" +"[13578])|(1[02]))[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))" +"|(((0?[469])|(11))[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|" +"(0?2[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12" +"35679])|([13579][01345789]))[\\-\\-\\s]?((((0?[13578])|(1[02]))" +"[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))" +"[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\-\\s]?((0?[" +"1-9])|(1[0-9])|(2[0-8]))))))"); 
		}else {
			p = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))"); 
			}
	 	return p.matcher(time).matches();
	}
	
	/**
	 * 获取当前时间往后推多少个小时
	 * @param hour
	 * @param date
	 * @param isOnTheHour 是否整点
	 * @return
	 */
	public static Date getCurrentEndTimeByHour(int hour, Date date, boolean isOnTheHour) {
		Calendar todayStart = Calendar.getInstance();
		if (date != null) {
			todayStart.setTime(date);
		}
		System.out.println(todayStart.get(Calendar.MINUTE));
		if(isOnTheHour){
			todayStart.set(Calendar.MINUTE, 59);
		}else{
			todayStart.set(Calendar.MINUTE, 29);
		}
		todayStart.set(Calendar.SECOND, 59);
		todayStart.set(Calendar.MILLISECOND, 999);
		todayStart.add(Calendar.HOUR_OF_DAY, hour);
		return todayStart.getTime();
	}
	
	public static Date calculateDayOfTime(Date date, Integer day){
		Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date); 
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
	}

	public static Date convertToDate(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}
	
	public static Date getBeforeDay(int day){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -day);
		return calendar.getTime();
	}
	/**
	 * 
	 * @param dt1
	 * @param dt2
	 * @return 1:dt1>dt2; -1:dt1<dt2
	 */
    public static int compare_date(Date dt1, Date dt2) {
        try {
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }


	public static void main(String[] args) throws ParseException {
		/*Date date = getCurrentEndTime(1, convertDate("2014-04-29", "yyyy-MM-dd"));
		System.out.println(converStringDate(date, "yyyy-MM-dd"));*/
		//System.out.println(converStringDate(getCurrentTimeByDay(1, new Date()), "yyyy-MM-dd HH:mm:ss"));
		//System.out.println(getTimeStampNumberFormat(getCurrentTime()));
		//System.out.println(converStringDate(getCurrentStartTime(-7, new Date()), "yyyy-MM-dd"));
	//	System.out.println(getTimeStampNumberFormat(getCurrentTime()));
//		System.out.println(convertToDate("2015-07-07"));
		
		/*long[] time = getBabyYearAndMonth(convertDate("2014-09-01", "yyyy-MM-dd"));
		System.out.println(time[0]+"---"+time[1]);*/
//		long date[]=getPregWeekDay("2016-2-28", "2015-12-23");
//		System.out.println(date[0]+"----"+date[1]);
//		Date date = calculateDayOfTime(new Date(), 1);
//		String strTime = converStringDate(date, "yyyy-MM-dd HH:mm:ss");
//		System.out.println(getLastWeekSunday());
		System.out.println(converStringDate(getCurrentStartTime(21, new Date()), "yyyy-MM-dd"));
//		String result = getFormatTime(56);
//		System.out.println(result);
	}
	/**
	 * 通过指定格式获得当前时间
	 * @param format
	 * @return
	 */
	public static Timestamp getCurrentTimeBy(String format) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				format);// 可以方便地修改日期格式
		String date=dateFormat.format(now);
		Timestamp time = null;
		//time = new Timestamp(now.getTime()).valueOf(date);
		try {
			time = new Timestamp((dateFormat.parse(date)).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	/** 获取上周周一 **/
	public static Date getLastWeekMonday() {
		Date a = getCurrentStartTime(-1,new Date());    
        Calendar cal = Calendar.getInstance();    
        cal.setTime(a);    
        cal.add(Calendar.WEEK_OF_YEAR, -1);// 一周    
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
		return cal.getTime();
	}
	/** 获取上周周日 **/
	public static Date getLastWeekSunday() {
		Date a = getCurrentStartTime(-1,new Date());    
        Calendar cal = Calendar.getInstance();    
        cal.setTime(a);
        cal.set(Calendar.DAY_OF_WEEK, 1);    
        return cal.getTime();   
	}
	/** 获取上个月第一天 **/
	public static Date getFirstDayOfLastMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.MILLISECOND, 00);
		return calendar.getTime();
	}
	/** 获取上个月最后一天 **/
	public static Date getLastDayOfLastMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1); 
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.MILLISECOND, 00);
		return calendar.getTime();
	}
	public static Date getBeforeDayByDate(int day, Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -day);
		return calendar.getTime();
	}
	/**通过测试时间计算孕周天
	 * @throws ParseException */
	public static int[] getPregWeekDay(Date expectDate,Date testDate)
	{
		int date[]=new int[2];
		/**先算出天数差值*/
		int days = daysBetween(testDate, expectDate);
		date[0]=(280-days)/7;
		date[1]=(280-days)%7;
		return date;
	}
	  /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate)    
    {   try {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
       return Integer.parseInt(String.valueOf(between_days));       
    } catch (Exception e) {
    	e.printStackTrace();
    	return 0;
	}
    }    
      
	/** 
	*字符串的日期格式的计算 
	*/  
    public static int daysBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));     
    }
    
    /** 获取当前unix时间戳 **/
	public static Long getCurrentUnixTimestamp() {
		return System.currentTimeMillis()/1000;
	}
	
	/** 时长格式化为 xx小时xx分xx秒 **/
	public static String getFormatTime(int time) {
		/*long longTime = Long.valueOf(time*1000);
		long hour = toHours(longTime);
		long totalMinute = toMinutes(longTime);
		long minute = totalMinute -hour*60;
		long second = toSeconds(longTime)-totalMinute*60;
		if(hour != 0){
			return String.format("%d小时%d分%d秒", hour,minute,second);
		}else if(minute != 0){
			return String.format("%d分%d秒", minute,second);
		}else{
			return String.format("%d秒", second);
		}*/
		long nh = 60 * 60;
	    long nm = 60;
	    long ns = 1;
	    long hour = time / nh;
	    long min = time % nh / nm;
	    long sec = time % nh % nm / ns;
		return String.format("%d小时%d分%d秒", new Object[]{hour, min, sec});
	}
	
	/**
	 * 时长格式化为 xx小时xx分xx秒
	 * @param second
	 * @return
	 */
	public static String getFormatTimes(int second) {
    	int h = 0;
    	int d = 0;
    	int s = 0;
    	int temp = second % 3600;
    	if(second > 3600) {
    		h= second / 3600;
    		if(temp != 0) {
    			if(temp>60){
	        		 d = temp/60;
	        		 if(temp%60!=0){
	        			 s = temp%60;
	        		 }
	        	 } else {
	        		 s = temp;
	        	 }
    		}
    	} else {
    		d = second/60;
 	        if(second%60!=0){
 	        	s = second%60;
 	        }
    	}
    	if(h == 0) {
    		return d+"分"+s+"秒";
    	} else if(h == 0 && d == 0) {
    		return s+"秒";
    	}
    	 return h+"小时"+d+"分"+s+"秒";
    }
	
	/** 获取时分秒毫秒为零的日期 **/
	public static Date getDateWithoutTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.MILLISECOND, 00);
		return calendar.getTime();
	}
	/** 获取某年某月的日期范围 2016/04/01-2014/04/30 **/
	public static String getRangDateOfMonth(Integer year,Integer month){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Calendar cal = Calendar.getInstance();
	        cal.set(Calendar.YEAR,year);
	        cal.set(Calendar.MONTH, month-1);
	        //获取某月最大天数
	        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        //设置日历中月份的最大天数
	        cal.set(Calendar.DAY_OF_MONTH, lastDay);
	        //格式化日期
	        
	        String lastDayOfMonth = sdf.format(cal.getTime());
	        cal.set(Calendar.DAY_OF_MONTH, 1);
	        String firstDayOfMonth = sdf.format(cal.getTime());
	        String rangDate = firstDayOfMonth+"-"+lastDayOfMonth;
	        return rangDate;
		} catch (Exception e) {
			e.printStackTrace();
	    	return null;
		}
        
    }
	/** 获取某年某月的日期 **/
	public static String getDateFormatByNum(Integer year,Integer month,Integer day){
		Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String time = sdf.format(cal.getTime());
        return time;
	}
	
	
	/**解析成时间戳**/
	public static Long parseDate(String date, String strFormat)
	{
		if (date == null)
			return null;

		if (strFormat == null)
			strFormat = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
		Date newDate = null;

		try
		{

			sdf.parse(date);
			newDate = sdf.parse(date);
		}
		catch (ParseException pe)
		{
			newDate = null;
		}
		return newDate.getTime() / 1000;
	}
	
	/**** 当前时间时间戳 *****/
	
	
	public static Long getNowTime()
	{
		return (System.currentTimeMillis() / 1000);
	}
	
}
