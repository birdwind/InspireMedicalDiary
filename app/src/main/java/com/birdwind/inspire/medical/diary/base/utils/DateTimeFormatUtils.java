package com.birdwind.inspire.medical.diary.base.utils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.jetbrains.annotations.NotNull;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class DateTimeFormatUtils {

    private static final SimpleDateFormat datetimeDayFormat =
        new SimpleDateFormat("yyyy/MM/dd(E) HH:mm", Locale.TAIWAN);

    private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.TAIWAN);

    private static final SimpleDateFormat minuteFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.TAIWAN);

    private static final SimpleDateFormat hourMinuteFormat = new SimpleDateFormat("HH:mm", Locale.TAIWAN);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN);

    private static final SimpleDateFormat monthDayFormat = new SimpleDateFormat("MM/dd", Locale.TAIWAN);

    // Java SE 7 以上才支援 X -> X 代表時區
    private static final SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.TAIWAN);

    private final static long YEAR = 1000 * 60 * 60 * 24 * 365L;

    private final static long MONTH = 1000 * 60 * 60 * 24 * 30L;

    private final static long WEEK = 1000 * 60 * 60 * 24 * 7L;

    private final static long DAY = 1000 * 60 * 60 * 24L;

    private final static long HOUR = 1000 * 60 * 60L;

    private final static long MINUTE = 1000 * 60L;

    public static String getCurrentTimestamp() {
        return datetimeFormat.format(new Date());
    }

    public static String datetimeDayFormat(Date date) {
        if (date == null) {
            return "";
        }

        return datetimeDayFormat.format(date).replace("週", "");
    }

    public static String datetimeFormat(Date date) {
        if (date == null) {
            return "";
        }

        return datetimeFormat.format(date);
    }

    public static String minuteFormat(Date date) {
        if (date == null) {
            return "";
        }

        return minuteFormat.format(date);
    }

    public static String dateFormat(Date date) {
        if (date == null) {
            return "";
        }

        return dateFormat.format(date);
    }

    public static String monthDayFormat(Date date) {
        if (date == null) {
            return "";
        }

        return monthDayFormat.format(date);
    }

    public static String hourMinuteFormat(Date date) {
        if (date == null) {
            return "";
        }
        return hourMinuteFormat.format(date);
    }

    public static String ISO8601StringFormat(Date date) {
        if (date == null) {
            return "";
        }

        return iso8601Format.format(date);
    }

    public static String numberFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-", Locale.TAIWAN);

        if (date == null) {
            return "";
        }

        return sdf.format(date);
    }

    public static String shortFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("M/d hh:mm", Locale.TAIWAN);

        if (date == null) {
            return "";
        }

        return sdf.format(date);
    }

    public static Date parseDatetimeDay(String dateString) {
        try {
            return datetimeDayFormat.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseDatetime(String dateString) {
        try {
            return datetimeFormat.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseMinute(String dateString) {
        try {
            return minuteFormat.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseMonthDay(String dateString) {
        try {
            return monthDayFormat.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseDate(String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseISO8601String(String dateString) {
        try {
            Date date = iso8601Format.parse(dateString);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR_OF_DAY, 8);
            date = calendar.getTime();
            return date;
        } catch (Exception e) {
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Date afterHours(Date date, int hours) {
        if (date == null) {
            date = new Date();
        }

        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        ZonedDateTime zdt = localDateTime.plusHours(hours).atZone(ZoneId.systemDefault());

        return Date.from(zdt.toInstant());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isCrossDay(Date date) {
        if (date == null) {
            date = new Date();
        }

        Calendar current = Calendar.getInstance();
        current.setTime(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        current.set(Calendar.HOUR_OF_DAY, 0);
        current.set(Calendar.MINUTE, 0);
        current.set(Calendar.SECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        LocalDateTime currentTemporal = toLocalDateTime(current);
        LocalDateTime calendarTemporal = toLocalDateTime(calendar);

        Duration duration = Duration.between(currentTemporal, calendarTemporal);

        return duration.toDays() >= 1;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDateTime toLocalDateTime(Calendar calendar) {
        if (calendar == null) {
            return null;
        }

        TimeZone tz = calendar.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean beforeOrEqual(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return false;
        }

        LocalDateTime dateTime1 = dateToLocalDateTime(d1);
        LocalDateTime dateTime2 = dateToLocalDateTime(d2);

        return dateTime1.isBefore(dateTime2) || dateTime1.isEqual(dateTime2);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDateTime dateToLocalDateTime(@NotNull Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean currentIsBefore(Date date) {
        if (date == null) {
            return false;
        }

        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return LocalDateTime.now().isBefore(localDateTime);
    }

    @NotNull
    public static String convertToChineseYear(String date, boolean isForm) {
        try {
            String[] dateArray = date.split("/");
            StringBuilder year = new StringBuilder(String.valueOf(Integer.parseInt(dateArray[0]) - 1911));
            if (isForm) {
                while (year.length() < 3) {
                    year.insert(0, "0");
                }
            }
            StringBuilder optDate = new StringBuilder(year.toString());
            for (int i = 1; i < dateArray.length; i++) {
                optDate.append("/").append(dateArray[i]);
            }
            return optDate.toString();
        } catch (Exception e) {
            return "";
        }
    }

    @NotNull
    public static String convertFromChineseYear(String date) {
        try {
            String[] dateArray = date.split("/");
            String year = String.valueOf(Integer.parseInt(dateArray[0]) + 1911);
            StringBuilder optDate = new StringBuilder(year);
            for (int i = 1; i < dateArray.length; i++) {
                optDate.append("/").append(dateArray[i]);
            }
            return optDate.toString();
        } catch (Exception e) {
            LogUtils.exception(e);
            return "";
        }
    }

    /**
     * 根據時間查詢時間屬於哪個時刻
     * 
     * @param date
     * @return
     */
    public static String natureTime(Date date) {
        Date now = new Date();
        long between = now.getTime() - date.getTime();

        if (between > YEAR) {
            return ((between - YEAR) / YEAR + 1) + "年前";
        }
        if (between > MONTH) {
            return ((between - MONTH) / MONTH + 1) + "月前";
        }
        if (between > WEEK) {
            return ((between - WEEK) / WEEK + 1) + "周前";
        }
        if (between > DAY) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            String time = hourMinuteFormat(date);
            switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.MONDAY:
                    return "星期一 " + time;
                case Calendar.TUESDAY:
                    return "星期二 " + time;
                case Calendar.WEDNESDAY:
                    return "星期三 " + time;
                case Calendar.THURSDAY:
                    return "星期四 " + time;
                case Calendar.FRIDAY:
                    return "星期五 " + time;
                case Calendar.SATURDAY:
                    return "星期六 " + time;
                case Calendar.SUNDAY:
                    return "星期日 " + time;
            }
        }
        if (between > HOUR) {
            return ((between - HOUR) / HOUR + 1) + "小時前";
        }
        if (between > MINUTE) {
            return ((between - MINUTE) / MINUTE + 1) + "分鐘前";
        }
        return "剛剛";
    }

}
