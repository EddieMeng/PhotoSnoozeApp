package xyz.photosnooze.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xyz.photosnooze.R;
import xyz.photosnooze.entity.SnoozePhotoAlarm;
import xyz.photosnooze.messenger.ApplicationLoader;
import xyz.photosnooze.messenger.MessageStorage;
import xyz.photosnooze.messenger.NotificationCenter;
import xyz.photosnooze.ui.actionbar.PhotoSnoozeActionBar;
import xyz.photosnooze.ui.adapter.AlarmListAdapter;
import xyz.photosnooze.ui.components.DividerItemDecoration;

public class MainActivity extends BaseActivity implements NotificationCenter.NotificationDelegate{
    private ImageView addAlarmButton;
    private RecyclerView alarmRecyclerView;
    private PhotoSnoozeActionBar actionBar;

    private List<SnoozePhotoAlarm> alarmList;
    private AlarmListAdapter alarmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApplicationLoader.postInitApplication();
        init();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.snoozePhotoAlarmDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.snoozePhotoAlarmDidDeleted);

        actionBar.setActionBarTitle("PhotoSnooze");
        MessageStorage.getInstacne().getAllAlarms();

        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAlarmActivity.class);
                startActivity(intent);
            }
        });

        alarmRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(this, alarmRecyclerView, new recyclerViewOnClickListener() {
            @Override
            public void onClick(View view, int Position) {

            }

            @Override
            public void onLongClick(View view, int Position) {
                final SnoozePhotoAlarm snoozePhotoAlarm = (SnoozePhotoAlarm)(view.findViewById(R.id.alarm_row).getTag());
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("SnoozePhoto");
                builder.setItems(new CharSequence[]{"Delete Alarm"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MessageStorage.getInstacne().deleteAlarm(snoozePhotoAlarm);
                        alarmList.remove(snoozePhotoAlarm);
                    }
                });
                AlertDialog dialog = builder.show();

            }
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.snoozePhotoAlarmDidLoaded);
    }


    private void init() {
        actionBar = (PhotoSnoozeActionBar) findViewById(R.id.actionbar_main);
        alarmRecyclerView = (RecyclerView) findViewById(R.id.alarmView);
        addAlarmButton = (ImageView) findViewById(R.id.addAlarmButton);
    }


    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.snoozePhotoAlarmDidLoaded) {
            alarmList = (ArrayList<SnoozePhotoAlarm>) args[0];
            if (alarmAdapter != null) {
                alarmAdapter.upDateContaceList(alarmList);
            } else {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                alarmRecyclerView.setLayoutManager(layoutManager);
                alarmRecyclerView.setItemAnimator(new DefaultItemAnimator());
                alarmRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
                alarmAdapter = new AlarmListAdapter(MainActivity.this, alarmList);
                alarmRecyclerView.setAdapter(alarmAdapter);
                alarmAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Yep", Toast.LENGTH_SHORT).show();
            }
        } else if (id == NotificationCenter.snoozePhotoAlarmDidDeleted) {
            if (alarmAdapter != null) {
                alarmAdapter.notifyDataSetChanged();
            }
        }
    }

    public interface recyclerViewOnClickListener {
        void onClick(View view, int Position);
        void onLongClick(View view, int Position);
    }

    public static class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {
        private recyclerViewOnClickListener listener;
        private GestureDetector gestureDetector;

        public RecyclerViewTouchListener(Context context, final RecyclerView recyclerView, final recyclerViewOnClickListener listener) {
            this.listener = listener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && listener != null) {
                        listener.onLongClick(child, recyclerView.getChildAdapterPosition(child));

                    }
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });

        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && listener != null && gestureDetector.onTouchEvent(e)) {
                listener.onClick(rv, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
