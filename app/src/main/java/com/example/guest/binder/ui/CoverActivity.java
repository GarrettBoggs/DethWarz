package com.example.guest.binder.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.ImageView;

import android.widget.TextView;


import com.example.guest.binder.R;
import com.example.guest.binder.models.Character;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CoverActivity extends AppCompatActivity implements View.OnClickListener {

    private GestureDetectorCompat mDetector;

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

    Animation performAnimation, LoseAnimation, rightStrong, leftStrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = getBaseContext();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mCharacterOne = Parcels.unwrap(intent.getParcelableExtra("charOne"));
        mCharacterTwo = Parcels.unwrap(intent.getParcelableExtra("charTwo"));

        performAnimation = AnimationUtils.loadAnimation(this, R.anim.move_one);
        performAnimation.setRepeatCount(1);

        LoseAnimation = AnimationUtils.loadAnimation(this, R.anim.move_left);
        LoseAnimation.setRepeatCount(1);

        leftStrong = AnimationUtils.loadAnimation(this, R.anim.move_left_strong);
        leftStrong.setRepeatCount(1);

        rightStrong = AnimationUtils.loadAnimation(this, R.anim.move_right_strong);
        rightStrong.setRepeatCount(1);

        LoseAnimation.setFillAfter(true);
        leftStrong.setFillAfter(true);
        rightStrong.setFillAfter(true);
        performAnimation.setFillAfter(true);

        mCharacterOneImage.setOnClickListener(this);
        mCharacterTwoImage.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mHeroOneName.setText(mCharacterOne.getName());
        mHeroOneDescription.setText(mCharacterOne.getDescription());
        Picasso.with(mContext).load(mCharacterOne.getPicture()).into(mCharacterOneImage);

        mHeroTwoName.setText(mCharacterTwo.getName());
        mHeroTwoDescription.setText(mCharacterTwo.getDescription());
        Picasso.with(mContext).load(mCharacterTwo.getPicture()).into(mCharacterTwoImage);

        mWinsReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("allCharacters")
                .child(mCharacterOne.getName());

        mWinsReferenceTwo = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("allCharacters")
                .child(mCharacterTwo.getName());
    }



    private static final int SWIPE_MIN_DISTANCE = 50;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;

        @Override
        public void onClick(View v){ {

            final Intent intent = new Intent(CoverActivity.this, StatsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            performAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mCharacterOne.addWin();
                    mCharacterTwo.addLoss();

                    intent.putExtra("winner", mCharacterOne.getName());
                    intent.putExtra("winnerWins", mCharacterOne.getStringWins());
                    intent.putExtra("winnerLosses", mCharacterOne.getStringLosses());
                    intent.putExtra("winnerWinPercent", mCharacterOne.calculateWin());
                    intent.putExtra("loserWins", mCharacterTwo.getStringWins());
                    intent.putExtra("loserLosses", mCharacterTwo.getStringLosses());
                    intent.putExtra("loserWinPercent", mCharacterTwo.calculateWin());
                    intent.putExtra("loser", mCharacterTwo.getName());
                    intent.putExtra("winnerImage", mCharacterOne.getPicture());
                    intent.putExtra("loserImage", mCharacterTwo.getPicture());

                    mWinsReference.child("wins").setValue(mCharacterOne.getWins());
                    mWinsReferenceTwo.child("losses").setValue(mCharacterTwo.getLosses());
                    mWinsReference.child("winrate").setValue(mCharacterOne.calculateWin());
                    mWinsReferenceTwo.child("winrate").setValue(mCharacterTwo.calculateWin());

                    startActivity(intent);
                    finish();

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
                    mCharacterTwo.addWin();
                    mCharacterOne.addLoss();

                    mWinsReference.child("losses").setValue(mCharacterOne.getLosses());
                    mWinsReferenceTwo.child("wins").setValue(mCharacterTwo.getWins());

                    mWinsReference.child("winrate").setValue(mCharacterOne.calculateWin());
                    mWinsReferenceTwo.child("winrate").setValue(mCharacterTwo.calculateWin());

                    intent.putExtra("winner", mCharacterTwo.getName());
                    intent.putExtra("winnerWins", mCharacterTwo.getStringWins());
                    intent.putExtra("winnerLosses", mCharacterTwo.getStringLosses());
                    intent.putExtra("loserWins", mCharacterOne.getStringWins());
                    intent.putExtra("loserLosses", mCharacterOne.getStringLosses());
                    intent.putExtra("loser", mCharacterOne.getName());
                    intent.putExtra("loserWinPercent", mCharacterOne.calculateWin());
                    intent.putExtra("winnerWinPercent", mCharacterTwo.calculateWin());
                    intent.putExtra("winnerImage", mCharacterTwo.getPicture());
                    intent.putExtra("loserImage", mCharacterOne.getPicture());

                    startActivity(intent);
                    finish();

                }



                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });


            if(v == mCharacterTwoImage && !dead) {
                dead = true;
                mCharacterOneImage.startAnimation(leftStrong);
                mCharacterTwoImage.startAnimation(LoseAnimation);
            }

            if(v == mCharacterOneImage && !dead){
                    dead = true;
                    mCharacterOneImage.startAnimation(performAnimation);
                    mCharacterTwoImage.startAnimation(rightStrong);
                }


        }

    }



}
