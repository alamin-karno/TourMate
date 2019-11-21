package com.teamblank.tourmate.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.teamblank.tourmate.R;

public class ForgetPasswordActivity extends AppCompatActivity {
    private EditText resetemailET;
    private Button resetpassBTN;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        init();

        passreset();
    }

    private void passreset() {
        resetpassBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                resetpassBTN.setVisibility(View.INVISIBLE);
                email = resetemailET.getText().toString();

                if(email.isEmpty()){
                    Toast.makeText(ForgetPasswordActivity.this, "Enter your email address.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    resetpassBTN.setVisibility(View.VISIBLE);
                }
                else {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ForgetPasswordActivity.this, "Email is send successfully.", Toast.LENGTH_LONG).show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        finish();
                                    }
                                },1000);


                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ForgetPasswordActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            resetpassBTN.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
    }

    private void init() {
        resetemailET = findViewById(R.id.resetemailET);
        resetpassBTN = findViewById(R.id.resetpasswordBTN);
        progressBar = findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void backIV(View view) {
        finish();
    }
}
