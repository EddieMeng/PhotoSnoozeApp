package xyz.photosnooze.messenger;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by shine on 16/6/10.
 */
public class ApplicationLoader extends Application {
    public static volatile Context applicatioonContext;
    public static volatile Handler applicationHandler;


    @Override
    public void onCreate() {
        super.onCreate();
        applicatioonContext = getApplicationContext();
        applicationHandler = new Handler(applicatioonContext.getMainLooper());
    }


    public static void postInitApplication() {
        ContactsController.getInstance().readContacts(applicatioonContext);
    }


}
