package com.teamblank.tourmate.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.teamblank.tourmate.R;
import com.teamblank.tourmate.fragments.MemoriesFragment;
import com.teamblank.tourmate.fragments.TripFragment;
import com.teamblank.tourmate.fragments.WalletFragment;

public class MainActivity extends AppCompatActivity {
    private TextView toolbarTV;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        defaultFragment();

        setBotoomNavigation();
    }

    private void defaultFragment() {
        replaceFragment(new TripFragment());
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
        toolbarTV = findViewById(R.id.toolbarTV);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.frameLayout);
    }
}
