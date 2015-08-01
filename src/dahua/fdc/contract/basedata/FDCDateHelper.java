package com.kingdee.eas.fdc.basedata;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.util.DateTimeUtils;

public class FDCDateHelper {
	Logger logger = Logger.getLogger(FDCDateHelper.class);

	public final static int[] SEASON = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };

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
	 * 描述：结束日期. 从金地专迁过来的代码
	 * @param da
	 * @return
	 */
	public static java.util.Date getDayEnd(Date da) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(da);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 0);
		java.util.Date ds = new java.util.Date(cal.getTimeInMillis());
		return ds;

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
		int sean = FDCDateHelper.getSeason(cal.get(Calendar.MONTH));
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
		return FDCDateHelper.SEASON[mouth];
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
			d = FDCDateHelper.getDayBegin();
		} else {
			try {
				d = FDCConstants.FORMAT_DAY.parse(s);
			} catch (ParseException e) {
				// @AbortException
				try {
					d = new SimpleDateFormat().parse(s);
				} catch (ParseException e1) {
					// @AbortException
					d = FDCDateHelper.getDayBegin();
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
			d = FDCDateHelper.getDayBegin();
		} else {
			try {
				d = FDCConstants.FORMAT_TIME.parse(s);
			} catch (ParseException e) {
				// @AbortException
				try {
					d = new SimpleDateFormat().parse(s);
				} catch (ParseException e1) {
					// @AbortException
					d = FDCDateHelper.getDayBegin();
				}
			}
		}
		return d;
	}

	public static String DateToString(Date s) {
		return FDCConstants.FORMAT_DAY.format(s);
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
	 * 描述：两日期比较的差异值
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @author jxd 创建时间：2005-11-17
	 */
	public static long dateDiff(Date date1, Date date2) {
		long date1ms = date1.getTime();
		long date2ms = date2.getTime();
		return date2ms - date1ms;
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
	 * @param interval    可以比较的对象为 ：yyyy 年； m 月 ； d 日 ；w: 该月的第几个星期 ; ww: 年中的第几个星期; h 小时; n 分钟 ; s 秒;
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
	 * 描述：get first day of current month, has truncated time
	 * 
	 * @return
	 * @author:liupd 创建时间：2005-11-23
	 *               <p>
	 */
	public static Date getFirstDayOfCurMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		return DateTimeUtils.truncateDate(cal.getTime());
	}
	public static Date getFirstDayOfMonth(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		return DateTimeUtils.truncateDate(cal.getTime());
	}

	/**
	 * get end day of current month
	 * 
	 * @return
	 */
	public static Date getEndDayOfCurMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		return DateTimeUtils.truncateDate(cal.getTime());
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
    	return FDCCommonServerHelper.getServerTimeStamp();
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
    
}
