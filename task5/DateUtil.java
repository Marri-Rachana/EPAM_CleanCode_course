package com.epam.engx.cleancode.functions.task5;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	private static final int NEXT_DAY = 1;
	private static final int PRIOR_DAY = -1;
	private static final int MIDNIGHT_TIME_COMPONENT = 0;

	public Date changeToNextMidnight(Date date) {
		return resetDate(date, NEXT_DAY);
	}

	public Date changeToPreviousMidnight(Date date) {
		return resetDate(date, PRIOR_DAY);
	}

	private Date resetDate(Date date, int dateResetValue) {
		Calendar calendar = getCalendarInstance(date);
		setTimeToMidNight(calendar, dateResetValue);
		return calendar.getTime();
	}


	private Calendar getCalendarInstance(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}


	private void setTimeToMidNight(Calendar calendar, int dateResetValue) {
		calendar.add(Calendar.DATE, dateResetValue);
		calendar.set(Calendar.HOUR_OF_DAY,MIDNIGHT_TIME_COMPONENT );
		calendar.set(Calendar.MINUTE,MIDNIGHT_TIME_COMPONENT);
		calendar.set(Calendar.SECOND,MIDNIGHT_TIME_COMPONENT);
		calendar.set(Calendar.MILLISECOND,MIDNIGHT_TIME_COMPONENT);
	}


}
