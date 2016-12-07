package com.example.guest.binder.ui;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.guest.binder.R;
import com.example.guest.binder.adapters.CharacterListAdapter;
import com.example.guest.binder.models.Character;
import com.example.guest.binder.services.BombService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CharacterActivity extends AppCompatActivity {

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private CharacterListAdapter mAdapter;

    public ArrayList<Character> mCharacters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);
        ButterKnife.bind(this);

        getCharacters("koopa");
    }

    private void getCharacters(String name) {
        final BombService bombService = new BombService();
        bombService.findAllCharacters(name, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                        mCharacters = bombService.proccessAllResults(response);

                CharacterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                            public void run() {
                    mAdapter = new CharacterListAdapter(getApplicationContext(), mCharacters);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(CharacterActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);

                    }
                });

            }
        });
    }

}
