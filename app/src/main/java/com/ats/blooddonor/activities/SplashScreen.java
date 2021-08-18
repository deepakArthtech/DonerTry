package com.ats.blooddonor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ats.blooddonor.Model.SharedPreManager;
import com.ats.blooddonor.R;
import com.ats.blooddonor.auth.AuthActivity;
import com.ats.blooddonor.home.HomeActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!new SharedPreManager(SplashScreen.this).getAcces_token().equals("")) {
                    Intent i = new Intent(SplashScreen.this, HomeActivity.class);
                    //Intent is used to switch from one activity to another.

                    startActivity(i);
                }else {
                    Intent i = new Intent(SplashScreen.this, AuthActivity.class);
                    //Intent is used to switch from one activity to another.

                    startActivity(i);
                }
                //invoke the SecondActivity.

                finish();
                //the current activity will get finished.
            }
        }, 2000);
    }
}