package com.teamblank.tourmate.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.teamblank.tourmate.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onSignup(View view) {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }
}
