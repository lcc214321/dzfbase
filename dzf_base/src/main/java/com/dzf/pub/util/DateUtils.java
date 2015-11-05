/*
 * @(#)DateUtils.java 2006-9-23
 * Copyright 2006 UFIDA Software CO.LTD. All rights reserved.
 */
package com.dzf.pub.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.dzf.pub.lang.DZFDate;


public class DateUtils {
	private static Calendar CALENDAR = Calendar.getInstance();

	public static Date endOfDay(Date date) {
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MILLISECOND, 999);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MINUTE, 59);
			return calendar.getTime();
		}
	}


	public static Date startOfDay(Date date) {
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MINUTE, 0);
			return calendar.getTime();
		}
	}


	public static long startOfDayInMillis(long date) {
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTimeInMillis(date);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MINUTE, 0);
			return calendar.getTimeInMillis();
		}
	}


	public static long endOfDayInMillis(long date) {
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTimeInMillis(date);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MILLISECOND, 999);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MINUTE, 59);
			return calendar.getTimeInMillis();
		}
	}


	public static Date nextDay(Date date) {
		return new Date(addDays(date.getTime(), 1));
	}

	public static long addDays(long time, int amount) {
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTimeInMillis(time);
			calendar.add(Calendar.DAY_OF_MONTH, amount);
			return calendar.getTimeInMillis();
		}
	}


	public static long nextDay(long date) {
		return addDays(date, 1);
	}


	public static long nextWeek(long date) {
		return addDays(date, 7);
	}


	public static int getDaysDiff(long t1, long t2, boolean checkOverflow) {
		if (t1 > t2) {
			long tmp = t1;
			t1 = t2;
			t2 = tmp;
		}
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTimeInMillis(t1);
			int delta = 0;
			while (calendar.getTimeInMillis() < t2) {
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				delta++;
			}
			if (checkOverflow && (calendar.getTimeInMillis() > t2)) {
				delta--;
			}
			return delta;
		}
	}

	public static int getDaysDiff(long t1, long t2) {
		return getDaysDiff(t1, t2, true);
	}

	
	public static boolean isFirstOfYear(long date) {
		boolean ret = false;
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTimeInMillis(date);
			int currentYear = calendar.get(Calendar.YEAR);
			// Check yesterday
			calendar.add(Calendar.DATE, -1);
			int yesterdayYear = calendar.get(Calendar.YEAR);
			ret = (currentYear != yesterdayYear);
		}
		return ret;
	}


	public static boolean isFirstOfMonth(long date) {
		boolean ret = false;
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTimeInMillis(date);
			int currentMonth = calendar.get(Calendar.MONTH);
			// Check yesterday
			calendar.add(Calendar.DATE, -1);
			int yesterdayMonth = calendar.get(Calendar.MONTH);
			ret = (currentMonth != yesterdayMonth);
		}
		return ret;
	}


	public static long previousDay(long date) {
		return addDays(date, -1);
	}


	public static long previousWeek(long date) {
		return addDays(date, -7);
	}


	public static long getPreviousDay(long date, int startOfWeek) {
		return getDay(date, startOfWeek, -1);
	}

	public static long getNextDay(long date, int startOfWeek) {
		return getDay(date, startOfWeek, 1);
	}

	private static long getDay(long date, int startOfWeek, int increment) {
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTimeInMillis(date);
			int day = calendar.get(Calendar.DAY_OF_WEEK);
			// Normalize the view starting date to a week starting day
			while (day != startOfWeek) {
				calendar.add(Calendar.DATE, increment);
				day = calendar.get(Calendar.DAY_OF_WEEK);
			}
			return startOfDayInMillis(calendar.getTimeInMillis());
		}
	}

	public static long getPreviousMonth(long date) {
		return incrementMonth(date, -1);
	}
	public static String getPreviousPeriod(String period) {
		DZFDate date=new DZFDate(period+"-01");
		long l=getPreviousMonth(date.toDate().getTime());
		date=new DZFDate(l);
		String str=date.toString();
		return str.substring(0, 7);
	}
	public static DZFDate getPeriodEndDate(String period) {
		DZFDate date=new DZFDate(period+"-01");
		long l=getEndOfMonth(date.toDate().getTime());
		date=new DZFDate(l);

		return date;
	}
	public static DZFDate getPeriodStartDate(String period) {
		DZFDate date=new DZFDate(period+"-01");

		return date;
	}
	public static String getPeriod(DZFDate enddate) {
		String period=enddate.getYear()+"-"+(enddate.getMonth()<10?"0"+enddate.getMonth():enddate.getMonth());
		return period;
	}

	public static long getPreviousYear(long date) {
		return incrementYear(date, -1);
	}

	public static long getNextYear(long date) {
		return incrementYear(date, 1);
	}


	public static long getNextMonth(long date) {
		return incrementMonth(date, 1);
	}

	private static long incrementYear(long date, int increment) {
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTimeInMillis(date);
			calendar.add(Calendar.YEAR, increment);
			return calendar.getTimeInMillis();
		}
	}

	private static long incrementMonth(long date, int increment) {
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTimeInMillis(date);
			calendar.add(Calendar.MONTH, increment);
			return calendar.getTimeInMillis();
		}
	}


	public static long getStartOfMonth(long date) {
		return getMonth(date, -1);
	}


	public static long getEndOfMonth(long date) {
		return getMonth(date, 1);
	}

	private static long getMonth(long date, int increment) {
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTimeInMillis(date);
			if (increment == -1) {
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				return startOfDayInMillis(calendar.getTimeInMillis());
			} else {
				calendar.add(Calendar.MONTH, 1);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.add(Calendar.MILLISECOND, -1);
				return calendar.getTimeInMillis();
			}
		}
	}


	public static int getDayOfWeek(long date) {
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTimeInMillis(date);
			return (calendar.get(Calendar.DAY_OF_WEEK));
		}
	}


	public static String getDate(Date d) {
		if (d == null)
			return "";
		SimpleDateFormat dataFormate = new SimpleDateFormat("yyyy-MM-dd");
		return dataFormate.format(d);
	}
}
