package com.jumper.angel.frame.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class IntervalUtils {

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
	 * 
	 * @param date1
	 *            日期1
	 * @param date2
	 *            日期2
	 * @return 比较两个日期的大小,传入的日期格式必须类似于2012-8-21这样的格式 返回1为date1>date2
	 *         返回0为date1=date2 返回-1为date1<date2
	 * @throws ParseException
	 */

	public static int getcomparedate(String date1, String date2) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		int flag = 0;
		Date dt1 = df.parse(date1);
		Date dt2 = df.parse(date2);
		if (dt1.getTime() > dt2.getTime()) {
			flag = 1;
		}
		if (dt1.getTime() < dt2.getTime()) {
			flag = -1;
		}
		if (dt1.getTime() == dt2.getTime()) {
			flag = 0;
		}
		return flag;
	}

	/**
	 * 
	 * @param date1
	 * @param date2
	 * @return返回日期相隔的天数
	 * @throws ParseException
	 */
	public static long getcomparedatedays(String date1, String date2) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dt1 = df.parse(date1);
		Date dt2 = df.parse(date2);
		// System.out.println(dt2.getTime() + "     " +dt1.getTime());
		return (Math.abs(dt2.getTime() - dt1.getTime())) / (3600 * 1000 * 24);

	}

	/**
	 * 
	 * @param date1
	 * @param date2
	 * @return返回日期相隔的天数
	 * @throws ParseException
	 */
	public static long getcomparedatedays(Date date1, Date date2) throws ParseException {
		// System.out.println(date2.getTime() + "     " +date1.getTime());
		return (Math.abs(date2.getTime() - date1.getTime())) / (3600 * 1000 * 24);
	}

	/**
	 * 计算怀孕了几周 、孕周计算
	 * 
	 * @param date
	 *            预产期
	 * @return 数组 [周，天，离预产期的天数]
	 * @throws ParseException
	 */
	public static int[] getPregnantWeek(Date date) throws ParseException {
		int[] data = new int[3];
	    Date nowDate = new Date();
	    if(nowDate.after(date)){
	    	return new int[] {39,6,280};
	    }
	    long days = getcomparedatedays(TimeUtils.converStringDate(date, "yyyy-MM-dd"), TimeUtils.converStringDate(nowDate, "yyyy-MM-dd"));
	    if (days > 280){
	    	return new int[] { 0, 0, 0 };
	    }
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
	 * 计算宝宝出生时长
	 * 
	 * @param date
	 *            宝宝生日
	 * @return 数组 [出生所在周(从1开始)，天,出生总天数]
	 * @throws ParseException
	 */
	public static int[] getBabyBirthWeek(Date date) throws ParseException {
		int[] data = new int[3];
		Date now = new Date();
		if (date.after(now)) {
			return new int[] { 0, 0, 0 };
		}
		int days = (int) getcomparedatedays(TimeUtils.converStringDate(date, "yyyy-MM-dd 00:00:00"), TimeUtils.converStringDate(now, "yyyy-MM-dd 00:00:00"));
		int week = days / 7;
		data[0] = week;
		data[1] = days - week * 7;
		data[2] = days;
		/** 婴儿超过156周(三岁)岁当作(156周) **/
		if(data[0] > 156){
			data[0] = 156;
			data[1] = 3;
		}
		return data;
	}

	/**
	 * 计算宝宝几岁几个月
	 * 
	 * @param date
	 *            宝宝生日
	 * @return 数组 [岁，月]
	 * @throws ParseException
	 */
	private static final long ONE_DAY = 86400000L;  
	public static int[] getBabyAgeAndMonth(Date birthday) {
		long delta = Math.abs(new Date().getTime() - birthday.getTime()); 
   	 	long years = 0;
   	 	long months = 0;
   	 	int[] ageAndMonth = new int[2];
	   	if (delta >= 365 * ONE_DAY) {
		    years = delta / (365 * ONE_DAY);
		}
	   	months = (delta/ONE_DAY - 365 * years)/30;
	   	ageAndMonth[0] = (int)years;
	   	ageAndMonth[1] = (int)months;
	   	return ageAndMonth;
	   	
	}

	public static String getDateString(Timestamp time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(time);
	}

	/** 操作时间距离预产期周数 **/
	public static int getPregnantWeek(Date pregDate, Date addDate) {
		/** 返回值控制在 0-39 范围内 **/
		try {
			int data;
			if (addDate.after(pregDate)) {// 添加时间超过预产期
				return 39;
			}
			long days = getcomparedatedays(TimeUtils.converStringDate(pregDate, "yyyy-MM-dd"), TimeUtils.converStringDate(addDate, "yyyy-MM-dd"));
			if (days > 40 * 7) { // 添加时间距离预产期超过280天
				return 0;
			}
			int week = (int)(days / 7L);
		    int day = (int)((week + 1) * 7 - days);
		    if(day == 7){
		    	day = 0;
		    	week-=1;
		    }
		    data = (39 - week);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/** 操作时间距离宝宝生日周数 **/
	public static int getBabyBirthWeek(Date birthDate, Date addDate) {
		/** 返回值控制0-156 **/
		try {
			if (birthDate.after(addDate)) {
				return 0;
			}
			int days = (int) getcomparedatedays(TimeUtils.converStringDate(birthDate, "yyyy-MM-dd HH:mm:ss"), TimeUtils.converStringDate(addDate, "yyyy-MM-dd 00:00:00"));
			int week = days / 7;
			if(week>156){
				week = 156;
			}
			return week;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;	
	}
	
	/**
	 * 根据末次月经时间计算预产期
	 * @param lastPeriod 末次月经时间
	 * @return 
	 */
	public static Date getPregancyDay(String lastPeriod){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = sdf.parse(lastPeriod);
			Calendar Calendarborn = Calendar.getInstance();
			Calendarborn.setTime(d1);
			Calendarborn.add(Calendar.DAY_OF_MONTH, 279);
			int bornyear = Calendarborn.get(Calendar.YEAR);
			int bornmonth = 1 + Calendarborn.get(Calendar.MONTH);
			int bornday = Calendarborn.get(Calendar.DAY_OF_MONTH);
			
			String borndayString = (String.valueOf(bornyear) + "-" + String.valueOf(bornmonth) + "-" + String.valueOf(bornday));
			return TimeUtils.convertDate(borndayString, "yyyy-MM-dd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据预产期计算末次月经时间
	 * @param lastPeriod 末次月经时间
	 * @return 
	 */
	public static Date getLastPeriodDay(String pregancyDay){
		Date date = TimeUtils.convertToDate(pregancyDay);
		Calendar todayStart = Calendar.getInstance();
		if (date != null) {
			todayStart.setTime(date);
		}
		todayStart.add(Calendar.DATE, -279);
		return todayStart.getTime();
	}
	
	public static long daysBetween(Date smdate, Date bdate) throws ParseException {  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		smdate = sdf.parse(sdf.format(smdate));  
		bdate = sdf.parse(sdf.format(bdate));  
		Calendar cal = Calendar.getInstance();  
		cal.setTime(smdate);  
		long time1 = cal.getTimeInMillis();  
		cal.setTime(bdate);  
		long time2 = cal.getTimeInMillis();  
		long between_days = (time2 - time1) / (1000 * 3600 * 24);  
		return between_days;  
	} 

	public static void main(String[] args) {
		try {
			/*Date date1 = getPregancyDay("2015-7-3");
			int[] p = getPregnantWeek(date1);
			System.out.println(TimeUtils.converStringDate(date1, "yyyy-MM-dd")+"---"+p[0]+"---"+p[1]);
			Date date = getLastPeriodDay("2017-07-11");
			System.out.println(TimeUtils.converStringDate(date, "yyyy-MM-dd"));
			Date date2 = DateUtil.addDate(date, 126);
			System.out.println(TimeUtils.converStringDate(date2, "yyyy-MM-dd"));
			Date date3 = DateUtil.addDate(date2, 6);
			System.out.println(TimeUtils.converStringDate(date3, "yyyy-MM-dd"));
//			Date date4 = DateUtil.addDate(date, 280);
//			System.out.println(TimeUtils.converStringDate(date4, "yyyy-MM-dd"));
			System.out.println(DateUtil.getPregnantWeek(TimeUtils.convertToDate("2017-02-09"), new Date())[0]+"周"+DateUtil.getPregnantWeek(TimeUtils.convertToDate("2017-02-09"), new Date())[1]+"天");
			
			System.out.println(TimeUtils.converStringDate(DateUtil.addDate(TimeUtils.convertDate("2017-07-11", "yyyy-MM-dd"), -155), "yyyy-MM-dd"));*/
			
			System.out.println(TimeUtils.converStringDate(getLastPeriodDay("2017-11-16"), "yyyy-MM-dd"));
			int week[] = DateUtil.getPregnantWeek(TimeUtils.convertToDate("2017-02-10"), TimeUtils.convertToDate("2017-02-10"));
			System.out.println(week[0]+"周"+week[1]+"天");
			
			Date dates = DateUtils.addDays(TimeUtils.convertDate("2017-11-16", "yyyy-MM-dd"), 0*7-279);
			System.out.println(TimeUtils.converStringDate(dates, "yyyy-MM-dd"));

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
