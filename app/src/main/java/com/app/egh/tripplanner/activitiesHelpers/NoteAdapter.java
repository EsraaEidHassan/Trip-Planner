package com.app.egh.tripplanner.activitiesHelpers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.egh.tripplanner.R;
import com.app.egh.tripplanner.activities.DetailedActivity;
import com.app.egh.tripplanner.data.model.Trip;

import java.util.List;

/**
 * Created by Hasnaa on 27/03/2018.
 */

public class NoteAdapter extends RecyclerView.Adapter <NoteAdapter.ViewHolder> {

    private static final String TAG = "NoteAdapter";
    public List<String> noteDataList;
    private Context context;
    private RecyclerView recyclerView;
    private final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildLayoutPosition(v);
            String noteData = noteDataList.get(itemPosition);
            Toast.makeText(context, noteData, Toast.LENGTH_LONG).show();
            Log.i(TAG , noteData);
         //   Intent intent = new Intent(context, DetailedActivity.class);

           // intent.putExtra("trip",tripData);
           // context.startActivity(intent);
        }
    };


    public  NoteAdapter(Context context, List<String> noteDataList, RecyclerView recyclerView){

        this.context = context;
        this.noteDataList = noteDataList;
        this.recyclerView = recyclerView;
    }
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG , "Note Adapter");
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.note_row_card, null);
        view.setOnClickListener(clickListener);
        return  new NoteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String noteData = noteDataList.get(position);
        Log.i(TAG , "on Bind Holder " + noteData);
        holder.noteTextView.setText(noteData);
        // holder.startLocationTextView.setText(tripData.getStart_name().toString());
        //  holder.destinationTextView.setText(tripData.getEnd_name().toString());
    }


    @Override
    public int getItemCount() {
        return noteDataList.size();
    }
    public  Context getContext(){  return this.context;}
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView noteTextView;
       // Button startTrip;
        public ViewHolder(View itemView) {
            super(itemView);
            noteTextView = itemView.findViewById(R.id.thenote);
            Log.i(TAG , "View Holder ");
//            startLocationTextView = itemView.findViewById(R.id.start);
//            destinationTextView = itemView.findViewById(R.id.end);
//            imageView = itemView.findViewById(R.id.imageView);
//            startTrip = itemView.findViewById(R.id.startTrip);
//            startTrip.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText( context, "Start trip activity", Toast.LENGTH_LONG).show();
//                }
//            });
        }
    }
}
