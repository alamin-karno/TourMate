package com.teamblank.tourmate.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamblank.tourmate.R;
import com.teamblank.tourmate.adapters.MemoryAdapter;
import com.teamblank.tourmate.bottomsheet.MemoryPost;
import com.teamblank.tourmate.model_class.Memory;
import com.teamblank.tourmate.model_class.Trip;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MemoriesFragment extends Fragment {
    private RecyclerView MemoryRecyclerView;
    private FloatingActionButton floatingActionButton;
    private DatabaseReference databaseReference;
    private List<Memory> memoryList;
    private MemoryAdapter adapter;
    public MemoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_memories, container, false);

        init(view);

        addMemory(view);

        loadData(view);

        fabIconScroll(view);

        return  view;
    }

    private void addMemory(View view) {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MemoryPost memoryPost = new MemoryPost();
                memoryPost.show(getActivity().getSupportFragmentManager(),"Bottom");
            }
        });
    }

    private void init(View view) {
        MemoryRecyclerView = view.findViewById(R.id.MemoryRecyclerView);
        MemoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        floatingActionButton = view.findViewById(R.id.addMemoryFav);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        memoryList = new ArrayList<>();
        adapter = new MemoryAdapter(memoryList);
        MemoryRecyclerView.setAdapter(adapter);

    }

    private void loadData(final View view) {
        DatabaseReference loadRef = databaseReference.child("Memory");
        loadRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    memoryList.clear();
                    for(DataSnapshot memoryData : dataSnapshot.getChildren()){
                        Memory memory = memoryData.getValue(Memory.class);

                        //memoryList.add(memory);

                        memoryList.add(new Memory(memory.getMemoryTitle(),memory.getMemoryDetails(),memory.getMemoryImage()));

                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void fabIconScroll(View view) {
        MemoryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0){
                    floatingActionButton.hide();
                }
                else if(dy<0){
                    floatingActionButton.show();
                }
            }
        });
    }


}
