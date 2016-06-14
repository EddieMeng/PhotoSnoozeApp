package xyz.photosnooze.messenger;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

/**
 * Created by shine on 16/6/12.
 */
public class DispatchQueue extends Thread{
    private volatile Handler mHandler = null;
    private CountDownLatch syncLatch = new CountDownLatch(1);

    public DispatchQueue(final String threadName) {
        setName(threadName);
        start();
    }

    public void canCelRunnable(Runnable runnable) {

    }

    public void postRunnable(Runnable runnable) {
        postRunnable(runnable, 0);
    }

    public void postRunnable(Runnable runnable, long delay) {
        Log.i("thread running3", "thread running3");
        try {
            syncLatch.await();
            if (delay <= 0) {
                mHandler.post(runnable);
            } else {
                mHandler.postDelayed(runnable, delay);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Log.i("thread running", "thread running");
        Looper.prepare();
        Log.i("thread running2", "thread running2");
        mHandler = new Handler();
        syncLatch.countDown();
        Looper.loop();
    }
}
