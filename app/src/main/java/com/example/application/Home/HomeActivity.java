package com.example.application.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;

import com.example.application.Authentication.AuthenticationActivity;


import com.example.application.Data.DisplayData;
import com.example.application.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ActivityHomeBinding homeBinding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());

        setTitle("Home");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLUE));


        homeBinding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.clear();
                myEdit.apply();

                Intent i = new Intent(HomeActivity.this, AuthenticationActivity.class);
                startActivity(i);
                Toast.makeText(HomeActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        homeBinding.btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, DisplayData.class);
                startActivity(i);
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();

        sharedPreferences = this.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        homeBinding.textViewEmail.setText(sharedPreferences.getString("Email","default"));

    }
}