package ca.vijaysharma.resume.utils;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Years;

public class Times {
    public static String duration(DateTime start, DateTime end) {
        final Years years = Years.yearsBetween(start, end);
        if (years.getYears() != 0) {
            return years.getYears() == 1 ? years.getYears() + " year" : years.getYears() + " years";
        }

        final Months months = Months.monthsBetween(start, end);
        if (months.getMonths() != 0) {
            return months.getMonths() == 1 ? months.getMonths() + " month" : months.getMonths() + " months";
        }

        final Days days = Days.daysBetween(start, end);
        return days.getDays() + " days";
    }
}
