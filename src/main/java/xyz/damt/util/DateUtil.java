package xyz.damt.util;

import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String getFormattedDate(long value) {
        Date date = new Date(value);
        String stringDateFormat = "MMMM d, yyyy";
        DateFormat dateFormat = new SimpleDateFormat(stringDateFormat);
        String formattedDate = dateFormat.format(date).replaceAll(",", "th,");
        return formattedDate;
    }

    public static long getDurationFromString(String input, int i) {
        if (input.equalsIgnoreCase("mon")) {
            return DateUtils.addMonths(new Date(), i).getTime() - new Date().getTime();
        }

        if (input.equalsIgnoreCase("mons")) {
            return DateUtils.addMonths(new Date(), i).getTime() - new Date().getTime();
        }

        if (input.equalsIgnoreCase("month")) {
            return DateUtils.addMonths(new Date(), i).getTime() - new Date().getTime();
        }

        if (input.equalsIgnoreCase("m")) {
            return DateUtils.addMonths(new Date(), i).getTime() - new Date().getTime();
        }

        if (input.equalsIgnoreCase("min")) {
            return DateUtils.addMinutes(new Date(), i).getTime() - new Date().getTime();
        }

        if (input.equalsIgnoreCase("mins")) {
            return DateUtils.addMinutes(new Date(), i).getTime() - new Date().getTime();
        }

        if (input.equalsIgnoreCase("minute")) {
            return DateUtils.addMinutes(new Date(), i).getTime() - new Date().getTime();
        }

        if (input.equalsIgnoreCase("minutes")) {
            return DateUtils.addMinutes(new Date(), i).getTime() - new Date().getTime();
        }

        if (input.equalsIgnoreCase("h")) {
            return DateUtils.addHours(new Date(), i).getTime() - new Date().getTime();
        }

        if (input.equalsIgnoreCase("hour")) {
            return DateUtils.addHours(new Date(), i).getTime() - new Date().getTime();
        }
        if (input.equalsIgnoreCase("hours")) {
            return DateUtils.addHours(new Date(), i).getTime() - new Date().getTime();
        }

        if (input.equalsIgnoreCase("s")) {
            return DateUtils.addSeconds(new Date(), i).getTime() - new Date().getTime();
        }

        if (input.equalsIgnoreCase("sec")) {
            return DateUtils.addSeconds(new Date(), i).getTime() - new Date().getTime();
        }

        if (input.equalsIgnoreCase("second")) {
            return DateUtils.addSeconds(new Date(), i).getTime() - new Date().getTime();
        }

        if (input.equalsIgnoreCase("w")) {
            return DateUtils.addWeeks(new Date(), i).getTime() - new Date().getTime();
        }

        if (input.equalsIgnoreCase("week")) {
            return DateUtils.addWeeks(new Date(), i).getTime() - new Date().getTime();
        }

        if (input.equalsIgnoreCase("weeks")) {
            return DateUtils.addWeeks(new Date(), i).getTime() - new Date().getTime();
        }

        if (input.equalsIgnoreCase("seconds")) {
            return DateUtils.addSeconds(new Date(), i).getTime() - new Date().getTime();
        }

        if (input.equalsIgnoreCase("y")) {
            return DateUtils.addYears(new Date(), i).getTime() - new Date().getTime();
        }

        if (input.equalsIgnoreCase("year")) {
            return DateUtils.addYears(new Date(), i).getTime() - new Date().getTime();
        }

        if (input.equalsIgnoreCase("years")) {
            return DateUtils.addYears(new Date(), i).getTime() - new Date().getTime();
        }

        return Long.parseLong(null);

    }
}

