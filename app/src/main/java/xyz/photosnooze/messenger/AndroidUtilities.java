package xyz.photosnooze.messenger;

/**
 * Created by shine on 16/6/13.
 */
public class AndroidUtilities {


    public static void runOnUIThread(Runnable runnable) {
        runOnUIThread(runnable, 0);
    }


    public static void runOnUIThread(Runnable runnable, long delay) {
        if (delay == 0) {
            ApplicationLoader.applicationHandler.post(runnable);
        } else {
            ApplicationLoader.applicationHandler.postDelayed(runnable, delay);
        }

    }


}
