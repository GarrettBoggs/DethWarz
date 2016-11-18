package com.example.guest.binder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StatsActivity extends AppCompatActivity {

    @Bind(R.id.verdictText) TextView mVerdictText;
    @Bind(R.id.similarCovers) ListView mSimilarCovers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String verdict = intent.getStringExtra("verdict");
        String[] books = intent.getStringArrayExtra("books");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, books);
        mSimilarCovers.setAdapter(adapter);

        mVerdictText.setText(verdict);
    }
}
