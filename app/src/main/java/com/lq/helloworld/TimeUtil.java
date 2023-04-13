package com.lq.helloworld;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {
    public static String FORMAT_SECOND = "MMM dd yyyy HH:mm:ss";
    public static String FORMAT_DAY = "MMM dd yyyy";
    public static String FORMAT_TIME_STAND = "yyyy-MM-dd HH:mm:ss.SSS Z";
    public static String FORMAT_TIME_API = "yyyy-MM-dd HH:mm:ss";
    public static String TIME_ZONE = "Australia/Sydney";

    public static Date stringToDate(String sourceFormat, String time) {
        try {
            return new SimpleDateFormat(sourceFormat, Locale.ENGLISH).parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将源时区的字符串转化为目的时区的字符串
     *
     * @param sourceFormat
     * @param dstFormat
     * @param time
     * @return
     */
    public static String format(String sourceFormat, String dstFormat, String time) {
        String result = time;
        try {
            Date date = new SimpleDateFormat(sourceFormat).parse(time);
            Date date1 = new Date(date.getTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dstFormat, Locale.ENGLISH);
            simpleDateFormat.setTimeZone(getTimeZone());
            result = simpleDateFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            result = "";
        }
        return result;
    }

    public static String formatLocalZone(String sourceFormat, String dstFormat, String time) {
        String result = time;
        try {
            Date date = new SimpleDateFormat(sourceFormat).parse(time);
            Date date1 = new Date(date.getTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dstFormat, Locale.ENGLISH);
            simpleDateFormat.setTimeZone(TimeZone.getDefault());
            result = simpleDateFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            result = "";
        }
        return result;
    }

    public static String getLocalTimeZoneStr(){
//        return TimeZone.getDefault().getDisplayName(true,TimeZone.SHORT);
        return getCurrentTimezoneOffset();
    }

    public static String getCurrentTimezoneOffset() {
        TimeZone tz = TimeZone.getDefault();
        Calendar cal = GregorianCalendar.getInstance(tz);
        int offsetInMillis = tz.getOffset(cal.getTimeInMillis());
        String offset = String.format("%02d:%02d", Math.abs(offsetInMillis / 3600000), Math.abs((offsetInMillis / 60000) % 60));
        offset = "GMT"+(offsetInMillis >= 0 ? "+" : "-") + offset;
        return offset;
    }

    public static TimeZone getTimeZone() {
        return TimeZone.getTimeZone(TIME_ZONE);
    }

    public static String formatApi(Date date) {
        return format(date, FORMAT_TIME_API);
    }

    public static String format(Date date) {
        return format(date, FORMAT_SECOND);
    }

    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern, Locale.ENGLISH).format(date);
    }

    public static String getSecond() {
        Date utildate = new Date();
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_SECOND, Locale.ENGLISH);
        return df.format(utildate);
    }

    public String getTime() {
        Date utildate = new Date();
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_SECOND, Locale.ENGLISH);
        return df.format(utildate);
    }

    public static String getTimeDay() {
        Date utildate = new Date();
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_DAY, Locale.ENGLISH);
        return df.format(utildate);
    }

    public static String getNowStand() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_TIME_STAND);
        return df.format(calendar.getTime());
    }

    public static String formatMillisecond(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS", Locale.ENGLISH);
        return df.format(calendar.getTime());
    }

    public static String getMillisecond() {
        Date utildate = new Date();
        SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS", Locale.ENGLISH);
        return df.format(utildate);
    }

    public static String getHours() {
        Date utildate = new Date();
        SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy HH:mm:ss", Locale.ENGLISH);
        return df.format(utildate);
    }

    public static String getData(String data) {
        if (data != null) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(FORMAT_SECOND, Locale.ENGLISH);
                String getTime = formatTime(format.parse(data), format.parse(getSecond()), data);
                if (getTime.equals(data)) {
                    return new SimpleDateFormat(FORMAT_DAY, Locale.ENGLISH).format(new Date(data));

                } else {
                    return getTime;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return "";
    }

    public static String getDataSecond(String data) {
        if (data != null) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(FORMAT_SECOND, Locale.ENGLISH);
                String getTime = formatTime(format.parse(data), format.parse(getSecond()), data);
                return getTime;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return data;
    }

    public static String getFormatTimeApiNow() {
        return ImageExifUtils.getExifTimeFormatNowApi();
    }


    private final static long minute = 60 * 1000;
    private final static long hour = 60 * minute;
    private final static long day = 24 * hour;


    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;


    private static final String ONE_SECOND_AGO = "Just now";
    private static final String ONE_MINUTE_AGO = " minute ago";
    private static final String ONE_MINUTES_AGO = " minutes ago";
    private static final String ONE_HOUR_AGO = " hour ago";
    private static final String ONE_HOURS_AGO = " hours ago";


    public static String formatTime(Date date, Date nowDate, String s) {
        long delta = nowDate.getTime() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return ONE_SECOND_AGO;
        }
        if (delta < 60L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            long minutesShow = (minutes <= 0 ? 1 : minutes);
            return minutesShow + ((minutesShow == 1) ? ONE_MINUTE_AGO : ONE_MINUTES_AGO);
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            long hoursShow = (hours <= 0 ? 1 : hours);
            return hoursShow + ((hoursShow == 1) ? ONE_HOUR_AGO : ONE_HOURS_AGO);
        }
        if (delta < 48L * ONE_HOUR) {
            return "Yesterday";
        }
        return s;
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }


}
