package com.anubhavps.satrank.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.anubhavps.satrank.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final Intent intent;

        intent = new Intent(SplashScreen.this, HomePage.class);


        int SPLASH_SCREEN = 1200;
        new Handler().postDelayed(() -> {
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            finish();
        }, SPLASH_SCREEN);

    }




}