package com.ef;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

    public static int convertDurationStringToInt(String calenderField) throws Exception {
        switch (calenderField) {
            case "hourly":
                return Calendar.HOUR_OF_DAY;
            case "daily":
                return Calendar.DAY_OF_MONTH;
            case "weekly":
                return Calendar.WEEK_OF_MONTH;
            case "monthly":
                return Calendar.MONTH;
            default:
                throw new Exception("Valid options for duration are (hourly, daily, weekly or monthly)");
        }
    }

    public static SimpleDateFormat inputArgDateFormater() {
        return new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");
    }

    public static SimpleDateFormat logDateFormater() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
