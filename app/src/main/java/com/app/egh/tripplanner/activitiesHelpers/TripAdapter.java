package com.app.egh.tripplanner.activitiesHelpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.egh.tripplanner.R;
import com.app.egh.tripplanner.data.model.Trip;

import java.util.List;

/**
 * Created by gehad on 3/19/18.
 */

public class TripAdapter extends RecyclerView.Adapter <TripAdapter.ViewHolder> {

    public List<Trip> tripDataList;
    private Context context;
    private RecyclerView recyclerView;
    private final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildLayoutPosition(v);
            Trip tripData = tripDataList.get(itemPosition);
            Toast.makeText(context, tripData.getTrip_name(), Toast.LENGTH_LONG).show();
        }
    };


    public  TripAdapter(Context context, List<Trip> tripDataList, RecyclerView recyclerView){

        this.context = context;
        this.tripDataList = tripDataList;
        this.recyclerView = recyclerView;
    }
    @Override
    public TripAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trip_row, null);
        view.setOnClickListener(clickListener);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TripAdapter.ViewHolder holder, final int position) {
        Trip tripData = tripDataList.get(position);
        holder.imageView.setImageResource(R.mipmap.icon3);
        holder.tripNameTextView.setText(tripData.getTrip_name());
        holder.startLocationTextView.setText(tripData.getStart_name().toString());
        holder.destinationTextView.setText(tripData.getEnd_name().toString());
    }

    @Override
    public int getItemCount() {
        return tripDataList.size();
    }
    public  Context getContext(){  return this.context;}
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tripNameTextView, startLocationTextView, destinationTextView;
        Button startTrip;
        public ViewHolder(View itemView) {
            super(itemView);
            tripNameTextView = itemView.findViewById(R.id.title);
            startLocationTextView = itemView.findViewById(R.id.start);
            destinationTextView = itemView.findViewById(R.id.end);
            imageView = itemView.findViewById(R.id.imageView);
            startTrip = itemView.findViewById(R.id.startTrip);
            startTrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText( context, "Start trip activity", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
