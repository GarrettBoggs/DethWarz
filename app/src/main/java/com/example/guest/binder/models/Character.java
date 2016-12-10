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
    public long wins = 0;
    public long losses = 0;
    private String pushId;

    public Character(String name, String picture, String description){
        this.name = name;
        this.picture = picture;
        this.description = description;
    }

    public Character(String name, String picture, String description, long wins, long losses){
        this.name = name;
        this.picture = picture;
        this.description = description;
        this.wins = wins;
        this.losses = losses;
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

    public long getWins() { return wins; }

    public long getLosses() { return losses; }

    public void setPushId(String pushId){
        this.pushId = pushId;
    }

    public String getPushId() {
        return pushId;
    }

    public void addWin() {
        wins += 1;
    }

    public void addLoss() {
        losses += 1;
    }
}
