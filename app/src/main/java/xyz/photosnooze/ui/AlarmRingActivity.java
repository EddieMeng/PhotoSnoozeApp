package xyz.photosnooze.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import xyz.photosnooze.R;
import xyz.photosnooze.messenger.CalendarUtil;
import xyz.photosnooze.ui.components.SlideToStopBar;

public class AlarmRingActivity extends BaseActivity {
    private final static int TAKE_PICTURE = 1;
    private final static String APP_TAG = "Snooze Photo";
    private static final String IMAGE_PREFIX = "IMG";
    private static final String IMAGE_SUFFIX = ".jpg";

    private String takenFileName;
    private Uri takenPhotoUri;

    private SlideToStopBar slideToStopBar;
    private ImageView alarmClockImage;
    private long alarmRingingTime;
    private TextView alarmTimeTextView;

    private Ringtone ringtone;
    private NotificationManager notificationManager;

    private AnimatorSet animatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ring);
        init();
        Intent intent = getIntent();
        String action = intent.getAction();
        alarmRingingTime = Long.parseLong(action);

        if (alarmRingingTime != 0) {
            TimePicker timePicker = CalendarUtil.convertMillisecondsToDate(AlarmRingActivity.this, alarmRingingTime);
            String formatedHour = CalendarUtil.formatAlarmTime(timePicker.getCurrentHour());
            String formatedMinute = CalendarUtil.formatAlarmTime(timePicker.getCurrentMinute());
            alarmTimeTextView.setText(formatedHour + ":" + formatedMinute);
        }

        startAlarmAnimation(alarmClockImage);
        playRingtone();
        sendNotification("Wake Up!");

        slideToStopBar.setOnUnlockListener(new SlideToStopBar.OnUnlockListener() {
            @Override
            public void onUnlock() {
                showToast("Alarm Already Stop");
                if (ringtone != null) {
                    ringtone.stop();
                }
                stopAlarmAnimation();
                notificationManager.cancel(1);
                finish();
                System.exit(0);
            }

            @Override
            public void onStopClick() {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File imageEmptyFile = createImageFile();
                takenPhotoUri = Uri.fromFile(imageEmptyFile);

//                if (imageEmptyFile != null) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, takenPhotoUri);
                    startActivityForResult(intent, TAKE_PICTURE);
//                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PICTURE) {
            if (resultCode == RESULT_OK) {
                Uri takenImage = takenPhotoUri;
                Bitmap bitmap = BitmapFactory.decodeFile(takenImage.getPath());
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
                sharePhotoByTelegramMessage(takenImage);
            }
        }
    }

    private void init() {
        slideToStopBar = (SlideToStopBar) findViewById(R.id.slideToStop);
        alarmClockImage = (ImageView) findViewById(R.id.alarmImage);
        alarmTimeTextView = (TextView) findViewById(R.id.alarmTimeTV);
    }

    private void playRingtone() {
        Uri alarmUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = RingtoneManager.getRingtone(this, alarmUri);
        if (ringtone != null) {
            ringtone.play();
        }
    }

    private void sendNotification(String msg) {
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, AlarmRingActivity.class), 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setContentTitle("Alarm").setSmallIcon(R.mipmap.app_icon)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg)).setContentText(msg).setAutoCancel(true);

        builder.setContentIntent(contentIntent);
        notificationManager.notify(1, builder.build());

    }

    private void startAlarmAnimation(View view) {
        ObjectAnimator animLeft = ObjectAnimator.ofFloat(view, "rotation", 0f, -15f);
        ObjectAnimator animLeftReverse = ObjectAnimator.ofFloat(view, "rotation", -15f, 0f);

        ObjectAnimator animRight = ObjectAnimator.ofFloat(view, "rotation", 0f, 15f);
        ObjectAnimator animRightReverse = ObjectAnimator.ofFloat(view, "rotation", 15f, 0f);

        animatorSet = new AnimatorSet();
        animatorSet.play(animLeft).before(animLeftReverse);
        animatorSet.play(animRight).after(animLeftReverse);
        animatorSet.play(animRightReverse).after(animRight);

        animatorSet.setDuration(300);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animatorSet.start();
            }
        });
    }

    private void stopAlarmAnimation() {
        animatorSet.end();
    }

    private void sharePhotoByTelegramMessage(Uri uri) {
        final String appName = "org.telegram.messenger";
        final boolean isTelegramInstalled = isAppAvailable(this, appName);
        if (isTelegramInstalled) {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("image/jpeg");
            myIntent.setPackage(appName);
            myIntent.putExtra(Intent.EXTRA_STREAM, uri);
            this.startActivity(Intent.createChooser(myIntent, "Send With"));
        } else {
            showToast("no telegram installed");
        }

    }

    private static boolean isAppAvailable(Context context, String appName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public File createImageFile(){
        String timeStamp = new SimpleDateFormat("yyyyMMDD").format(new Date());
        String imageFileName = IMAGE_PREFIX + timeStamp + "_" + IMAGE_SUFFIX;
        File emptyImage = null;

        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){

           String filePath = Environment.getExternalStorageDirectory() + File.separator + "SnoozePhoto" + File.separator + "SnoozePhoto Images";
            File file = new File(filePath);

            if(!file.exists()){
                file.mkdirs();
            }

            emptyImage = new File(file,imageFileName);

            if(!emptyImage.exists()){

                try {
                    emptyImage.createNewFile();
                } catch (IOException e) {
                }
            }

        }else{
            showToast("SDcard Unavailable");
        }
        return emptyImage;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ringtone != null) {
            ringtone.stop();
        }
        stopAlarmAnimation();
        notificationManager.cancel(1);

    }
}
