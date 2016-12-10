package com.example.guest.binder.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.guest.binder.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StatsActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.winnerText) TextView mWinnerText;
    @Bind(R.id.loserText) TextView mLoserText;
    @Bind(R.id.nextButton) Button mNextButton;
    @Bind(R.id.contactClick) TextView mContactClick;
    @Bind(R.id.victorButton) Button mVictorButton;
    @Bind(R.id.characterButton) Button  mCharacterButton;
    @Bind(R.id.loserlosses) TextView mLoserLosses;
    @Bind(R.id.loserWins) TextView mLoserWins;
    @Bind(R.id.winnerWins) TextView mWinnerWins;
    @Bind(R.id.winnerLosses) TextView mWinnerLosses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String winner = intent.getStringExtra("winner");
        String loser = intent.getStringExtra("loser");

        mWinnerText.setText(winner);
        mLoserText.setText(loser);


        mWinnerWins.setText(intent.getStringExtra("winnerWins"));
        mWinnerLosses.setText(intent.getStringExtra("winnerLosses"));
        mLoserWins.setText(intent.getStringExtra("loserWins"));
        mLoserLosses.setText(intent.getStringExtra("loserLosses"));

        mNextButton.setOnClickListener(this);
        mContactClick.setOnClickListener(this);
        mCharacterButton.setOnClickListener(this);
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

        else if (v == mCharacterButton) {
            Intent intent = new Intent(StatsActivity.this, CharacterActivity.class);
            startActivity(intent);
        }

        else if (v == mVictorButton) {
            Intent intent = new Intent(StatsActivity.this, HistoryActivity.class);
            startActivity(intent);
        }


    }

}
