package com.wmeup.util.date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @brief 时间类
 *
 *        类详细说明
 * 
 * @author - 2016年4月18日 张基闯 创建初始版本
 *
 */
/**
 * @brief 类简要说明
 *
 * 类详细说明
 * 
 * @author
 *    - 2016年4月18日  张基闯  创建初始版本
 *
 */

/**
 * @brief 类简要说明
 *
 *        类详细说明
 * 
 * @author - 2016年4月18日 张基闯 创建初始版本
 *
 */
public class DateUtil {
	/** 日志 */
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DEFAULTDATEFORMAT = "yyyyMMdd";

	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
	public static final String DEFAULT_HAOMIAO_FORMAT = "YYYY-MM-dd HH:mm:ss.SSS";

	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm";

	public static final String DEFAULT_MONTHDAY_FORMAT = "MM-dd HH:mm";

	public static final String DEFAULT_DATETIME_FORMAT_SEC = "yyyy-MM-dd HH:mm:ss";

	public static final String DEFAULT_TIMESTAMP_FORMAT_SEC = "yyyyMMddHHmmssSSS";

	public static final String DEFAULT_DATE_FORMAT_YYYYMMDDHH = "yyyyMMddHH";

	public static final String DEFAULT_DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/**
	 * 
	 * 
	 */
	public static final String NOWTIME = "HHmmss";
	/**
	 * 年月日格式
	 */
	public static final String YEARMONTHDAY_FORMAT = "yyyyMMdd";

	/**
	 * 年月日格式
	 */
	public static final String YEAR_DATETIME_FORMAT = "yyyy年MM月dd日";

	/**
	 * 年月日格式
	 */
	public static final String YEAR_DOUBIGESHI_FORMAT = "yyyy年MM月dd日 HH:mm";

	public static Date today() {
		return formatDate(new Date(), DEFAULT_DATE_FORMAT);
	}
	
	public static String strigToday() {
		return dateToString(new Date(), DEFAULTDATEFORMAT);
	}

	public static String now() {
		return dateToString(new Date(), DEFAULT_DATETIME_FORMAT_SEC);
	}

	public static String nowYdm() {
		return dateToString(new Date(), YEARMONTHDAY_FORMAT);
	}

	public static String getTimeStamp() {
		return dateToString(new Date(), DEFAULT_TIMESTAMP_FORMAT_SEC);
	}
	
	/**
	 * 
	 * @brief 获取6位时分秒
	 *
	 * @author
	 *    - 2016年5月13日	 刘小强  创建初始版本
	 *
	 * @return
	 */
	public static String getNowTime(){
		Date time = new Date();
		SimpleDateFormat dateFormater = new SimpleDateFormat(NOWTIME);
		return dateFormater.format(time);
		
	}
	/**
	 * @brief 字符串与日期的转换
	 *
	 * @author - 2016年4月18日 张基闯 创建初始版本
	 *
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static Date formatDate(Date date, String formatStr) {
		Date ret = null;
		SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
		String dateStr = formatter.format(date);
		try {
			ret = formatter.parse(dateStr);
		} catch (ParseException e) {
			logger.error( "String与date转日期错误 ParseException="+ e.getMessage());
		}
		return ret;
	}

	/**
	 * @brief 日期转字符串
	 *
	 * @author - 2016年4月18日 张基闯 创建初始版本
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		String ret = "";
		if (date == null) {
			return ret;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		ret = formatter.format(date);
		return ret;
	}

	/**
	 * @brief str日期类型转DATE
	 *
	 * @author - 2016年4月18日 张基闯 创建初始版本
	 *
	 * @param dateStr
	 * @return
	 */
	public static Date strToDate(String dateStr) {
		if (dateStr == null || "".equals(dateStr.trim())) {
			return null;
		}
		try {
			return strToDate(dateStr, DEFAULT_DATE_FORMAT);
		} catch (Exception e) {
			logger.error("String与date转日期错误 Exception =", e.getMessage());
		}
		return null;
	}

