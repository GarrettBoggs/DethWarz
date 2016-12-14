package com.example.guest.binder.ui;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Interpolator;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Scroller;
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

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CoverActivity extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener {

    @Bind(R.id.heroOneImage) ImageView mCharacterOneImage;
    @Bind(R.id.heroTwoImage) ImageView mCharacterTwoImage;
    @Bind(R.id.heroOneName) TextView mHeroOneName;
    @Bind(R.id.heroTwoName) TextView mHeroTwoName;
    @Bind(R.id.heroOneDescription) TextView mHeroOneDescription;
    @Bind(R.id.heroTwoDescription) TextView mHeroTwoDescription;

    boolean dead = false;

    private Context mContext;

    Random rn = new Random();

    private DatabaseReference mWinsReference;
    private DatabaseReference mWinsReferenceTwo;

    public Character mCharacterOne;
    public Character mCharacterTwo;

    public List<String> characterNames = Arrays.asList("Abe Lincoln", "Ash Ketchum", "Banjo Kazooie", "Big Bird", "Bigfoot", "Bill Clinton", "Boo", "Britney Spears", "Bugs Bunny", "Chuck Norris", "Cloud", "Companion Cube", "Darth Vader", "Dracula", "Dumbledore", "Eragon", "Ernest Hemmingway", "Fred Flintstone", "Frodo", "Gandalf", "HanSolo", "Harley Quinn", "James Bond", "Link", "Luke Skywalker", "Mario", "Megaman", "Mr Mime", "Mr T", "Pikachu", "Rick Grimes", "Robin Hood", "Sonic", "Spiderman", "Spongebob", "Superman", "The Flash","The Hulk", "Tiger Woods", "Tigger", "Tracer", "Vegeta", "Wonder Woman", "Yoda", "Yoshi", "Zelda", "Zeus") ;

    Animation performAnimation, LoseAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = getBaseContext();

        int guessSize = characterNames.size() - 1;
        int guess = rn.nextInt(guessSize);
        int guess2;

        Log.d("Turtles!" , characterNames.get(guess));

        mWinsReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("allCharacters")
                .child(characterNames.get(guess));

        do{
            guess2 = rn.nextInt(guessSize);
        } while (guess == guess2);

        Log.d("Turtles!" , characterNames.get(guess2));

        mWinsReferenceTwo = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("allCharacters")
                .child(characterNames.get(guess2));

        mWinsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    String name = (String) dataSnapshot.child("name").getValue();
                    String desc = (String)  dataSnapshot.child("description").getValue();
                    String picture = (String)  dataSnapshot.child("picture").getValue();
                    long wins = (long)  dataSnapshot.child("wins").getValue();
                    long losses = (long)  dataSnapshot.child("losses").getValue();

                    Character temp = new Character(name, picture, desc, wins, losses);

                    mCharacterOne = temp;
                    mHeroOneName.setText(mCharacterOne.getName());
                    mHeroOneDescription.setText(mCharacterOne.getDescription());

                    Picasso.with(mContext).load(mCharacterOne.getPicture()).into(mCharacterOneImage);

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mWinsReferenceTwo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = (String) dataSnapshot.child("name").getValue();
                String desc = (String)  dataSnapshot.child("description").getValue();
                String picture = (String)  dataSnapshot.child("picture").getValue();
                long wins = (long)  dataSnapshot.child("wins").getValue();
                long losses = (long)  dataSnapshot.child("losses").getValue();

                Character temp = new Character(name, picture, desc, wins, losses);

                mCharacterTwo = temp;
                mHeroTwoName.setText(mCharacterTwo.getName());
                mHeroTwoDescription.setText(mCharacterTwo.getDescription());

                Picasso.with(mContext).load(mCharacterTwo.getPicture()).into(mCharacterTwoImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //mWinsReference.child("1").child("name").setValue("spiderman");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
        ButterKnife.bind(this);

        mCharacterOneImage.setOnClickListener(this);
        mCharacterTwoImage.setOnClickListener(this);

        performAnimation = AnimationUtils.loadAnimation(this, R.anim.move_one);
        performAnimation.setRepeatCount(1);

        LoseAnimation = AnimationUtils.loadAnimation(this, R.anim.move_left);
        LoseAnimation.setRepeatCount(1);

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private static final int SWIPE_MIN_DISTANCE = 50;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;

    @Override
    public void onClick(View v){


        final Intent intent = new Intent(CoverActivity.this, StatsActivity.class);

        LoseAnimation.setFillAfter(true);
        performAnimation.setFillAfter(true);

        performAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(intent);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        LoseAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(intent);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

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

        if(v == mCharacterOneImage){
            intent.putExtra("winner", mCharacterOne.getName());
            intent.putExtra("winnerWins", mCharacterOne.getStringWins());
            intent.putExtra("winnerLosses", mCharacterOne.getStringLosses());
            intent.putExtra("winnerWinPercent", mCharacterOne.calculateWin());
            intent.putExtra("loserWins", mCharacterTwo.getStringWins());
            intent.putExtra("loserLosses", mCharacterTwo.getStringLosses());
            intent.putExtra("loserWinPercent", mCharacterTwo.calculateWin());
            intent.putExtra("loser", mCharacterTwo.getName());

            if(!dead) {

                mCharacterOne.addWin();
                mCharacterTwo.addLoss();

                mCharacterOneImage.startAnimation(performAnimation);
                mCharacterTwoImage.startAnimation(performAnimation);
                dead = true;

            }

            mOneReference.child("wins").setValue(mCharacterOne.getWins());
            mTwoReference.child("losses").setValue(mCharacterTwo.getLosses());


        }

        if(v == mCharacterTwoImage){
            if(!dead) {
                mCharacterTwo.addWin();
                mCharacterOne.addLoss();

                mCharacterOneImage.startAnimation(LoseAnimation);
                mCharacterTwoImage.startAnimation(LoseAnimation);
                dead = true;
            }

            mTwoReference.child("wins").setValue(mCharacterTwo.getWins());
            mOneReference.child("losses").setValue(mCharacterOne.getLosses());

            intent.putExtra("winner", mCharacterTwo.getName());
            intent.putExtra("winnerWins", mCharacterTwo.getStringWins());
            intent.putExtra("winnerLosses", mCharacterTwo.getStringLosses());
            intent.putExtra("loserWins", mCharacterOne.getStringWins());
            intent.putExtra("loserLosses", mCharacterOne.getStringLosses());
            intent.putExtra("loser", mCharacterOne.getName());
            intent.putExtra("loserWinPercent", mCharacterOne.calculateWin());
            intent.putExtra("winnerWinPercent", mCharacterTwo.calculateWin());
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
