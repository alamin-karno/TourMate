package com.teamblank.tourmate.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamblank.tourmate.R;
import com.teamblank.tourmate.fragments.MemoriesFragment;
import com.teamblank.tourmate.fragments.TripFragment;
import com.teamblank.tourmate.fragments.WalletFragment;


public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView toolbarTV,profileNameTV,profileEmailTV;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String screen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        init();

        setScrenn();


        //profileInfo();

        setBotoomNavigation();

        setNavigationView();


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

    }

    private void setScrenn() {
        screen = getIntent().getStringExtra("setScreen");
        if(screen != null){
            replaceFragment(new MemoriesFragment());
            bottomNavigationView.setSelectedItemId(R.id.nav_memories);
        }
        else {
            replaceFragment(new TripFragment());
            bottomNavigationView.setSelectedItemId(R.id.nav_trips);
        }
    }

    private void profileInfo() {
        final String userID = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference userRef = databaseReference.child("users").child(userID);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String name = dataSnapshot.child("Name").getValue().toString();
                    String email = dataSnapshot.child("Email").getValue().toString();
                    Toast.makeText(MainActivity.this, ""+name+"\n"+email, Toast.LENGTH_SHORT).show();
                    if(!name.isEmpty() && !email.isEmpty()){
                        profileNameTV.setText(name);
                        profileEmailTV.setText(email);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if(bottomNavigationView.getSelectedItemId() == R.id.nav_wallet){
            replaceFragment(new TripFragment());
            bottomNavigationView.setSelectedItemId(R.id.nav_trips);
        }
        else if(bottomNavigationView.getSelectedItemId() == R.id.nav_memories){
            replaceFragment(new TripFragment());
            bottomNavigationView.setSelectedItemId(R.id.nav_trips);
        }
        else {
            super.onBackPressed();
        }


    }


    private void setNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){
                    case R.id.nav_trips:
                        toolbarTV.setText("Trips");
                        bottomNavigationView.setSelectedItemId(R.id.nav_trips);
                        replaceFragment(new TripFragment());
                        break;
                    case R.id.nav_memories:
                        toolbarTV.setText("Memories");
                        bottomNavigationView.setSelectedItemId(R.id.nav_memories);
                        replaceFragment(new MemoriesFragment());
                        break;
                    case R.id.nav_wallet:
                        toolbarTV.setText("Wallet");
                        bottomNavigationView.setSelectedItemId(R.id.nav_wallet);
                        replaceFragment(new WalletFragment());
                        break;
                    case R.id.weather_id:
                        Intent intent = new Intent(MainActivity.this,WeatherActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nearby_id:
                        startActivity(new Intent(MainActivity.this,MapActivity.class));
                        break;
                    case R.id.Logout_id:
                        LogoutApp();
                        break;

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void LogoutApp() {
        sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }


    private void setBotoomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_trips:
                        toolbarTV.setText("Trips");
                        replaceFragment(new TripFragment());
                        return true;
                    case R.id.nav_memories:
                        toolbarTV.setText("Memories");
                        replaceFragment(new MemoriesFragment());
                        return true;
                    case R.id.nav_wallet:
                        toolbarTV.setText("Wallet");
                        replaceFragment(new WalletFragment());
                        return true;
                }

                return false;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbarTV = findViewById(R.id.toolbarTV);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.frameLayout);
        profileNameTV = findViewById(R.id.profileNameTV);
        profileEmailTV = findViewById(R.id.profileemaiTV);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}
