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
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.binder.Constants;
import com.example.guest.binder.adapters.CharacterListAdapter;
import com.example.guest.binder.R;
import com.example.guest.binder.services.BombService;
import com.example.guest.binder.models.Character;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    @Bind(R.id.characterOneButton) Button mCharacterOneButton;
    @Bind(R.id.characterTwoButton) Button mCharacterTwoButton;
    @Bind(R.id.heroOneName) TextView mHeroOneName;
    @Bind(R.id.heroTwoName) TextView mHeroTwoName;

    Random rn = new Random();

    private CharacterListAdapter mAdapter;

    private DatabaseReference mWinsReference;

    public Character mCharacterOne;
    public Character mCharacterTwo;

    public ArrayList<Character> mCharacters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mWinsReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("allCharacters");

        mWinsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    String name = (String) locationSnapshot.child("name").getValue();
                    String desc = (String) locationSnapshot.child("description").getValue();
                    String picture = (String) locationSnapshot.child("picture").getValue();

                    Character temp = new Character(name, picture, desc);

                    mCharacters.add(temp);

                    if(mCharacters.size() > 2){
                        int guess = rn.nextInt(mCharacters.size());
                        mCharacterOne = mCharacters.get(guess);
                        mHeroOneName.setText(mCharacterOne.getName());
                        int guess2 = rn.nextInt(mCharacters.size());
                        mCharacterTwo = mCharacters.get(guess2);
                        mHeroTwoName.setText(mCharacterTwo.getName());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //mWinsReference.child("1").child("name").setValue("spiderman");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
        ButterKnife.bind(this);

        mCharacterOneButton.setOnClickListener(this);
        mCharacterTwoButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(CoverActivity.this, StatsActivity.class);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        DatabaseReference mWinsReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_CHARACTERS)
                .child(uid);

        DatabaseReference pushRef = mWinsReference.push();
        String pushId = pushRef.getKey();

        if(v == mCharacterOneButton){

            mCharacters.get(0).setPushId(pushId);
            pushRef.setValue( mCharacters.get(0));

            intent.putExtra("winner", mCharacters.get(0).getName());
            intent.putExtra("loser", mCharacters.get(1).getName());
            startActivity(intent);
        }

        if(v == mCharacterTwoButton){

            mCharacters.get(1).setPushId(pushId);
            pushRef.setValue( mCharacters.get(1));

            intent.putExtra("winner", mCharacters.get(1).getName());
            intent.putExtra("loser", mCharacters.get(0).getName());
            startActivity(intent);
        }
    }

    public void addWinToFirebase(String wins) {
        int intw = Integer.parseInt(wins);
        intw += 1;

        mWinsReference.push().setValue(Integer.toString(intw));
    }

}
