package com.rc.core.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
}
