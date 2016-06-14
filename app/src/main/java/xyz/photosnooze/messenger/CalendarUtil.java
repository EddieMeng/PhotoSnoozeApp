package xyz.photosnooze.messenger;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by shine on 16/6/14.
 */
public class CalendarUtil {


    public static String WeekDayOfTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        String tomorrow = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
        return tomorrow;
    }

}
