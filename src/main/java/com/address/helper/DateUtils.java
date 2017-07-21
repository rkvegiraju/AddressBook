package com.address.helper;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static Date startOfDay(Date currentDate) {
		if (currentDate == null) {
			return currentDate;
		}
		
		Calendar mbCal = Calendar.getInstance();
		mbCal.setTimeInMillis(currentDate.getTime());
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, mbCal.get(Calendar.YEAR));
		cal.set(Calendar.MONTH, mbCal.get(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, mbCal.get(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return cal.getTime();
	}
	
	public static Date endOfDay(Date currentDate) {
		if (currentDate == null) {
			return currentDate;
		}

		Calendar mbCal = Calendar.getInstance();
		mbCal.setTimeInMillis(currentDate.getTime());
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, mbCal.get(Calendar.YEAR));
		cal.set(Calendar.MONTH, mbCal.get(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, mbCal.get(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 0);
		
		return cal.getTime();
	}

}
