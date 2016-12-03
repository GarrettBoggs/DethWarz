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

import com.example.guest.binder.adapters.CharacterListAdapter;
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


    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.characterOneButton) Button mCharacterOneButton;
    @Bind(R.id.characterTwoButton) Button mCharacterTwoButton;

    private CharacterListAdapter mAdapter;

    public ArrayList<Character> mCharacters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
        ButterKnife.bind(this);

        getCharacter();
        getCharacter();

        mCharacterOneButton.setOnClickListener(this);
        mCharacterTwoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(CoverActivity.this, StatsActivity.class);

        if(v == mCharacterOneButton){
            intent.putExtra("winner", mCharacters.get(0).getName());
            intent.putExtra("loser", mCharacters.get(1).getName());
            startActivity(intent);
        }

        if(v == mCharacterTwoButton){
            intent.putExtra("winner", mCharacters.get(1).getName());
            intent.putExtra("loser", mCharacters.get(0).getName());
            startActivity(intent);
        }
    }

    private void getCharacter() {
        final BombService bombService = new BombService();
        bombService.findBooks(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mCharacters.add(bombService.proccessResults(response));

                CoverActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            mAdapter = new CharacterListAdapter(getApplicationContext(), mCharacters);
                            mRecyclerView.setAdapter(mAdapter);
                            RecyclerView.LayoutManager layoutManager =
                                    new LinearLayoutManager(CoverActivity.this);
                            mRecyclerView.setLayoutManager(layoutManager);
                            mRecyclerView.setHasFixedSize(false);

                        mCharacterOneButton.setText(mCharacters.get(0).getName());
                        if(mCharacters.size() > 1){
                            mCharacterTwoButton.setText(mCharacters.get(1).getName());
                        }

                    }

                });
            }
        });
    }

}
