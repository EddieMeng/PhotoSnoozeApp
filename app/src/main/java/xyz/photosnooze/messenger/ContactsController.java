package xyz.photosnooze.messenger;

import android.content.Context;
import android.widget.ImageView;

import java.util.ArrayList;

import xyz.photosnooze.R;
import xyz.photosnooze.entity.Contacts;

/**
 * Created by shine on 16/6/10.
 */
public class ContactsController {
    public ArrayList<Contacts> contactsList = new ArrayList<>();


    private static volatile ContactsController Instance = null;

    public static ContactsController getInstance() {
        ContactsController localInstance = Instance;
        if (localInstance == null) {
            synchronized (ContactsController.class) {
                localInstance = Instance;
                if (localInstance == null) {
                     Instance = localInstance = new ContactsController();
                }

            }
        }
        return localInstance;
    }

    public void readContacts(Context context) {
        for (int i = 0; i < 10; i++) {
            Contacts contacts = new Contacts();
            contacts.setName("Contacts" + i);
            ImageView avatar = new ImageView(context);
            avatar.setImageResource(R.mipmap.ic_launcher);
            contacts.setAvatar(avatar);
            contactsList.add(contacts);
        }
        ApplicationLoader.readContacts = true;
    }






}
