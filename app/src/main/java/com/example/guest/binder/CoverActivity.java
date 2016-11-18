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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
        ButterKnife.bind(this);

        mCoolButton.setOnClickListener(this);
        mLameButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(CoverActivity.this, StatsActivity.class);

        if(v == mCoolButton){
            String verdict = "COOL";
            intent.putExtra("verdict", verdict);
            intent.putExtra("books", books);
            startActivity(intent);
        }

        if(v == mLameButton){
            String verdict = "LAME";
            intent.putExtra("verdict", verdict);
            intent.putExtra("books", books);
            startActivity(intent);
        }
    }
}
