package com.example.guest.binder;


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

    Random rn = new Random();
    int guess = rn.nextInt(6);

    public static void findBooks(String keyword , Callback callback){
        String[] allCharacters = {"3005-34077" , "3005-34048" , "3005-33968", "3005-33901", "3005-693", "3005-2972"};
        Random rn = new Random();
        int guess = rn.nextInt(6);

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

    public ArrayList<Character> proccessResults(Response response) {
        ArrayList<Character> characters = new ArrayList<>();

        try{
            String jsonData = response.body().string();
            if(response.isSuccessful())
            {
                JSONObject totalJSON = new JSONObject(jsonData);
                JSONObject bookJSON = totalJSON.getJSONObject("results");
                JSONObject imageJSON = bookJSON.getJSONObject("image");


                String name = bookJSON.getString("name");
                String super_url = imageJSON.getString("super_url");

                Character character = new Character(name , super_url);

                characters.add(character);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return characters;
    }

}
