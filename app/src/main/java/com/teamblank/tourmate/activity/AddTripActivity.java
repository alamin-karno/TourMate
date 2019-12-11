package com.teamblank.tourmate.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.teamblank.tourmate.R;
import com.teamblank.tourmate.model_class.Trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTripActivity extends AppCompatActivity {
    private EditText tripnameET,tripStartLoactionET,tripLoactionET,tripdetailsET,tripBudgetET;
    private Button tripstartDateBTN,tripendDateBTN,addTripBTN;
    private String name,starloaction,endloaction,startdate,enddate,tripdetails,budget;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        init();

        tripStartDate();

        tripEndDate();

        sendDatatoFireStore();
    }

    public void backIV(View view) {
        finish();
    }

    private void tripEndDate() {
        tripendDateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(startdate != null){
                    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            month = month+1;

                            String currentdate = day+"/"+month+"/"+year;

                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                            Date date = null;

                            try {
                                date = dateFormat.parse(currentdate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            tripendDateBTN.setText(dateFormat.format(date));
                        }
                    };

                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddTripActivity.this,dateSetListener,year,month,day);
                    datePickerDialog.show();
                }
                else {
                    Toast.makeText(AddTripActivity.this, "Select from date first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void tripStartDate() {
        tripstartDateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;

                        String currentdate = day+"/"+month+"/"+year;

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                        Date date = null;

                        try {
                            date = dateFormat.parse(currentdate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        startdate = dateFormat.format(date);
                        tripstartDateBTN.setText(startdate);
                    }
                };

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTripActivity.this,dateSetListener,year,month,day);
                datePickerDialog.show();
            }
        });


    }

    private void sendDatatoFireStore() {
        addTripBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(view);

                checkfield_and_sendData(view);
            }
        });
    }

    private void checkfield_and_sendData(View view) {

        if(name.isEmpty()){
            Toast.makeText(this, "Please enter your trip name.", Toast.LENGTH_SHORT).show();
        }
        else if(starloaction.isEmpty()){
            Toast.makeText(this, "Please enter your trip start location.", Toast.LENGTH_SHORT).show();
        }
        else if(endloaction.isEmpty()){
            Toast.makeText(this, "Please enter your trip location.", Toast.LENGTH_SHORT).show();
        }
        else if(startdate.isEmpty()){
            Toast.makeText(this, "Please enter your trip start date.", Toast.LENGTH_SHORT).show();
        }
        else if(enddate.isEmpty()){
            Toast.makeText(this, "Please enter your trip end date.", Toast.LENGTH_SHORT).show();
        }
        else if(budget.isEmpty()){
            Toast.makeText(this, "Please enter your trip budget.", Toast.LENGTH_SHORT).show();
        }
        else{
            sentdata(view);
        }
    }

    private void sentdata(View view) {

        DatabaseReference tripdatabase = databaseReference.child("Trip");
        String UserId = tripdatabase.push().getKey();
        Trip addTrip = new Trip(UserId,name,starloaction,endloaction,startdate,enddate,tripdetails,budget);

        tripdatabase.child(UserId).setValue(addTrip).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddTripActivity.this, "Successfully added.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddTripActivity.this,MainActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddTripActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData(View view) {
        name = tripnameET.getText().toString();
        starloaction = tripStartLoactionET.getText().toString();
        endloaction = tripLoactionET.getText().toString();
        startdate = tripstartDateBTN.getText().toString();
        enddate = tripendDateBTN.getText().toString();
        tripdetails = tripdetailsET.getText().toString();
        budget = tripBudgetET.getText().toString();
    }

    private void init() {
        tripnameET = findViewById(R.id.tripnameET);
        tripStartLoactionET = findViewById(R.id.tripstartlocationET);
        tripLoactionET = findViewById(R.id.triploactionET);
        tripdetailsET = findViewById(R.id.tripDiscriptionET);
        tripBudgetET = findViewById(R.id.tripBudgetET);
        tripstartDateBTN = findViewById(R.id.tripstartDateBTN);
        tripendDateBTN = findViewById(R.id.tripendDateBTN);
        addTripBTN = findViewById(R.id.addTripBTN);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }
}
