package xyz.photosnooze.messenger.receiver;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by shine on 16/6/15.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i("preAction", action);
        Intent launchIntent = new Intent();
        launchIntent.setClassName(context, "xyz.photosnooze.ui.AlarmRingActivity");
        launchIntent.setAction(action);
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(launchIntent);
    }
}
