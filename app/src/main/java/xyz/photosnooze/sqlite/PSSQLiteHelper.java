package xyz.photosnooze.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import xyz.photosnooze.entity.SnoozePhotoAlarm;
import xyz.photosnooze.messenger.AndroidUtilities;
import xyz.photosnooze.messenger.NotificationCenter;

/**
 * Created by shine on 16/6/12.
 */
public class PSSQLiteHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "photosnooze.db";
    private static final String COLUMN_ID = "_id";

    private static final String TABLE_ALARM = "alarm";
    private static final String COLUMN_TIME = "alarmtime";
    private static final String COLUMN_WEEKDAY = "weekday";
    private static final String COLUMN_RECEIVER = "receivername";

    private static final String DATABASE_CREATE_ALARM = "CREATE TABLE " + TABLE_ALARM + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TIME + " string, "
            + COLUMN_WEEKDAY + " string, "
            + COLUMN_RECEIVER + " string);";

    public PSSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_ALARM);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    public void createAlarm(SnoozePhotoAlarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME, alarm.getAlarmTime());
        values.put(COLUMN_WEEKDAY, alarm.getWeekDay());
        values.put(COLUMN_RECEIVER, alarm.getReceiverName());
        db.insert(TABLE_ALARM, null, values);

        db.close();
    }


    public ArrayList<SnoozePhotoAlarm> readAllAlarm() {
        final ArrayList<SnoozePhotoAlarm> alarmList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_ALARM;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                SnoozePhotoAlarm alarm = new SnoozePhotoAlarm();
                alarm.setId(Integer.parseInt(cursor.getString(0)));
                alarm.setAlarmTime(cursor.getString(1));
                alarm.setWeekDay(cursor.getString(2));
                alarm.setReceiverName(cursor.getString(3));
                alarmList.add(alarm);
            } while (cursor.moveToNext());
        }
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                NotificationCenter.getInstance().postNotification(NotificationCenter.snoozePhotoAlarmDidLoaded, alarmList);
            }
        });
        return alarmList;
    }


    public void deleteAlarm(SnoozePhotoAlarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALARM, COLUMN_ID + " = ? ", new String[]{String.valueOf(alarm.getId())});
        db.close();
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                NotificationCenter.getInstance().postNotification(NotificationCenter.snoozePhotoAlarmDidDeleted);
            }
        });
    }



}
