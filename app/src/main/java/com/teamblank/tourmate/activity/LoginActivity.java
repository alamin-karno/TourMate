package com.teamblank.tourmate.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.teamblank.tourmate.R;

public class LoginActivity extends AppCompatActivity {
    private EditText emailET,passwordET;
    private Button loginBTN;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private String email = null,password= null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private RelativeLayout loginlayout,loadingIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        autoLogin();

        loginAction();
    }

    private void autoLogin() {
        sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
        email = sharedPreferences.getString("email",null);
        password = sharedPreferences.getString("password",null);

        loadingIV.setVisibility(View.VISIBLE);
        loginlayout.setVisibility(View.INVISIBLE);

        if(email == null && password == null){
            loadingIV.setVisibility(View.INVISIBLE);
            loginlayout.setVisibility(View.VISIBLE);
            loginAction();
        }
        else {
            login(email,password);
        }
    }

    private void loginAction() {
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                loginBTN.setVisibility(View.INVISIBLE);

                email = emailET.getText().toString();
                password = passwordET.getText().toString();

                if(email.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please enter your email address.", Toast.LENGTH_SHORT).show();
                    ProgressBarAction();
                }
                else if(password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Enter at least 6 digit password.", Toast.LENGTH_SHORT).show();
                    ProgressBarAction();
                }
                else if(password.length()<6){
                    Toast.makeText(LoginActivity.this, "Enter at least 6 digit password.", Toast.LENGTH_SHORT).show();
                    ProgressBarAction();
                }
                else {
                    login(email,password);
                }
            }
        });
    }

    private void ProgressBarAction() {
        progressBar.setVisibility(View.INVISIBLE);
        loginBTN.setVisibility(View.VISIBLE);
    }

    private void login(final String email, final String password) {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    progressBar.setVisibility(View.INVISIBLE);

                    sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("email",email);
                    editor.putString("password",password);
                    editor.apply();
                    finish();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                ProgressBarAction();
            }
        });
    }

    private void init() {
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        loginBTN = findViewById(R.id.loginBTN);
        progressBar = findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        loginlayout = findViewById(R.id.loginLayout);
        loadingIV = findViewById(R.id.loadingIV);
    }

    public void onSignup(View view) {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        finish();
    }

    public void fogotpass(View view) {
        startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
    }
}
