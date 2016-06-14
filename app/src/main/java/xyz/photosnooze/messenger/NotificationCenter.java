package xyz.photosnooze.messenger;

import android.util.SparseArray;

import java.util.ArrayList;

/**
 * Created by shine on 16/6/13.
 */
public class NotificationCenter {
    private static int totalEvent = 1;

    public static final int snoozePhotoAlarmDidLoaded = totalEvent++;
    public static final int snoozePhotoAlarmDidDeleted = totalEvent++;

    private static volatile NotificationCenter Instance = null;

    private SparseArray<ArrayList<Object>> observers = new SparseArray<>();

    public interface NotificationDelegate {
        void didReceivedNotification(int id, Object... args);
    }


    public static NotificationCenter getInstance() {
        NotificationCenter localInstance = Instance;
        if (localInstance == null) {
            synchronized (NotificationCenter.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new NotificationCenter();
                }
            }
        }
        return localInstance;
    }

    public void postNotification(int id, Object... args) {
         ArrayList<Object> objects = observers.get(id);

        if (objects != null && !objects.isEmpty()) {
            for (Object obejct : objects) {
                ((NotificationDelegate)obejct).didReceivedNotification(id, args);
            }
        }


    }

    public void addObserver(Object observer, int id) {
       ArrayList<Object> objects = observers.get(id);
        if (objects == null) {
            observers.put(id, objects = new ArrayList<>());
        }

        if (objects.contains(observer)) {
            return;
        }
        objects.add(observer);

    }


    public void removeObserver(Object observer, int id) {
        ArrayList<Object> objects = observers.get(id);
        if (objects != null) {
            objects.remove(observer);
        }
    }





}
