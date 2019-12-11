package com.teamblank.tourmate.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamblank.tourmate.R;
import com.teamblank.tourmate.activity.AddTripActivity;
import com.teamblank.tourmate.adapters.TripAdapter;
import com.teamblank.tourmate.model_class.Trip;

import java.util.ArrayList;
import java.util.List;

public class TripFragment extends Fragment {
    private List<Trip> tripList;
    private RecyclerView recyclerView;
    private TripAdapter adapter;
    private FloatingActionButton addtripfavicon;
    private DatabaseReference databaseReference;
    public TripFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip, container, false);

        init(view);

        addTrip();

        loadData(view);

        fabIconScroll(view);

        return view;
    }

    private void fabIconScroll(View view) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0){
                    addtripfavicon.hide();
                }
                else if(dy<0){
                    addtripfavicon.show();
                }
            }
        });
    }

    private void loadData(final View view) {
        DatabaseReference loadRef = databaseReference.child("Trip");
        loadRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    tripList.clear();
                    for(DataSnapshot tripData : dataSnapshot.getChildren()){
                        Trip trip = tripData.getValue(Trip.class);
                        tripList.add(trip);
                        //tripList.add(new Trip(trip.getTriptitle(),trip.getStartlocation(),trip.getTriplocation(),trip.getStartdate(),trip.getEnddate(),trip.getTripdetails(),trip.getTripbudget()));

                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addTrip() {
        addtripfavicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddTripActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init(View view) {
        addtripfavicon = view.findViewById(R.id.addTripFav);
        tripList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = view.findViewById(R.id.tripRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TripAdapter(tripList);
        recyclerView.setAdapter(adapter);
    }

}
