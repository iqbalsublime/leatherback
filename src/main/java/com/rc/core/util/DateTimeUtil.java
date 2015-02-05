package com.rc.core.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
	private static final String UTC_TIME_ZONE = "Etc/UTC";

	public static String toString(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(date);
	}

	public static Date toDate(String dateString, String format) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.parse(dateString);
	}

	public static Date toUtcTime(Date localTime) {
		ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(localTime.toInstant(), ZoneId.of(UTC_TIME_ZONE));

		return Date.from(zonedDateTime.toInstant());
	}

	public static java.sql.Timestamp convertToSqlTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	public static java.sql.Date convertToSqlDate(Date date) {
		return new java.sql.Date(date.getTime());
	}

	public static Date getTheBeginingOfDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	public static Date getTheEndOfDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);

		return calendar.getTime();
	}
}
