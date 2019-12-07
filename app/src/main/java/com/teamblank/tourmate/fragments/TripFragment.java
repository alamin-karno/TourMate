package com.teamblank.tourmate.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.teamblank.tourmate.R;
import com.teamblank.tourmate.adapters.TripAdapter;
import com.teamblank.tourmate.model_class.Trip;

import java.util.ArrayList;
import java.util.List;

public class TripFragment extends Fragment {
    private List<Trip> tripList;
    private RecyclerView recyclerView;
    private TripAdapter adapter;
    private FloatingActionButton addtripfavicon;
    public TripFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip, container, false);

        init(view);

        tripList.add(new Trip("1","Cox's Bazar Tour","Gazipur","Cox's Bazer","29/11/2019","02/12/2019","This our first tour of Tour Bangla Group","5500"));
        tripList.add(new Trip("1","Cox's Bazar Tour","Gazipur","Cox's Bazer","29/11/2019","02/12/2019","This our first tour of Tour Bangla Group","5500"));
        tripList.add(new Trip("1","Cox's Bazar Tour","Gazipur","Cox's Bazer","29/11/2019","02/12/2019","This our first tour of Tour Bangla Group","5500"));
        tripList.add(new Trip("1","Cox's Bazar Tour","Gazipur","Cox's Bazer","29/11/2019","02/12/2019","This our first tour of Tour Bangla Group","5500"));
        tripList.add(new Trip("1","Cox's Bazar Tour","Gazipur","Cox's Bazer","29/11/2019","02/12/2019","This our first tour of Tour Bangla Group","5500"));
        tripList.add(new Trip("1","Cox's Bazar Tour","Gazipur","Cox's Bazer","29/11/2019","02/12/2019","This our first tour of Tour Bangla Group","5500"));
        tripList.add(new Trip("1","Cox's Bazar Tour","Gazipur","Cox's Bazer","29/11/2019","02/12/2019","This our first tour of Tour Bangla Group","5500"));
        tripList.add(new Trip("1","Cox's Bazar Tour","Gazipur","Cox's Bazer","29/11/2019","02/12/2019","This our first tour of Tour Bangla Group","5500"));
        adapter.notifyDataSetChanged();
        return view;
    }

    private void init(View view) {
        addtripfavicon = view.findViewById(R.id.addTripFav);
        tripList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.tripRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TripAdapter(tripList);
        recyclerView.setAdapter(adapter);
    }

}
