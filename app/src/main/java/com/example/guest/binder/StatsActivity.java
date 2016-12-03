package com.example.guest.binder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StatsActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.verdictText) TextView mVerdictText;
    @Bind(R.id.similarCovers) ListView mSimilarCovers;
    @Bind(R.id.nextButton) Button mNextButton;
    @Bind(R.id.progressBar) ProgressBar mRatingBar;
    @Bind(R.id.percentText) TextView mPercentText;
    @Bind(R.id.bookNameText) TextView mBookNameText;
    @Bind(R.id.contactClick) TextView mContactClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String verdict = intent.getStringExtra("verdict");
        String bookName = intent.getStringExtra("bookName");
        String[] books = intent.getStringArrayExtra("books");
        Integer agreementVal = intent.getIntExtra("agreementVal", 0);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, books);
        mSimilarCovers.setAdapter(adapter);

        mVerdictText.setText(verdict);
        mBookNameText.setText("Covers Similar to " + bookName);
        mPercentText.setText(String.valueOf(agreementVal));

        mNextButton.setOnClickListener(this);
        mContactClick.setOnClickListener(this);

        mRatingBar.setProgress(agreementVal);

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


    }

}
