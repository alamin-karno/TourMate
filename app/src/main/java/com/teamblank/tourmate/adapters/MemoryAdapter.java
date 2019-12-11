package com.teamblank.tourmate.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
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

        Bitmap bitmap = stringToBitmap(memory.getMemoryImage());

        holder.memoryImage.setImageBitmap(bitmap);
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

    private Bitmap stringToBitmap(String updateimage) {

        try {
            byte [] encodeByte= Base64.decode(updateimage,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
