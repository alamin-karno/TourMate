package com.teamblank.tourmate.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.teamblank.tourmate.R;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void onSignin(View view) {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
