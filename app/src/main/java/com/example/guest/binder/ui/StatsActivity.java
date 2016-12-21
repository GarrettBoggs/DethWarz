package com.example.guest.binder.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class StatsActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.winnerText) TextView mWinnerText;
    @Bind(R.id.loserText) TextView mLoserText;
    @Bind(R.id.nextButton) Button mNextButton;
    @Bind(R.id.contactClick) TextView mContactClick;
    @Bind(R.id.victorButton) Button mVictorButton;
    @Bind(R.id.loserlosses) TextView mLoserLosses;
    @Bind(R.id.loserWins) TextView mLoserWins;
    @Bind(R.id.winnerWins) TextView mWinnerWins;
    @Bind(R.id.winnerLosses) TextView mWinnerLosses;
    @Bind(R.id.winnerWinPercent) TextView mWinnerWinPercent;
    @Bind(R.id.loserWinPercent) TextView mLoserWinPercent;
    @Bind(R.id.winnerImage) ImageView mWinnerImage;
    @Bind(R.id.loserImage) ImageView mLoserImage;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ButterKnife.bind(this);

        Context mContext = this.getBaseContext();

        Intent intent = getIntent();
        String winner = intent.getStringExtra("winner");
        String loser = intent.getStringExtra("loser");
        String winnerImage = intent.getStringExtra("winnerImage");
        String loserImage = intent.getStringExtra("loserImage");

        Picasso.with(mContext).load(winnerImage).into(mWinnerImage);
        Picasso.with(mContext).load(loserImage).into(mLoserImage);

        mWinnerText.setText(winner);
        mLoserText.setText(loser);

        mWinnerWins.setText(intent.getStringExtra("winnerWins"));
        mWinnerLosses.setText(intent.getStringExtra("winnerLosses"));
        mWinnerWinPercent.setText(intent.getStringExtra("winnerWinPercent") + "%");
        mLoserWins.setText(intent.getStringExtra("loserWins"));
        mLoserLosses.setText(intent.getStringExtra("loserLosses"));
        mLoserWinPercent.setText(intent.getStringExtra("loserWinPercent") + "%");

        mNextButton.setOnClickListener(this);
        mContactClick.setOnClickListener(this);
        mVictorButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == mNextButton) {
            Intent intent = new Intent(StatsActivity.this, CoverActivity.class);
            startActivity(intent);
        }

        else if (v == mContactClick) {
            Intent intent = new Intent(StatsActivity.this, ContactActivity.class);
            startActivity(intent);
        }

        else if (v == mVictorButton) {
            Intent intent = new Intent(StatsActivity.this, HistoryActivity.class);
            startActivity(intent);
        }


    }

}
