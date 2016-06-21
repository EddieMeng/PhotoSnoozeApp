package xyz.photosnooze.entity;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by shine on 16/6/10.
 */
public class Contacts implements Serializable{
    private transient ImageView avatar;
    private String name;

    public Contacts(ImageView avatar, String name) {
        this.avatar = avatar;
        this.name = name;
    }

    public Contacts() {

    }

    public ImageView getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageView avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
