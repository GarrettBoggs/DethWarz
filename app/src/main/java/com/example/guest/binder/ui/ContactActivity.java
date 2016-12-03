package com.example.guest.binder.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.guest.binder.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.phoneButton) Button mPhoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);

        mPhoneButton.setOnClickListener(this);
    }

    @Override()
        public void onClick(View v){
            if(v == mPhoneButton){
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel: 304-550-9798"));
                startActivity(phoneIntent);
            }
        }

}
