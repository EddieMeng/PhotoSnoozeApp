package xyz.photosnooze.messenger;

import xyz.photosnooze.entity.SnoozePhotoAlarm;
import xyz.photosnooze.sqlite.PSSQLiteHelper;

/**
 * Created by shine on 16/6/12.
 */
public class MessageStorage{
    private DispatchQueue storageQueue = new DispatchQueue("storageQueue");
    private PSSQLiteHelper sqliteHelper;


    private static volatile MessageStorage Instance = null;
    public static MessageStorage getInstacne() {
        MessageStorage localInstance = Instance;
        if (localInstance == null) {
            synchronized (MessageStorage.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new MessageStorage();
                }
            }
        }
        return localInstance;
    }

    private MessageStorage() {
        storageQueue.setPriority(Thread.MAX_PRIORITY);
        openDataBase();
    }

    public DispatchQueue getStorageQueue() {
        return storageQueue;
    }

    private void openDataBase() {
        sqliteHelper = new PSSQLiteHelper(ApplicationLoader.applicatioonContext);
    }


    public void saveAlarm(final SnoozePhotoAlarm alarm) {
        storageQueue.postRunnable(new Runnable() {
            @Override
            public void run() {
                sqliteHelper.createAlarm(alarm);
            }
        });
    }

    public void getAllAlarms() {
        storageQueue.postRunnable(new Runnable() {
            @Override
            public void run() {
                sqliteHelper.readAllAlarm();
            }
        });
    }

    public void deleteAlarm(final SnoozePhotoAlarm alarm) {
        storageQueue.postRunnable(new Runnable() {
            @Override
            public void run() {
                sqliteHelper.deleteAlarm(alarm);
            }
        });

    }




}
