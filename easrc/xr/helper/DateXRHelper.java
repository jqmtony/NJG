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
	 * ���������ֶε�ƫ������ֵ
	 * @param date ����
	 * @param field �����ֶ�
	 * @param amount Ϊ�ֶ���ӵ����ڻ�ʱ����
	 * @return ����ƫ�Ƶ���������
	 */
	public static Date offsetDate(Date date, int field, int amount) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		
		return cal.getTime();
	}
	
	/**
	 * �����ת��������ʱ�� �� 2008 ����2008-01-01
	 * @param year ��������ֶ�
	 * @return
	 */
	public static Date yearToDate(int year) {
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, 0, 1);
        
		return cal.getTime();
	}
	
	/**
	 * �����ת������ʱ�䲢������ȵ�������ڵ�ʱ��� 
	 * �� 2008 ����2008��12��31��23ʱ59��59��59����...
	 * @param year
	 * @return
	 */
	public static Date yearDateTo(int year) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(yearToDate(year));
		cal.add(Calendar.YEAR, 1);//	��1��
		cal.add(Calendar.MILLISECOND, -1);//	��1����
		
		return cal.getTime();
	}
	
	/**
	 * ��������ʱ����һ������ʱ��� ��ĳ��ĳ��ĳ��23ʱ59��59��59����...
	 * @param date
	 * @return
	 */
	public static Date DateTo(Date date) {
		Calendar cal = Calendar.getInstance();
		DateTimeUtils.truncateDate(date);
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);//	��1��
		cal.add(Calendar.MILLISECOND, -1);//	��1����
		cal.getTime();
		return cal.getTime();
	}
	/**
	 * ��һ��
	 * @param date
	 * @return
	 */
	public static Date lastDate(Date date) {
		Calendar cal = Calendar.getInstance();
		DateTimeUtils.truncateDate(date);
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);//	��1��
		cal.add(Calendar.MILLISECOND, -1);//	��1����
		cal.getTime();
		return cal.getTime();
	}
	/**
	 *��ʱ������������
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
     * ��ʱ���·���������
     * @param date ʱ��
     * @param i	����ֵ
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
     * ��ʱ��Сʱ��������
     * @param date ʱ��
     * @param i	����ֵ
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
     * ��ʱ�������������
     * @param date ʱ��
     * @param i	����ֵ
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
	 * ������
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
     * ��ȡ��
     * @param date
     */
	 public static int getDay(Date date)
	    {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        return calendar.get(5);
	    }
	/**
     * ��ȡ��
     * @param date
     */
	public static int getMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(2) + 1;
    }
	 /**
     * �Ƿ�������
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
	 * String��ʽת����Date���� 
	 * @param sdate �ַ�����2013-08-16
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
	 * Date����ת����String ��ʽ
	 * @param date ����
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
	 * ������Ƭ����
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
	 * ������Ƭһ����
	 * ��Χ�ܶ��ѭ��ʱ,�������2��new���ܳ�Ϊ���ܷ���,���ȹ���������������������
	 * @param beginYear
	 * @param beginMonth//begin
	 *            with 0
	 * @param k
	 *            ʱ����
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
	 * ��ǰһ��Ŀ�ʼʱ��
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
	 * ��ǰһ��Ŀ�ʼʱ��
	 * 
	 * @return
	 */
	public static Timestamp getTimeStampDayBegin() {

		return new Timestamp(getDayBegin().getTime());
	}

	/**
	 * ��ǰһ��Ľ���ʱ��
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
	 * ��ǰһ�����һ��
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
	 * �¸��µ�ͬһ��,������������Ƶ��¸���
	 * ��:10/31 ���Ϊ12/1
	 * ע�� by sxhong 2008-10-10 10:25:40
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
	 * �ϸ��µ�ͬһ��,������������Ƶ��¸���
	 * ��:10/31 ���Ϊ10/1
	 * ע�� by sxhong 2008-10-10 10:25:40
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
	 * �ϸ��µ����һ��,,�ضϷ���
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
	 * �¸��µ����һ��,�ضϷ���
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
	 * ���µ����һ��,�ضϷ���
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
	 * sql �Ŀ�ʼʱ��
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
	 * sql�Ľ���ʱ��
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
	 * ���ؼ��ȵĵ�һ����
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
	 * һ��ĵ�һ�죬ʱ����0
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
	 * һ������һ�죬ʱ����0
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
	 * �����·ַ��ؼ���
	 * 
	 * @param mouth
	 * @return
	 */
	public static int getSeason(int mouth) {
		return SEASON[mouth];
	}

	/**
	 * ���ָ���յ�ǰһ������(���¼���)
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
	 * ���ָ���յĺ�һ������(���¼���)
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
	 * ������ָ�������Ƿ�һ���һ��
	 * 
	 * @param date
	 * @return liupd ����ʱ�䣺2005-5-18
	 */
	public static boolean isFirstDayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) == Calendar.JANUARY
				&& cal.get(Calendar.DAY_OF_MONTH) == 1;
	}

	/**
	 * һ��ĵ�һ�죬ʱ����0
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
	 * �ַ���תʱ���
	 * @author owen_wen
	 * @param s ��תʱ������ַ���
	 * @return ʱ�������ʽ��"yyyy-MM-dd HH:mm:ss"
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
	 * һ������һ�죬ʱ����0
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
	 * ��������ʼ����
	 * 
	 * @param da
	 * @return
	 * @author jxd ����ʱ�䣺2005-11-17
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
	 * �����������ڱȽϵĲ���ֵ 
	 * @param interval  ���ԱȽϵĶ���Ϊ ��yyyy �ꣻ m �� �� d �� ��
	 * 					w: ���µĵڼ������� ; ww: ���еĵڼ�������; 
	 * 					h Сʱ; n ���� ; s ��;
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
	 * ��ǰ�µĵ�һ��
	 * @return
	 */
	public static Date getFirstDayOfCurMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		return DateTimeUtils.truncateDate(cal.getTime());
	}
	
	/**
	 * 
	 * �µĵ�һ��
	 * @Date ��ǰ��
	 */
	public static Date getFirstDayOfMonth(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		return DateTimeUtils.truncateDate(cal.getTime());
	}

	/**
	 *��ǰ�µ����һ��
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
	 *��ǰ�µ��������
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
	 *������get last day of current month, �ضϷ���
	 * 
	 * @return
	 * @author:liupd ����ʱ�䣺2005-11-23
	 *               <p>
	 */
	public static Date getLastDayOfCurMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		return DateTimeUtils.truncateDate(cal.getTime());
	}
	
	/**
	 * ������ȡ��ĳ�������µ����һ��,��������
	 * @param date
	 * @return
	 * @author:wangweidong
	 * ����ʱ�䣺2007-5-9 <p>
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
	 * ������ȡ��ĳ�������µ����һ��,�ضϷ���
	 * @param date
	 * @return
	 * @author:wangweidong
	 * ����ʱ�䣺2007-5-9 <p>
	 */
	public static Date getLastDayOfMonth2(Date date) {
		return DateTimeUtils.truncateDate(getLastDayOfMonth(date));
	}
	
	/**
	 * 
	 * ��������ʽ��ΪyyyyMMdd
	 * 
	 * @param d
	 * @return
	 * @author:liupd ����ʱ�䣺2005-11-29
	 *               <p>
	 */
	public static String formatDate(Date d) {
		java.text.SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(d);
	}

	/**
	 * 
	 * ��������ʽ��Ϊyyyy-MM-dd
	 * 
	 * @param d
	 * @return
	 * @author:liupd ����ʱ�䣺2005-11-29
	 *               <p>
	 */
	public static String formatDate2(Date d) {
		java.text.SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(d);
	}

	/**
	 *  �������
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
	 * ��С����
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
     *  java.sql.Dateת���� java.util.Date
     */
	public static java.sql.Date truncateSqlDate(java.sql.Date sqlDate){
        Date date = new Date(sqlDate.getTime());
        return new java.sql.Date(DateTimeUtils.truncateDate(date).getTime());
    }
	
    /**
     * ��������������֮�������
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
     * java.util.Date ת����java.sql.Date 
     */
    public static java.sql.Date getSqlDate(java.util.Date date) {
    	java.sql.Date sqlDate = new java.sql.Date(date.getTime());
    	return sqlDate;
    }
    
    /**
     * ���ط�����������
     * @deprecated
     * @return
     * @throws BOSException
     */
    public static Timestamp getServerTimeStamp() throws BOSException{
    	return XRSQLFacadeFactory.getRemoteInstance().getServerTime();
    }
    
    /**
     * ������������һ��
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
     * �������µ����һ��,ʱ����0
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
     * ����������
     * @param date
     * @return
     */
    public static Date getDayDate(Date date){
    	return DateTimeUtils.truncateDate(date);
    }
    
    /**
     * ��������
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
	 * ��������ȡ��̨���ڣ�Date��
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
