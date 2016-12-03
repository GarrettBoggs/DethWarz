package com.example.guest.binder.models;

import android.util.Log;

/**
 * Created by Guest on 12/2/16.
 */
public class Character {
    public String mName;
    public String mDescription;
    public String mPicture;

    public Character(String name, String picture, String description){
        this.mName = name;
        this.mPicture = picture;
        this.mDescription = description;
    }

    public String getName(){
        return mName;
    }

    public String getPicture(){
        return mPicture;
    }

    public String getDescription(){
        return mDescription;
    }
}
