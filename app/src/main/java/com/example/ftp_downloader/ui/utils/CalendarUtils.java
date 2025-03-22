package com.example.ftp_downloader.ui.utils;

import android.content.Context;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class CalendarUtils {

    /**
     * Returns the current date in string format
     *
     * @return string format of current date in most used format. i.e., "yyyy-MM-dd HH:mm:ss"
     */
    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    /**
     * Returns current date using the given pattern
     *
     * @param format required date format
     * @return string format of current date in given format.
     */
    public static String getCurrentDate(String format) {
        if (format.contains("dddd,")) format = format.replace("dddd,", "EEE,");
        return new SimpleDateFormat(format, Locale.getDefault()).format(new Date());
    }

    public static String getYearMonthDate(String date) {
        return convertDateFormat(date, "dd-MM-yyyy", "yyyy-MM-dd");
    }

    /**
     * Converts date formats
     *
     * @param date             date in string format
     * @param inputDateFormat  format of date param
     * @param outputDateFormat required date format
     * @return date in string format as required
     */
    public static String convertDateFormat(String date, String inputDateFormat, String outputDateFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(inputDateFormat, Locale.getDefault());
        Date oldDate = new Date();
        try {
            oldDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat(outputDateFormat, Locale.getDefault()).format(Objects.requireNonNull(oldDate));
    }


    /**
     * @param dateString        date in string format
     * @param dateStringPattern date pattern of given date string
     * @return a {@link Date} object for a given string date
     */
    public static Date getDate(String dateString, String dateStringPattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateStringPattern, Locale.getDefault());
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String getDate(long milliseconds, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getTimeFromDate(Context context, String dateTime) {
        return getTimeFromDate(context, dateTime, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getTimeFromDate(Context context, String dateTime, String dateTimeFormat) {
        String time = "";
        SimpleDateFormat defaultDateTimeFormat = new SimpleDateFormat(dateTimeFormat, Locale.getDefault());
        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        Date date = new Date();
        try {
            date = defaultDateTimeFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) return time;

        try {
            if (DateFormat.is24HourFormat(context))
                time = _24HourSDF.format(date);
            else time = _12HourSDF.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String convert(String date, String inputFormat, String outputFormat) {
        if (date == null) return null;
        Date date1 = getDate(date, inputFormat);
        if (date1 != null) {
            return getDate(date1.getTime(), outputFormat);
        } else {
            return null;
        }
    }

    public static boolean isFormatRight(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            Date d = dateFormat.parse(dateString);
            return  true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

}
