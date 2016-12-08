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

import com.example.guest.binder.Constants;
import com.example.guest.binder.adapters.CharacterListAdapter;
import com.example.guest.binder.R;
import com.example.guest.binder.services.BombService;
import com.example.guest.binder.models.Character;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    private DatabaseReference mWinsReference;

    public ArrayList<Character> mCharacters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mWinsReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_WINS);

        mWinsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    String location = locationSnapshot.getValue().toString();
                    Log.d("Wins updated", "wins:");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

            addWinToFirebase("1");

            DatabaseReference mWinsReference = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_CHARACTERS);
            mWinsReference.push().setValue(mCharacters.get(0));

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
        bombService.findCharacter(new Callback() {

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

                            if(mCharacters.get(0).getName().equals(mCharacters.get(1).getName()) ) {
                                mCharacters.remove(1);
                                getCharacter();
                            }

                            if(mCharacters.size() > 1){
                                mCharacterTwoButton.setText(mCharacters.get(1).getName());
                            }

                        }

                    }

                });
            }
        });
    }

    public void addWinToFirebase(String wins) {
        int intw = Integer.parseInt(wins);
        intw += 1;

        mWinsReference.push().setValue(Integer.toString(intw));
    }

}
