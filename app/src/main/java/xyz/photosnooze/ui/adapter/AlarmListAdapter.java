package xyz.photosnooze.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.List;

import xyz.photosnooze.R;
import xyz.photosnooze.entity.SnoozePhotoAlarm;
import xyz.photosnooze.messenger.CalendarUtil;

/**
 * Created by shine on 16/6/13.
 */
public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.AlarmViewHolder>{
    private Context mContext;
    private List<SnoozePhotoAlarm> alarmList;

    public class AlarmViewHolder extends RecyclerView.ViewHolder {
        private TextView timeView, weekDayView, receiverView;
        private RelativeLayout alarmRow;

        public AlarmViewHolder(View itemView) {
            super(itemView);
            alarmRow = (RelativeLayout) itemView.findViewById(R.id.alarm_row);
            timeView = (TextView) itemView.findViewById(R.id.timeTextView);
            weekDayView = (TextView) itemView.findViewById(R.id.weekDayTV);
        }
    }

    public AlarmListAdapter(Context context, List<SnoozePhotoAlarm> list) {
        this.mContext = context;
        this.alarmList = list;
    }

    public void upDateContaceList(List<SnoozePhotoAlarm> list) {
        this.alarmList = list;
        notifyDataSetChanged();
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(mContext).inflate(R.layout.alarm_row, parent, false);
        return new AlarmViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder holder, int position) {
        SnoozePhotoAlarm alarm = alarmList.get(position);
        TimePicker timePicker = CalendarUtil.convertMillisecondsToDate(mContext, alarm.getAlarmTime());
        String formatedHour = CalendarUtil.formatAlarmTime(timePicker.getCurrentHour());
        String formatedMinute = CalendarUtil.formatAlarmTime(timePicker.getCurrentMinute());

        holder.timeView.setText(formatedHour + ":" + formatedMinute);
        holder.weekDayView.setText(alarm.getWeekDay());
        holder.alarmRow.setTag(alarm);
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    public Object getItem(int position) {
        return alarmList.size() > 0 ? null : alarmList.get(position);
    }

    private String[] convertStringToArray(String string) {
        String[] array = string.split(",");
        return array;
    }

}
