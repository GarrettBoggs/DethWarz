package com.example.guest.binder.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.guest.binder.CharacterListAdapter;
import com.example.guest.binder.R;
import com.example.guest.binder.services.BombService;
import com.example.guest.binder.models.Character;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CoverActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.coolButton) Button mCoolButton;
    @Bind(R.id.lameButton) Button mLameButton;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    private CharacterListAdapter mAdapter;

    public ArrayList<Character> mCharacters = new ArrayList<>();

    private String[] books = new String[] {"Eldest", "The Hobbit", "The Lightning Thief", "Dragon Rider"};
    private int agreementVal = 0;
    private String bookName = "";

    Random rn = new Random();
    private int randomNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
        ButterKnife.bind(this);

        mCoolButton.setOnClickListener(this);
        mLameButton.setOnClickListener(this);

        getBooks("Eragon");
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(CoverActivity.this, StatsActivity.class);
        intent.putExtra("books", books);
        intent.putExtra("bookName",bookName);

        if(v == mCoolButton){
            agreementVal = 80;
            String verdict = "COOL";
            intent.putExtra("verdict", verdict);
            intent.putExtra("agreementVal", agreementVal);
            startActivity(intent);
            Toast.makeText(CoverActivity.this, bookName + "'s cover is " + verdict + "!", Toast.LENGTH_SHORT).show();
        }

        if(v == mLameButton){
            agreementVal = 20;
            String verdict = "LAME";
            intent.putExtra("verdict", verdict);
            intent.putExtra("agreementVal", agreementVal);
            startActivity(intent);
            Toast.makeText(CoverActivity.this, bookName + "'s cover is " + verdict + "!", Toast.LENGTH_SHORT).show();
        }

    }

    private void getBooks(String keyword){
        final BombService amazonService = new BombService();
        amazonService.findBooks(keyword, new Callback() {

            @Override
            public void onFailure(Call call, IOException e){
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                        mCharacters = amazonService.proccessResults(response);

                        Log.v("Test this" , mCharacters.get(0).getName());
                CoverActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new CharacterListAdapter(getApplicationContext(), mCharacters);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(CoverActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }

                });
            }
        });
    }
}
