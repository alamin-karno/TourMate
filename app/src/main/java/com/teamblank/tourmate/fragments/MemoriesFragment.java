package com.teamblank.tourmate.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.teamblank.tourmate.R;
import com.teamblank.tourmate.bottomsheet.MemoryPost;


/**
 * A simple {@link Fragment} subclass.
 */
public class MemoriesFragment extends Fragment {
    private RecyclerView MemoryRecyclerView;
    private FloatingActionButton floatingActionButton;

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
        floatingActionButton = view.findViewById(R.id.addMemoryFav);
    }

}
