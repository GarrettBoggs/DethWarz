package com.example.guest.binder.models;

import android.util.Log;
import org.parceler.Parcel;

/**
 * Created by Guest on 12/2/16.
 */

@Parcel
public class Character {
    public String name;
    public String description;
    public String picture;
    public String wins = "0";
    public String losses = "0";
    private String pushId;

    public Character(String name, String picture, String description){
        this.name = name;
        this.picture = picture;
        this.description = description;
    }

    public Character() {};

    public String getName(){
        return name;
    }

    public String getPicture(){
        return picture;
    }

    public String getDescription(){
        return description;
    }

    public String getWins() { return wins; }

    public String getLosses() { return losses; }

    public void setPushId(String pushId){
        this.pushId = pushId;
    }

    public String getPushId() {
        return pushId;
    }
}
