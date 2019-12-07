package com.teamblank.tourmate.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamblank.tourmate.R;
import com.teamblank.tourmate.model_class.Trip;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {
    private List<Trip> trips;

    public TripAdapter(List<Trip> trips) {
        this.trips = trips;
    }

    @NonNull
    @Override
    public TripAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_design_tour_expense,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripAdapter.ViewHolder holder, int position) {
        Trip trip = trips.get(position);
        holder.triptitleTV.setText(trip.getTriptitle());
        holder.startlocationTV.setText(trip.getStartlocation());
        holder.locationtripTV.setText(trip.getTriplocation());
        holder.startdateTV.setText(trip.getStartdate());
        holder.enddateTV.setText(trip.getEnddate());
        holder.tripdetailsTV.setText(trip.getTripdetails());
        holder.tripbudgetTV.setText(trip.getTripbudget());
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView triptitleTV,startlocationTV,locationtripTV,startdateTV,enddateTV,tripdetailsTV,tripbudgetTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            triptitleTV = itemView.findViewById(R.id.triptitleTV);
            startlocationTV = itemView.findViewById(R.id.startloactionTV);
            locationtripTV = itemView.findViewById(R.id.locationtripTV);
            startdateTV = itemView.findViewById(R.id.startdateTV);
            enddateTV = itemView.findViewById(R.id.enddateTV);
            tripdetailsTV = itemView.findViewById(R.id.tripdetailsTV);
            tripbudgetTV = itemView.findViewById(R.id.tripbudgetTV);
        }
    }
}
