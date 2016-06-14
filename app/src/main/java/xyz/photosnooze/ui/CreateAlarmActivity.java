package xyz.photosnooze.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import xyz.photosnooze.ui.actionbar.ActionBarMenu;
import xyz.photosnooze.ui.actionbar.NavigationBackCell;
import xyz.photosnooze.ui.actionbar.PhotoSnoozeActionBar;

public class CreateAlarmActivity extends BaseActivity {
    private final static int REQUEST_SENDTO_FRIEND = 1;
    private ArrayList<Integer> currentTime = new ArrayList<>();

    private PhotoSnoozeActionBar actionbar;
    private NavigationBackCell navigationBackCell;
    private TextView sendToButton, weekDayText;
    private TimePicker timePicker;
    private Calendar calendar;
    private ArrayList<Contacts> contactsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);
        init();
        sendToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAlarmActivity.this, FriendListActivity.class);
                startActivityForResult(intent, REQUEST_SENDTO_FRIEND);
            }
        });

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SENDTO_FRIEND) {
            if (resultCode == RESULT_OK) {
                contactsList = (ArrayList<Contacts>) data.getSerializableExtra("contactsChoosen");
            }
        }
    }

    private void init() {
        actionbar = (PhotoSnoozeActionBar) findViewById(R.id.snoozeActionbar);
        sendToButton = (TextView) findViewById(R.id.sendToBtn);
        weekDayText = (TextView) findViewById(R.id.weekDay);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        calendar = Calendar.getInstance();

        navigationBackCell = actionbar.createNavigationCell();
        navigationBackCell.addNavigationImage(this).addImageIcon(this, R.mipmap.ic_launcher);


        actionbar.setActionBarTitle("Create Alarm");
        ActionBarMenu menu = actionbar.createMenu(this);
        menu.addCancelMenuItem(this).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        menu.addDividerLine(this);
        menu.addSaveMenuItem(this).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSnoozeAlarm();
            }
        });

        String weekDay = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
        weekDayText.setText(weekDay);

        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        calendar.add(Calendar.MINUTE, 1);
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
    }



    private void saveSnoozeAlarm() {
        if (contactsList.size() > 0) {
            Contacts contacts = contactsList.get(0);
            if (contacts != null) {
                SnoozePhotoAlarm alarm = new SnoozePhotoAlarm();
                int currentHour = timePicker.getCurrentHour();
                int currentMinite = timePicker.getCurrentMinute();
                String currentHourInString = currentHour < 10 ? String.valueOf(0).concat(String.valueOf(currentHour)) : String.valueOf(currentHour);
                String currentMiniteInString = currentMinite < 10 ? String.valueOf(0).concat(String.valueOf(currentMinite)) : String.valueOf(currentMinite);
                ArrayList<String> list = new ArrayList<>();
                list.add(currentHourInString);
                list.add(currentMiniteInString);

                alarm.setAlarmTime(convertArrayToString(list));
                alarm.setReceiverName(contacts.getName());
                alarm.setWeekDay(weekDayText.getText().toString());

                MessageStorage.getInstacne().saveAlarm(alarm);
                Intent intent = new Intent(CreateAlarmActivity.this, MainActivity.class);
                startActivity(intent);
            }
        } else {
            showToast("Still Not Choose Friend To Send");
        }

    }

    private String convertArrayToString(ArrayList<String> stringArray) {
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
