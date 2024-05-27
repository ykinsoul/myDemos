package com.example.servicenovigrad.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicenovigrad.R;

public class WelcomeAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_admin);
    }

    public void buttonUsers(View view) {
        Intent intent = new Intent(this, ViewUsersActivity.class);
        startActivity(intent);
    }

    public void buttonServices(View view) {
        Intent serviceIntent = new Intent(this, ViewServicesActivity.class);
        startActivity(serviceIntent);
    }
}