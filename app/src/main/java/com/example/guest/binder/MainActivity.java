package com.example.guest.binder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.loginButton) Button mLoginButton;
    @Bind(R.id.usernameText) EditText mUsername;
    @Bind(R.id.passwordText) EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLoginButton.setOnClickListener(this);
        
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(MainActivity.this, CoverActivity.class);

        if(v == mLoginButton) {
            String username = mUsername.getText().toString();
            String password = mPassword.getText().toString();

            if(username.equals("") || password.equals("")){
                Toast.makeText(MainActivity.this, "Please enter your username and password.", Toast.LENGTH_SHORT).show();
            }
            else{
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Welcome " + username + "!", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
