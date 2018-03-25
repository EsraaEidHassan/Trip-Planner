package com.app.egh.tripplanner.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.egh.tripplanner.R;
import com.app.egh.tripplanner.data.model.Trip;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailedFragment extends Fragment {

    private static final String TAG = "DetailedFragment";

    TextView trip_name;
    TextView trip_from;
    TextView trip_to;
    TextView trip_date_time;
    ImageView repeated;
    ImageView roundTrip;
    LinearLayout notes;

    public DetailedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detailed, container, false);

        trip_name = view.findViewById(R.id.trip_name);
        trip_from = view.findViewById(R.id.trip_from);
        trip_to = view.findViewById(R.id.trip_to);
        trip_date_time = view.findViewById(R.id.trip_date_time);
        repeated = view.findViewById(R.id.trip_repeated);
        roundTrip = view.findViewById(R.id.trip_roundTrip);
        notes = view.findViewById(R.id.trip_notes);

        Intent intent = getActivity().getIntent();
        Trip trip = (Trip) intent.getSerializableExtra("trip");

        trip_name.setText(trip.getTrip_name());
        trip_from.setText("From: "+trip.getStart_name());
        trip_to.setText("To: "+trip.getEnd_name());
        trip_date_time.setText("Date: "+trip.getDate_time());
        if(trip.isRepeated())
            repeated.setImageResource(R.drawable.yes);
        else
            repeated.setImageResource(R.drawable.no);
        if(trip.isRoundtrip())
            roundTrip.setImageResource(R.drawable.yes);
        else
            roundTrip.setImageResource(R.drawable.no);

        // Dynamicly add notes
        //LayoutInflater inflater = getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for(int i=0;i<trip.getNotes().size();i++) {
            View dynamic_view = inflater.inflate(R.layout.dynamic_note_row,null);
            TextView textView = dynamic_view.findViewById(R.id.note);
            textView.setText(trip.getNotes().get(i));
            //if(dynamic_view.getParent()!=null)
            //    ((ViewGroup)dynamic_view.getParent()).removeView(dynamic_view);
            notes.addView(dynamic_view);
            Log.i(TAG , "note "+i);
        }

        Log.i(TAG , "trip title : "+trip.getTrip_name());
        return view;
    }

}
