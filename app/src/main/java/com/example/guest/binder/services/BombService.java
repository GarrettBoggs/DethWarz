package com.example.guest.binder.services;


import com.example.guest.binder.Constants;
import com.example.guest.binder.models.Character;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Guest on 12/2/16.
 */
public class BombService {

    public static void findBooks(Callback callback){
        String[] allCharacters = { "3005-34048" , "3005-33968", "3005-33901", "3005-693", "3005-2972" , "3005-73", "3005-5", "3005-13", "3005-24" , "3005-69","3005-22", "3005-7", "3005-10", "3005-99", "3005-18", "3005-121", "3005-63" };
        Random rn = new Random();
        int guess = rn.nextInt(17);

        OkHttpClient client = new OkHttpClient.Builder()
                .build();


        String id = allCharacters[guess] + "/?";

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.BOMB_BASE_URL + allCharacters[guess]).newBuilder();
        urlBuilder.addQueryParameter("api_key", Constants.BOMB_KEY);
        urlBuilder.addQueryParameter("format", "json");
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

    }

    public Character proccessResults(Response response) {

        Character character = new Character("blank", "blank", "blank");

        try{
            String jsonData = response.body().string();
            if(response.isSuccessful())
            {
                JSONObject totalJSON = new JSONObject(jsonData);
                JSONObject bookJSON = totalJSON.getJSONObject("results");
                JSONObject imageJSON = bookJSON.getJSONObject("image");


                String name = bookJSON.getString("name");
                String desc = bookJSON.getString("deck");
                String super_url = imageJSON.getString("small_url");

                character = new Character(name , super_url, desc);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return character;
    }

}
