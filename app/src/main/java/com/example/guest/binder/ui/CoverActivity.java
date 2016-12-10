package com.example.guest.binder.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CoverActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.characterOneButton) Button mCharacterOneButton;
    @Bind(R.id.heroOneImage) ImageView mCharacterOneImage;
    @Bind(R.id.heroTwoImage) ImageView mCharacterTwoImage;
    @Bind(R.id.characterTwoButton) Button mCharacterTwoButton;
    @Bind(R.id.heroOneName) TextView mHeroOneName;
    @Bind(R.id.heroTwoName) TextView mHeroTwoName;

    boolean start = false;

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
                    long wins = (long) locationSnapshot.child("wins").getValue();
                    long losses = (long) locationSnapshot.child("losses").getValue();

                    Character temp = new Character(name, picture, desc, wins, losses);

                    mCharacters.add(temp);

                    if(mCharacters.size() > 2 && !start){
                        int guess = rn.nextInt(mCharacters.size());
                        mCharacterOne = mCharacters.get(guess);
                        mCharacterOneButton.setText(mCharacterOne.getName());
                        mHeroOneName.setText(mCharacterOne.getName());
                        new DownloadImageTask(mCharacterOneImage)
                                .execute(mCharacterOne.getPicture());
                       // Picasso.with(mContext).load(mCharacterOne.getPicture()).into(mCharacterImageView);

                        int guess2 = rn.nextInt(mCharacters.size());
                        mCharacterTwo = mCharacters.get(guess2);
                        mHeroTwoName.setText(mCharacterTwo.getName());
                        new DownloadImageTask(mCharacterTwoImage)
                                .execute(mCharacterTwo.getPicture());
                        mCharacterTwoButton.setText(mCharacterTwo.getName());


                        start = true;
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

        DatabaseReference mOneReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("allCharacters")
                .child(mCharacterOne.getName());

        DatabaseReference mTwoReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("allCharacters")
                .child(mCharacterTwo.getName());

        if(v == mCharacterOneButton){

            mCharacterOne.addWin();
            mCharacterTwo.addLoss();

            mOneReference.child("wins").setValue(mCharacterOne.getWins());
            mTwoReference.child("losses").setValue(mCharacterTwo.getLosses());

            intent.putExtra("winner", mCharacterOne.getName());
            intent.putExtra("loser", mCharacterTwo.getName());
            startActivity(intent);
        }

        if(v == mCharacterTwoButton){

            mCharacterTwo.addWin();
            mCharacterOne.addLoss();

            mTwoReference.child("wins").setValue(mCharacterTwo.getWins());
            mOneReference.child("losses").setValue(mCharacterOne.getLosses());

            intent.putExtra("winner", mCharacterTwo.getName());
            intent.putExtra("loser", mCharacterOne.getName());
            startActivity(intent);
        }
    }

    public void addWinToFirebase(String wins) {
        int intw = Integer.parseInt(wins);
        intw += 1;

        mWinsReference.push().setValue(Integer.toString(intw));
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
