package xyz.photosnooze.messenger;

import android.os.Handler;
import android.os.Looper;

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
        Looper.prepare();
        mHandler = new Handler();
        syncLatch.countDown();
        Looper.loop();
    }
}
