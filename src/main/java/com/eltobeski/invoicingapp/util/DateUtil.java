package com.eltobeski.invoicingapp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {


        private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
        public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSS";

    public DateUtil() {
    }

    public static String incrementDate(Date reportDate, String dateFormat, int count) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);

        try {
            cal.setTime(reportDate);
            cal.add(5, count);
            return df.format(cal.getTime());
        } catch (Exception var6) {
            logger.info(var6.getMessage());
            return df.format(reportDate);
        }
    }

    public static Date incrementDateHour(Date reportDate, int count) {
        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(reportDate);
            cal.add(10, count);
            return cal.getTime();
        } catch (Exception var4) {
            logger.info(var4.getMessage());
            return cal.getTime();
        }
    }

    public static String getDateFormattedString(Date reportDate, String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);

        try {
            cal.setTime(reportDate);
            return df.format(cal.getTime());
        } catch (Exception var5) {
            logger.info(var5.getMessage());
            return df.format(reportDate);
        }
    }

    public static Date incrementDate(Date reportDate, int count) {
        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(reportDate);
            cal.add(5, count);
            return cal.getTime();
        } catch (Exception var4) {
            logger.info(var4.getMessage());
            return cal.getTime();
        }
    }

    public static Date getJdbcDate(Date date) {
        java.sql.Date utilDate;
        if (date != null) {
            utilDate = new java.sql.Date(date.getTime());
        } else {
            utilDate = null;
        }

        return utilDate;
    }

    public static java.sql.Date getSQLDate(Date date) {
        return date == null ? null : new java.sql.Date(date.getTime());
    }

    public static Timestamp getSQLTimeStamp(Date date) {
        return date == null ? null : new Timestamp(date.getTime());
    }

    public static Date getTheEndOfTheDay(Date day) {
        Calendar c = Calendar.getInstance();
        c.setTime(day);
        c.set(11, c.getMaximum(11));
        c.set(13, c.getMaximum(13));
        c.set(12, c.getMaximum(12));
        return c.getTime();
    }

    public static Date getTheStartOfTheDay(Date day) {
        Calendar c = Calendar.getInstance();
        c.setTime(day);
        c.set(11, c.getMinimum(11));
        c.set(12, c.getMinimum(12));
        c.set(13, c.getMinimum(13));
        return c.getTime();
    }

    public static String convertDateToStringFormat(LocalDateTime originalDate) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS");
        return originalDate.format(dateFormatter);
    }

    public static String convertDateToStringFormat(Date originalDate, String targetDateFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(targetDateFormat);
        return dateFormat.format(originalDate);
    }

    public static String convertDateToStringFormat(LocalDateTime originalDate, String targetDateFormat) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(targetDateFormat);
        return originalDate.format(dateFormatter);
    }

    public static String convertDateStringToFormattedDateString(String originalDate, String originalDateFormat, String targetDateFormat) throws ParseException {
        SimpleDateFormat originalFormat = new SimpleDateFormat(originalDateFormat);
        SimpleDateFormat targetFormat = new SimpleDateFormat(targetDateFormat);
        Date originalDateObject = originalFormat.parse(originalDate);
        return targetFormat.format(originalDateObject);
    }

    public static Date convertDateStringToFormattedDate(String dateString, String dateFormat) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        Calendar start = Calendar.getInstance();
        start.setTime(df.parse(dateString));
        return start.getTime();
    }

    public static boolean isToday(Date date) {
        if (date == null) {
            return false;
        } else {
            Calendar c = Calendar.getInstance();
            c.set(11, 0);
            c.set(12, 0);
            c.set(13, 0);
            c.set(14, 0);
            Date today = c.getTime();
            c.setTime(date);
            c.set(11, 0);
            c.set(12, 0);
            c.set(13, 0);
            c.set(14, 0);
            Date dateSpecified = c.getTime();
            return today.equals(dateSpecified);
        }
    }

    public static long getDateInterval(Date firstDate, Date secondDate) {
        if (firstDate != null && secondDate != null) {
            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        } else {
            return 9223372036854775807L;
        }
    }



    public static LocalDateTime convertStringToLDT(String dateString) {
        Date date = convertStringToDate(dateString);
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static Date convertStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS");
        return dateFormat.parse(dateString, new ParsePosition(0));
    }

    public static String convertDateToStringFormat(Date originalDate) {
        return convertDateToStringFormat(originalDate, "yyyy-MM-dd HH:mm:ss.SSSS");
    }



}
