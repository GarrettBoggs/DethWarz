package com.example.guest.binder;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;


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

}
