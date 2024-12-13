package com.example.as2_blood_donation.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.as2_blood_donation.utils.SessionManager;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SessionManager sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            // User is logged in, proceed to main activity
            startActivity(new Intent(SplashActivity.this, ManageSites.class));
        } else {
            // User is not logged in, proceed to login activity
            startActivity(new Intent(SplashActivity.this, Login.class));
        }

        finish(); // Close splash screen
    }
}

