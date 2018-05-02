package com.conan.console.server.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ConanUtils {
	public static Date getLastDay(int lastDays) {
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, -1*lastDays);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
}
