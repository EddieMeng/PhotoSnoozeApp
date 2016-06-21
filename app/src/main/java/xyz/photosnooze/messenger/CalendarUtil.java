package xyz.photosnooze.messenger;

import android.content.Context;
import android.widget.TimePicker;

import java.util.ArrayList;
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

    public static TimePicker convertMillisecondsToDate(Context context, long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        TimePicker timePicker = new TimePicker(context);

        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        return timePicker;
    }

    public static String formatAlarmTime(int time) {
        String currentMiniteInString = time < 10 ? String.valueOf(0).concat(String.valueOf(time)) : String.valueOf(time);
        return currentMiniteInString;
    }


    private static String convertArrayToString(ArrayList<String> stringArray) {
        String result = "";
        for (int i = 0; i < stringArray.size(); i++) {
            if (i == (stringArray.size() - 1)) {
                result = result.concat(stringArray.get(i));
            } else {
                result = result.concat(stringArray.get(i)).concat(",");
            }
        }
        return result;
    }

}
