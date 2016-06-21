package xyz.photosnooze.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import xyz.photosnooze.R;
import xyz.photosnooze.entity.Contacts;
import xyz.photosnooze.entity.SnoozePhotoAlarm;
import xyz.photosnooze.messenger.CalendarUtil;
import xyz.photosnooze.messenger.MessageStorage;
import xyz.photosnooze.messenger.receiver.AlarmReceiver;
import xyz.photosnooze.ui.actionbar.ActionBarMenu;
import xyz.photosnooze.ui.actionbar.NavigationBackCell;
import xyz.photosnooze.ui.actionbar.PhotoSnoozeActionBar;

public class CreateAlarmActivity extends BaseActivity {
    private final static int REQUEST_SENDTO_FRIEND = 1;

    private PhotoSnoozeActionBar actionbar;
    private NavigationBackCell navigationBackCell;
    private TextView weekDayText;
    private TimePicker timePicker;
    private DatePicker datePicker;
    private Calendar calendar;

    private static BroadcastReceiver timeTickReceiver;
    private static CreateAlarmActivity inst;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    private String Today;

    private ArrayList<Contacts> contactsList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);
        init();

        timeTickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                    long millis = System.currentTimeMillis();
                    calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(millis);

                    int currentHours = calendar.get(Calendar.HOUR_OF_DAY);
                    int currentMinutes = calendar.get(Calendar.MINUTE);

                    int timePickerHours = timePicker.getCurrentHour();
                    int timePickerMinutes = timePicker.getCurrentMinute();

                    if (currentHours > timePickerHours) {
                        weekDayText.setText(CalendarUtil.WeekDayOfTomorrow());
                    } else if (currentHours == timePickerHours && currentMinutes >= timePickerMinutes) {
                        if (timePickerHours >=0) {
                            weekDayText.setText(CalendarUtil.WeekDayOfTomorrow());
                        }
                    } else {
                        weekDayText.setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US));
                    }

                }
            }
        };

        registerReceiver(timeTickReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));


        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                calendar = Calendar.getInstance();
                if (calendar.get(Calendar.HOUR_OF_DAY) > hourOfDay) {
                    if (hourOfDay >=0) {
                        weekDayText.setText(CalendarUtil.WeekDayOfTomorrow());
                    }
                } else if (calendar.get(Calendar.HOUR_OF_DAY) == hourOfDay && calendar.get(Calendar.MINUTE) >= minute) {
                    if (hourOfDay >=0) {
                        weekDayText.setText(CalendarUtil.WeekDayOfTomorrow());
                    }
                } else {
                    weekDayText.setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US));
                }
            }
        });

        navigationBackCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_SENDTO_FRIEND) {
//            if (resultCode == RESULT_OK) {
//                contactsList = (ArrayList<Contacts>) data.getSerializableExtra("contactsChoosen");
//            }
//        }
//    }


    @Override
    protected void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timeTickReceiver != null) {
            unregisterReceiver(timeTickReceiver);
            timeTickReceiver = null;
        }
    }

    private void init() {
        actionbar = (PhotoSnoozeActionBar) findViewById(R.id.snoozeActionbar);
        weekDayText = (TextView) findViewById(R.id.weekDay);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        calendar = Calendar.getInstance();
        datePicker = new DatePicker(this);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        navigationBackCell = actionbar.createNavigationCell();
        navigationBackCell.addNavigationImage(this).addImageIcon(this, R.mipmap.app_icon);

        actionbar.setActionBarTitle("Create Alarm");
        ActionBarMenu menu = actionbar.createMenu(this);
        menu.addCancelImageView(this).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        menu.addSaveImageView(this).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSnoozeAlarm();
            }
        });

        Today = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
        weekDayText.setText(Today);

        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        calendar.add(Calendar.MINUTE, 1);
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
    }



    private void saveSnoozeAlarm() {
                SnoozePhotoAlarm alarm = new SnoozePhotoAlarm();
                int currentHour = timePicker.getCurrentHour();
                int currentMinite = timePicker.getCurrentMinute();

                if (!(weekDayText.getText().toString().equals(Today))) {
                    calendar.add(Calendar.DATE, 1);
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    datePicker.updateDate(year, month, day);
                }
                calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), currentHour, currentMinite);
                alarm.setAlarmTime(calendar.getTimeInMillis());
                alarm.setWeekDay(weekDayText.getText().toString());

                MessageStorage.getInstacne().saveAlarm(alarm);

                Intent myIntent = new Intent(CreateAlarmActivity.this, AlarmReceiver.class);
                myIntent.setAction(String.valueOf(alarm.getAlarmTime()));
                pendingIntent = PendingIntent.getBroadcast(CreateAlarmActivity.this, (int)System.currentTimeMillis(), myIntent, 0);

                alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

                Intent intent = new Intent(CreateAlarmActivity.this, MainActivity.class);
                intent.putExtra("alarmPendingIntent", pendingIntent);
                startActivity(intent);
    }

}
