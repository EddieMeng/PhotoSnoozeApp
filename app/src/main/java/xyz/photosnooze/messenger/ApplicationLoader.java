package xyz.photosnooze.messenger;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by shine on 16/6/10.
 */
public class ApplicationLoader extends Application {
    public static volatile Context applicationContext;
    public static volatile Handler applicationHandler;

    public volatile static boolean readContacts;


    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        applicationHandler = new Handler(applicationContext.getMainLooper());
    }


    public static void postInitApplication() {
        synchronized (ApplicationLoader.class) {
            if (!readContacts) {
                ContactsController.getInstance().readContacts(applicationContext);
            }
        }
    }


}
