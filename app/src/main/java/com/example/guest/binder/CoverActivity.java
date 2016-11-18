package com.example.guest.binder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CoverActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.coolButton) Button mCoolButton;
    @Bind(R.id.lameButton) Button mLameButton;

    private String[] books = new String[] {"Eldest", "The Hobbit", "The Lightning Theif", "Dragon Rider"};
    private int agreementVal = 0;
    private String bookName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
        ButterKnife.bind(this);

        mCoolButton.setOnClickListener(this);
        mLameButton.setOnClickListener(this);

        //In future, bookName will be selected from API
        bookName = "Eragon";
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(CoverActivity.this, StatsActivity.class);
        intent.putExtra("books", books);
        intent.putExtra("bookName",bookName);

        if(v == mCoolButton){
            agreementVal = 80;
            String verdict = "COOL";
            intent.putExtra("verdict", verdict);
            intent.putExtra("agreementVal", agreementVal);
            startActivity(intent);
        }

        if(v == mLameButton){
            agreementVal = 20;
            String verdict = "LAME";
            intent.putExtra("verdict", verdict);
            intent.putExtra("agreementVal", agreementVal);
            startActivity(intent);
        }
    }
}
