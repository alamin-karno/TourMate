package com.teamblank.tourmate.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.teamblank.tourmate.R;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    private EditText nameET,emailET,passwordET,confrimpasswordET;
    private Button signupBTN;
    private ProgressBar progressBar;
    private String name,email,password,confpass;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        
        init();

        SignUpAction();
    }

    private void SignUpAction() {
        signupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                signupBTN.setVisibility(View.INVISIBLE);

                name = nameET.getText().toString();
                email = emailET.getText().toString();
                password = passwordET.getText().toString();
                confpass = confrimpasswordET.getText().toString();

                if(name.isEmpty()){
                    Toast.makeText(SignupActivity.this, "Enter your name.", Toast.LENGTH_SHORT).show();
                    ProgressBarAction();
                }
                else if(email.isEmpty()){
                    Toast.makeText(SignupActivity.this, "Enter your email address.", Toast.LENGTH_SHORT).show();
                    ProgressBarAction();
                }
                else if(password.isEmpty()){
                    Toast.makeText(SignupActivity.this, "Enter at least 6 digit password.", Toast.LENGTH_SHORT).show();
                    ProgressBarAction();
                }
                else if(password.length()<6){
                    Toast.makeText(SignupActivity.this, "Enter at least 6 digit password.", Toast.LENGTH_SHORT).show();
                    ProgressBarAction();
                }
                else if(confpass.isEmpty()){
                    Toast.makeText(SignupActivity.this, "Please confirm your password.", Toast.LENGTH_SHORT).show();
                    ProgressBarAction();
                }
                else if(!password.equals(confpass)){
                    Toast.makeText(SignupActivity.this, "Confirm password does not match.", Toast.LENGTH_SHORT).show();
                    ProgressBarAction();
                }
                else {
                    signUp(name,email,password);
                }

            }
        });
    }

    private void ProgressBarAction() {
        progressBar.setVisibility(View.INVISIBLE);
        signupBTN.setVisibility(View.VISIBLE);
    }

    private void signUp(final String name, final String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignupActivity.this, "Account Create Successfully.", Toast.LENGTH_SHORT).show();

                    String userID = firebaseAuth.getCurrentUser().getUid();
                    DatabaseReference userRef = databaseReference.child("users").child(userID);
                    HashMap<String,Object> userInfo = new HashMap<>();
                    userInfo.put("Name",name);
                    userInfo.put("Email",email);
                    userInfo.put("UserID",userID);

                    userRef.setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                                progressBar.setVisibility(View.INVISIBLE);
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignupActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            ProgressBarAction();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignupActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                ProgressBarAction();
            }
        });
    }

    private void init() {
        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        confrimpasswordET = findViewById(R.id.confirmpasswordET);
        signupBTN = findViewById(R.id.signupBTN);
        progressBar = findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void onSignin(View view) {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
