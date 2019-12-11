package com.teamblank.tourmate.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamblank.tourmate.R;
import com.teamblank.tourmate.model_class.Memory;

import java.util.List;

public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.ViewHolder> {
    List<Memory> memories;

    public MemoryAdapter(List<Memory> memories) {
        this.memories = memories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_design_memories,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Memory memory = memories.get(position);

        holder.memoryImage.setImageURI(Uri.parse(memory.getMemoryImage()));
        holder.memoryTitle.setText(memory.getMemoryTitle());
        holder.memoryDetails.setText(memory.getMemoryDetails());
    }

    @Override
    public int getItemCount() {
        return memories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView memoryImage;
        private TextView memoryTitle,memoryDetails;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            memoryDetails = itemView.findViewById(R.id.memoryDetails);
            memoryTitle = itemView.findViewById(R.id.memoryTitle);
            memoryImage = itemView.findViewById(R.id.memoryImageView);
        }
    }
}
