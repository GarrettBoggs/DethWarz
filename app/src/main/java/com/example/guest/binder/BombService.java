package com.example.guest.binder;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

    public static void findBooks(String keyword , Callback callback){

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        String id = "3005-34167" + "/?";

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.BOMB_BASE_URL + id).newBuilder();
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

                String name = bookJSON.getString("name");
                String super_url = bookJSON.getString("name");

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
