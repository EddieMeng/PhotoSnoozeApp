package xyz.photosnooze.entity;

/**
 * Created by shine on 16/6/12.
 */
public class SnoozePhotoAlarm {
    private int id;
    private String alarmTime;
    private String weekDay;
    private String receiverName;

    public SnoozePhotoAlarm() {

    }

    public SnoozePhotoAlarm(String time, String weekDay, String name) {
        this.alarmTime = time;
        this.weekDay = weekDay;
        this.receiverName = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
}
