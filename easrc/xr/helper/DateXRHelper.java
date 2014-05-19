package com.kingdee.eas.xr.helper;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.xr.XRSQLFacadeFactory;
import com.kingdee.util.DateTimeUtils;

public class DateXRHelper {
	public final static int[] SEASON = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
	/**
	 * 设置日历字段的偏移量的值
	 * @param date 日历
	 * @param field 日历字段
	 * @param amount 为字段添加的日期或时间量
	 * @return 返回偏移的日历日期
	 */
	public static Date offsetDate(Date date, int field, int amount) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		
		return cal.getTime();
	}
	
	/**
	 * 将年度转换成日历时间 如 2008 ＝》2008-01-01
	 * @param year 年度日历字段
	 * @return
	 */
	public static Date yearToDate(int year) {
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, 0, 1);
        
		return cal.getTime();
	}
	
	/**
	 * 将年度转换日历时间并且是年度的最后日期的时间点 
	 * 如 2008 ＝》2008年12月31日23时59分59秒59毫秒...
	 * @param year
	 * @return
	 */
	public static Date yearDateTo(int year) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(yearToDate(year));
		cal.add(Calendar.YEAR, 1);//	加1年
		cal.add(Calendar.MILLISECOND, -1);//	减1毫秒
		
		return cal.getTime();
	}
	
	/**
	 * 设置日历时间这一天最后的时间点 如某年某月某日23时59分59秒59毫秒...
	 * @param date
	 * @return
	 */
	public static Date DateTo(Date date) {
		Calendar cal = Calendar.getInstance();
		DateTimeUtils.truncateDate(date);
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);//	加1天
		cal.add(Calendar.MILLISECOND, -1);//	减1毫秒
		cal.getTime();
		return cal.getTime();
	}
	/**
	 * 上一日
	 * @param date
	 * @return
	 */
	public static Date lastDate(Date date) {
		Calendar cal = Calendar.getInstance();
		DateTimeUtils.truncateDate(date);
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);//	加1天
		cal.add(Calendar.MILLISECOND, -1);//	减1毫秒
		cal.getTime();
		return cal.getTime();
	}
	/**
	 *给时间年增量计算
	 * @param date 
	 * @return
	 */
	public static Date addYear(Date date, int i)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int j = calendar.get(1);
        calendar.set(1, i + j);
        return calendar.getTime();
    }
	/**
     * 给时间月份增量计算
     * @param date 时间
     * @param i	增量值
     * @return
     */
    public static Date addMonth(Date date, int i)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int j = calendar.get(2);
        j += i;
        int k = j / 12;
        j %= 12;
        calendar.set(2, j);
        if(k != 0)
        {
            int l = calendar.get(1);
            calendar.set(1, k + l);
        }
        return calendar.getTime();
    }
    /**
     * 给时间小时增量计算
     * @param date 时间
     * @param i	增量值
     * @return
     */
    public static Date addHour(Date date, long l)
    {
        long l1 = date.getTime();
        long l2 = l * 60L * 60L * 1000L;
        long l3 = l1 + l2;
        Date date1 = new Date(l3);
        return date1;
    }
    /**
     * 给时间分钟增量计算
     * @param date 时间
     * @param i	增量值
     * @return
     */
    public static Date addMinute(Date date, long l)
    {
        long l1 = date.getTime();
        long l2 = l * 60L * 1000L;
        long l3 = l1 + l2;
        Date date1 = new Date(l3);
        return date1;
    }
	/**
	 * 增加秒
	 * @param date 
	 * @return
	 */
	public static Date addSecond(Date date, long l)
    {
        long l1 = date.getTime();
        long l2 = l * 1000L;
        long l3 = l1 + l2;
        Date date1 = new Date(l3);
        return date1;
    }
	
	/**
     * 获取天
     * @param date
     */
	 public static int getDay(Date date)
	    {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        return calendar.get(5);
	    }
	/**
     * 获取月
     * @param date
     */
	public static int getMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(2) + 1;
    }
	 /**
     * 是否是闰年
     * @param i
     * @return
     */
    public static boolean isLeap(int i)
    {
        boolean flag = i % 4 == 0;
        boolean flag1 = i % 100 == 0;
        boolean flag2 = i % 400 == 0;
        return flag && (!flag1 || flag2);
    }
	/**
	 * String格式转换成Date日期 
	 * @param sdate 字符日期2013-08-16
	 * @param format yyyy-MM-dd
	 */
	public static Date parseCustomDateString(String sDate, String format)
    {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date d = null;
		if(sDate != null && sDate.length() == format.length())
		try{
			d = dateFormat.parse(sDate);
        }catch(ParseException ex){
        	return null;
        }
		return d;
    }
	/**
	 * Date日期转换成String 格式
	 * @param date 日期
	 * @param format yyyy-MM-dd ,yyyy-MM,yyyy
	 */
	public static String getCustomDateString(Date date, String format)
    {
		if(date == null)
        {
			return null;
        } else
        {
        	DateFormat dateFormat = new SimpleDateFormat(format);
        	return dateFormat.format(date);
        }
    }
	/**
	 * 按天切片日期
	 * 
	 * @param beginYear
	 * @param beginMonth
	 * @param endYear
	 * @param endMonth
	 * @param k
	 */
	public static List getTimeList(int beginYear, int beginMonth, int endYear,
			int endMonth, int k) {
		List list = new ArrayList();
		if (beginYear == endYear) {
			for (int j = beginMonth; j <= endMonth; j++) {
				list.add(getTimeList(beginYear, j, k));

			}
		} else {
			{
				for (int j = beginMonth; j < 12; j++) {
					list.add(getTimeList(beginYear, j, k));
				}

				for (int i = beginYear + 1; i < endYear; i++) {
					for (int j = 0; j < 12; j++) {
						list.add(getTimeList(i, j, k));
					}
				}
				for (int j = 0; j <= endMonth; j++) {
					list.add(getTimeList(endYear, j, k));
				}
			}
		}
		return list;
	}

	/**
	 * 按天切片一个月
	 * 外围很多次循环时,这里面的2个new可能成为性能风险,进度管理曾经发生过这类问题
	 * @param beginYear
	 * @param beginMonth//begin
	 *            with 0
	 * @param k
	 *            时间跨度
	 */
	public static List getTimeList(int beginYear, int beginMonth, int k) {
		List list = new ArrayList();
		Calendar begincal = new GregorianCalendar(beginYear, beginMonth, 1);
		int max = begincal.getActualMaximum(Calendar.DATE);
		for (int i = 1; i < max; i = i + k) {
			list.add(begincal.getTime());
			begincal.add(Calendar.DATE, k);
		}
		begincal = new GregorianCalendar(beginYear, beginMonth, max);
		list.add(begincal.getTime());
		return list;
	}

	/**
	 * 当前一天的开始时间
	 * 
	 * @return
	 */
	public static java.util.Date getDayBegin() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 当前一天的开始时间
	 * 
	 * @return
	 */
	public static Timestamp getTimeStampDayBegin() {

		return new Timestamp(getDayBegin().getTime());
	}

	/**
	 * 当前一天的结束时间
	 * 
	 * @return
	 */
	public static java.util.Date getDayEnd() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);

		return cal.getTime();
	}

	/**
	 * 当前一天的下一天
	 * 
	 * @return
	 */
	public static java.util.Date getNextDay(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
		return cal.getTime();
	}

	/**
	 * 下个月的同一天,如果不存在则推到下个月
	 * 如:10/31 结果为12/1
	 * 注释 by sxhong 2008-10-10 10:25:40
	 * @param date
	 * @return
	 */
	public static java.util.Date getNextMonth(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		return cal.getTime();
	}

	/**
	 * 上个月的同一天,如果不存在则推到下个月
	 * 如:10/31 结果为10/1
	 * 注释 by sxhong 2008-10-10 10:25:40
	 * @param date
	 * @return
	 */
	public static java.util.Date getPreMonth(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
		return cal.getTime();
	}
	
	/**
	 * 上个月的最后一天,,截断分秒
	 * @param date
	 * @return
	 */
	public static java.util.Date getPreMonthMaxDate(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return  DateTimeUtils.truncateDate(cal.getTime());
	}
	
	/**
	 * 下个月的最后一天,截断分秒
	 * @param date
	 * @return
	 */
	public static java.util.Date getNextMonthMaxDate(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return DateTimeUtils.truncateDate(cal.getTime());
	}
	
	/**
	 * 本月的最后一天,截断分秒
	 * @param date
	 * @return
	 */
	public static java.util.Date getMonthMaxDate(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return DateTimeUtils.truncateDate(cal.getTime());
	}
	/**
	 * sql 的开始时间
	 * 
	 * @param da
	 * @return
	 */
	public static java.sql.Date getSQLBegin(Date da) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(da);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		java.sql.Date ds = new java.sql.Date(cal.getTimeInMillis());
		return ds;

	}

	/**
	 * sql的结束时间
	 * 
	 * @param da
	 * @return
	 */
	public static java.sql.Date getSQLEnd(Date da) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(da);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return new java.sql.Date(cal.getTimeInMillis());

	}

	/**
	 * 返回季度的第一个月
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getFirstSeasonDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int sean = getSeason(cal.get(Calendar.MONTH));
		cal.set(Calendar.MONTH, sean * 3 - 2);
		return cal.getTime();
	}

	/**
	 * 一年的第一天，时间清0
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getFirstYearDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 1);

		return DateTimeUtils.truncateDate(cal.getTime());
	}

	/**
	 * 一年的最后一天，时间清0
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getLastYearDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DATE, 31);

		return DateTimeUtils.truncateDate(cal.getTime());
	}
	
	/**
	 * 根据月分返回季度
	 * 
	 * @param mouth
	 * @return
	 */
	public static int getSeason(int mouth) {
		return SEASON[mouth];
	}

	/**
	 * 获得指定日的前一天日期(按月计算)
	 * 
	 * @param thisDate
	 * @return
	 */
	public static Date getBeforeDay(Date thisDate) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(thisDate);
		cal.add(Calendar.DAY_OF_MONTH, -1);

		return cal.getTime();
	}

	/**
	 * 获得指定日的后一天日期(按月计算)
	 * 
	 * @param thisDate
	 * @return
	 */
	public static Date getAfterDay(Date thisDate) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(thisDate);
		cal.add(Calendar.DAY_OF_MONTH, 1);

		return cal.getTime();
	}

	/**
	 * 
	 * 描述：指定日期是否一年第一天
	 * 
	 * @param date
	 * @return liupd 创建时间：2005-5-18
	 */
	public static boolean isFirstDayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) == Calendar.JANUARY
				&& cal.get(Calendar.DAY_OF_MONTH) == 1;
	}

	/**
	 * 一年的第一天，时间清0
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getFirstYearDate(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		// cal.set
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 1);

		return DateTimeUtils.truncateDate(cal.getTime());
	}

	public static Date stringToDate(String s) {
		Date d = null;
		if (FMHelper.isEmpty(s)) {
			d = getDayBegin();
		} else {
			try {
				d = CommonXRHelper.FORMAT_DAY.parse(s);
			} catch (ParseException e) {
				try {
					d = new SimpleDateFormat().parse(s);
				} catch (ParseException e1) {

					d = getDayBegin();
				}

			}
		}
		return d;

	}
	
	/**
	 * 字符串转时间戳
	 * @author owen_wen
	 * @param s 待转时间戳的字符串
	 * @return 时间戳，格式："yyyy-MM-dd HH:mm:ss"
	 */
	public static Date stringToTimeStamp(String s){
		Date d = null;
		if (FMHelper.isEmpty(s)) {
			d = getDayBegin();
		} else {
			try {
				d = CommonXRHelper.FORMAT_TIME.parse(s);
			} catch (ParseException e) {
				try {
					d = new SimpleDateFormat().parse(s);
				} catch (ParseException e1) {

					d = getDayBegin();
				}

			}
		}
		return d;
	}

	public static String DateToString(Date s) {
		return CommonXRHelper.FORMAT_DAY.format(s);
	}

	/**
	 * 一年的最后一天，时间清0
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getLastYearDate(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		// cal.set
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		// int day = DateTimeUtils.daysOfMonth(year, 12);
		// cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.DATE, 31);

		return DateTimeUtils.truncateDate(cal.getTime());
	}

	/**
	 * 
	 * 描述：开始日期
	 * 
	 * @param da
	 * @return
	 * @author jxd 创建时间：2005-11-17
	 */
	public static java.util.Date getDayBegin(Date da) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(da);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		java.util.Date ds = new java.util.Date(cal.getTimeInMillis());
		return ds;

	}

	/**
	 * 描述：两日期比较的差异值 
	 * @param interval  可以比较的对象为 ：yyyy 年； m 月 ； d 日 ；
	 * 					w: 该月的第几个星期 ; ww: 年中的第几个星期; 
	 * 					h 小时; n 分钟 ; s 秒;
	 * @param dDate1
	 * @param dDate2
	 * @return
	 */
	public static long dateDiff(String interval, Date dDate1, Date dDate2) {
		int desiredField = 0;
		int coef = 1;
		Date date1;
		Date date2;
		if (dDate1.getTime() > dDate2.getTime()) {
			coef = -1;
			date1 = dDate2;
			date2 = dDate1;
		} else {
			date1 = dDate1;
			date2 = dDate2;
		}
		int field;
		if (interval.equals("yyyy"))
			field = 1;
		else if (interval.equals("m"))
			field = 2;
		else if (interval.equals("d"))
			field = 5;
		else if (interval.equals("y"))
			field = 5;
		else if (interval.equals("w"))
			field = 4;
		else if (interval.equals("ww"))
			field = 3;
		else if (interval.equals("h")) {
			field = 5;
			desiredField = 11;
		} else if (interval.equals("n")) {
			field = 5;
			desiredField = 12;
		} else if (interval.equals("s")) {
			field = 5;
			desiredField = 13;
		} else {
			return -1L;
		}
		Calendar calTmp = Calendar.getInstance();
		calTmp.setTime(date1);
		long nbOccurence = 0L;
		calTmp.add(field, 1);
		for (Date dateTemp = calTmp.getTime(); dateTemp.getTime() <= date2
				.getTime();) {
			calTmp.add(field, 1);
			dateTemp = calTmp.getTime();
			nbOccurence++;
		}

		if (desiredField == 11 || desiredField == 12 || desiredField == 13) {
			calTmp.setTime(date1);
			calTmp.add(field, (int) nbOccurence);
			Date dateTemp = calTmp.getTime();
			switch (desiredField) {
			case 11:
				nbOccurence *= 24L;
				break;

			case 12:
				nbOccurence = nbOccurence * 24L * 60L;
				break;

			case 13:
				nbOccurence = nbOccurence * 24L * 60L * 60L;
				break;
			}
			calTmp.add(desiredField, 1);
			for (dateTemp = calTmp.getTime(); dateTemp.getTime() <= date2
					.getTime();) {
				calTmp.add(desiredField, 1);
				dateTemp = calTmp.getTime();
				nbOccurence++;
			}

		}
		return nbOccurence * (long) coef;
	}

	/**
	 * 
	 * 当前月的第一天
	 * @return
	 */
	public static Date getFirstDayOfCurMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		return DateTimeUtils.truncateDate(cal.getTime());
	}
	
	/**
	 * 
	 * 月的第一天
	 * @Date 当前月
	 */
	public static Date getFirstDayOfMonth(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		return DateTimeUtils.truncateDate(cal.getTime());
	}

	/**
	 *当前月的最后一天
	 * 
	 * @return
	 */
	public static Date getEndDayOfCurMonth(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		return DateTimeUtils.truncateDate(cal.getTime());
	}
	
	/**
	 *当前月的最大天数
	 * 
	 * @return
	 */
	public static int getMaxDayOfCurMonth(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return cal.getActualMaximum(Calendar.DATE);
	}

	/**
	 * 
	 *描述：get last day of current month, 截断分秒
	 * 
	 * @return
	 * @author:liupd 创建时间：2005-11-23
	 *               <p>
	 */
	public static Date getLastDayOfCurMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		return DateTimeUtils.truncateDate(cal.getTime());
	}
	
	/**
	 * 描述：取得某日所在月的最后一天,包括分秒
	 * @param date
	 * @return
	 * @author:wangweidong
	 * 创建时间：2007-5-9 <p>
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
    	calendar.add(Calendar.MONTH, 1);
    	calendar.set(Calendar.DAY_OF_MONTH, 1);
    	calendar.add(Calendar.DATE, -1);
    	calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
    	calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
    	calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
    	return calendar.getTime();
	}

	/**
	 * 描述：取得某日所在月的最后一天,截断分秒
	 * @param date
	 * @return
	 * @author:wangweidong
	 * 创建时间：2007-5-9 <p>
	 */
	public static Date getLastDayOfMonth2(Date date) {
		return DateTimeUtils.truncateDate(getLastDayOfMonth(date));
	}
	
	/**
	 * 
	 * 描述：格式化为yyyyMMdd
	 * 
	 * @param d
	 * @return
	 * @author:liupd 创建时间：2005-11-29
	 *               <p>
	 */
	public static String formatDate(Date d) {
		java.text.SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(d);
	}

	/**
	 * 
	 * 描述：格式化为yyyy-MM-dd
	 * 
	 * @param d
	 * @return
	 * @author:liupd 创建时间：2005-11-29
	 *               <p>
	 */
	public static String formatDate2(Date d) {
		java.text.SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(d);
	}

	/**
	 *  最大日期
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Date max(Date date1, Date date2) {
		if (date1 == null) {
			return date2;
		}
		if (date2 == null) {
			return date1;
		}
		if (date1.after(date2)) {
			return date1;
		}
		return date2;
	}
	/**
	 * 最小日期
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Date min(Date date1, Date date2) {
		if (date1 == null) {
			return date2;
		}
		if (date2 == null) {
			return date1;
		}
		if (date1.after(date2)) {
			return date2;
		}
		return date1;
	}
	
	/**
     *  java.sql.Date转化成 java.util.Date
     */
	public static java.sql.Date truncateSqlDate(java.sql.Date sqlDate){
        Date date = new Date(sqlDate.getTime());
        return new java.sql.Date(DateTimeUtils.truncateDate(date).getTime());
    }
	
    /**
     * 描述：两个日期之间的天数
     */
    public static int getDiffDays(Date beginDate, Date endDate) {
    	
    	if (beginDate == null || endDate == null) {
    		throw new IllegalArgumentException("getDiffDays param is null!");
    	}
    	
    	long diff = (endDate.getTime() - beginDate.getTime())/(1000 * 60 * 60 * 24);
    	
    	int days = new Long(diff).intValue() + 1;
    	
    	return days;
    }
    
    /**
     * java.util.Date 转化成java.sql.Date 
     */
    public static java.sql.Date getSqlDate(java.util.Date date) {
    	java.sql.Date sqlDate = new java.sql.Date(date.getTime());
    	return sqlDate;
    }
    
    /**
     * 返回服务器端日期
     * @deprecated
     * @return
     * @throws BOSException
     */
    public static Timestamp getServerTimeStamp() throws BOSException{
    	return XRSQLFacadeFactory.getRemoteInstance().getServerTime();
    }
    
    /**
     * 该日期年的最后一天
     * @param date
     * @return
     */
    public static Date getYearDate(Date date){
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(date);
    	cal.set(cal.get(Calendar.YEAR)+1, 0, 0, 0, 0, 0);
    	return new Date((cal.getTimeInMillis() / 1000) * 1000);
    }
    /**
     * 该日期月的最后一天,时间清0
     * @param date
     * @return
     */
    public static Date getMonthDate(Date date){
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(date);
    	cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, 0, 0, 0, 0);
    	return new Date((cal.getTimeInMillis() / 1000) * 1000);
    }
    /**
     * 年月日日期
     * @param date
     * @return
     */
    public static Date getDayDate(Date date){
    	return DateTimeUtils.truncateDate(date);
    }
    
    /**
     * 增加天数
     * @param date
     * @param day
     * @return
     */
    public static Date addDays(Date date,int day)
    {
    	Calendar calendar = new GregorianCalendar();
    	calendar.setTime(date);
    	calendar.add(Calendar.DATE,day);
    	return calendar.getTime();
    }

	/**
	 * 描述：获取后台日期（Date）
	 */
	public static Date getServerDate() {
		try {
			Timestamp serTime = getServerTimeStamp();
			return new Date(serTime.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
