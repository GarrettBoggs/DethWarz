package com.example.guest.binder.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guest.binder.R;
import com.example.guest.binder.models.Character;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.loginButton) Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.d("What ??" , "aang" + R.raw.setokaibasound);
        Log.d("What ??" , "lincoln" + R.raw.seabiscuitsound);

        mLoginButton.setOnClickListener(this);
        
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(MainActivity.this, CoverActivity.class);

        if(v == mLoginButton) {

            startActivity(intent);
            Toast.makeText(MainActivity.this, "Just tap to vote!", Toast.LENGTH_LONG).show();
        }
    }

}
