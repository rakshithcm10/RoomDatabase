package com.example.application.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;

import com.example.application.Authentication.AuthenticationActivity;
import com.example.application.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        setTitle("");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        Intent i =new Intent(MainActivity.this, AuthenticationActivity.class);
        Handler handler = new Handler();



        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                finish();
            }
        },2000);

    }
}