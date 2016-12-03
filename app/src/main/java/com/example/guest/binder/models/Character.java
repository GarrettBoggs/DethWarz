package com.example.guest.binder.models;

import android.util.Log;

/**
 * Created by Guest on 12/2/16.
 */
public class Character {
    public String mName;
    public String mPicture;

    public Character(String name, String picture){
        this.mName = name;
        this.mPicture = picture;

    }

    public String getName(){
        return mName;
    }

    public String getPicture(){

        return mPicture;
    }
}