	/**
	 * @brief 字符串转日期 自定义格式
	 *
	 * @author - 2016年4月18日 张基闯 创建初始版本
	 *
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date strToDate(String dateStr, String format) {
		Date ret = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			ret = formatter.parse(dateStr);
		} catch (ParseException e) {
			logger.error( "String与date转日期错误 [%s]", e.getMessage());
		}
		return ret;
	}

	public static Date addDays(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, day);
		Date newDate = c.getTime();
		return newDate;
	}

	public static Date addMonths(Date date, int month) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, month);
		Date newDate = c.getTime();
		return newDate;
	}

	public static Date addYears(Date date, int year) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, year);
		Date newDate = c.getTime();
		return newDate;
	}

	public static Date addss(Date date, int ss) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, ss);
		Date newDate = c.getTime();
		return newDate;
	}

	public static int getDistanceDays(Date before, Date after) {
		long diff = after.getTime() - before.getTime();
		return Long.valueOf(diff / (1000 * 60 * 60 * 24)).intValue();
	}

	public static int getNowHourOfDay() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public static int getNowDayOfMonth() {
		return getDaysOfMonth(new Date());
	}

	public static int getDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public static int getNowMonth() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MONTH) + 1;
	}

	public static int getNowYear() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}

	public static String getSimpleTime(Date beginDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.format(beginDate);
	}

	public static String getTimeBegin(int day, Date nowDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.add(Calendar.DATE, day);
		String result = format.format(calendar.getTime());
		return result;
	}

	public static String getTimeEnd(int day, Date nowDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.add(Calendar.DATE, day);
		String result = format.format(calendar.getTime());
		return result;
	}

	public static int getDaysOfMonth(Date now) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(now);
		return calender.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static Date getMonth(int differ) {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH, differ);
		Date result = DateUtil.formatDate(date.getTime(), "yyyy-MM-dd");
		return result;
	}

	public static Date getYear(int differ) {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.YEAR, differ);
		Date result = DateUtil.formatDate(date.getTime(), "yyyy-MM-dd HH:mm:ss");
		return result;
	}

	public static Date integerToDate(Integer time) {
		if (time == null) {
			return null;
		}
		long longTime = ((long) time) * 1000;
		return new Date(longTime);
	}

	public static int getMonthNum(Date start, Date end) {
		Date index = start;
		int count = 0;
		while (true) {
			if (DateUtil.addMonths(index, 1).compareTo(DateUtil.addDays(end, 1)) < 0) {
				index = DateUtil.addMonths(index, 1);
				count++;
			} else {
				break;
			}
		}
		return count;
	}

	/**
	 * @brief 获取当前系统时间
	 *
	 * @author - 2016年4月8日 张基闯 创建初始版本
	 *
	 * @return
	 */
	public static Date getSystemDate() {
		return new Date();
	}

	/**
	 * 将日期转化为日期字符串。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param parttern
	 *            日期格式
	 * @author - 2016年4月8日 张基闯 创建初始版本
	 *
	 * @return 日期字符串
	 */
//	public static String DateToString(Date date, String parttern) {
//		String dates = "";
//		if (date != null) {
//			try {
//				dates = getDateFormat(parttern).format(date);
//			} catch (Exception e) {
//			}
//		}
//		return dates;
//	}

	/**
	 * 获取SimpleDateFormat
	 * 
	 * @param parttern
	 *            日期格式
	 * 
	 * @author - 2016年4月8日 张基闯 创建初始版本
	 * 
	 * @return SimpleDateFormat 对象
	 * @throws RuntimeException
	 *             异常：非法日期格式
	 */
	private static SimpleDateFormat getDateFormat(String parttern) throws RuntimeException {
		return new SimpleDateFormat(parttern);
	}
	
	public static long differSecondsCurrentTime(String str) {
		long currentTime = System.currentTimeMillis();
		long tempTime = strToDate(str).getTime();
		return Math.abs(currentTime - tempTime)/1000;
	}
	/**
	 * 
	 * @brief 方法功能简要说明
	 *		获取时间字符串表示
	 * @author
	 *    - 2016年4月12日  张兴  创建初始版本
	 *
	 * @return
	 */
	public static String getNowDateStr(){
		Date time = new Date();
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		return dateFormater.format(time);
	}

	 /**
	  * 
	  * @brief 方法功能简要说明
	  *			将指定格式的字符串（yyyy-MM-dd H:m:s）字符串转换为Date类型
	  * @author
	  *    - 2016年4月12日  张兴  创建初始版本
	  *
	  * @param dateStr
	  * 		时间字符串
	  * @return
	  * @throws ParseException
	  */
	public static Date getDate(String dateStr) throws ParseException {
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		if(dateStr==null)
			dateStr = "2000-01-01 00:00:00";
		return dateFormater.parse(dateStr);
	}
	/*
		判断2个日期之间是否大于一个月
	 */
	public static boolean boolTwoDateDifferOneMonth(String firstDate, String secondDate){
		boolean result = false;
		Date beginDate;
		Date endDate;
		SimpleDateFormat dateFormater = new SimpleDateFormat(DEFAULTDATEFORMAT);
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(dateFormater.parse(firstDate));
			c.add(Calendar.MONTH, 1);
			beginDate = c.getTime();
			endDate = dateFormater.parse(secondDate);
			if(endDate.compareTo(beginDate)>0){
				result =  true;
			}else {
				result =  false;
			}
		} catch (ParseException e) {
			logger.error( "String与date转日期错误", e);
		}
		return result;
	}

}
