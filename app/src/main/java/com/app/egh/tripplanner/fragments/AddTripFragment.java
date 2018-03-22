package com.app.egh.tripplanner.fragments;



import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.egh.tripplanner.R;
import com.app.egh.tripplanner.data.model.Adapter;
import com.app.egh.tripplanner.data.model.Trip;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTripFragment extends Fragment {

    Button addNoteBtn;
    Button addTripBtn;
    EditText tripNameField;
    EditText startPointField;
    EditText endPointField;
    EditText timeAndDateField;
    TextView notesTextView;
    RadioGroup notesRadioGroup;


    String tripName;
    long startLatit;
    long startLongit;
    String startName;
    long endLatit;
    long endLongit;
    String endName;
    Date dateAndTime;
    boolean repeat;
    boolean roundTrip;
    List<String> tripNotes;


    public AddTripFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_trip, container, false);
        // Inflate the layout for this fragment

        tripNameField = view.findViewById(R.id.tripNameField);
        startPointField = view.findViewById(R.id.startPointField);
        endPointField = view.findViewById(R.id.endPointField);
        timeAndDateField = view.findViewById(R.id.timeAndDateField);
        notesTextView =  view.findViewById(R.id.notesTextView);
        notesRadioGroup = view.findViewById(R.id.notesRadioGroup);
        addNoteBtn = view.findViewById(R.id.addNoteBtn);
        addTripBtn = view.findViewById(R.id.addTripBtn);

        //defaults values temporarly
        startLatit = (long) 30.1;
        startLongit = (long) 31.1;
        startName = "home";
        endLatit = (long) 30.1;
        endLongit = (long) 31.1;
        endName = "school";
        dateAndTime = Calendar.getInstance().getTime();
        Log.i("time"  ,""+dateAndTime);
        repeat = false;
        roundTrip = false;
        tripNotes = new ArrayList<>();
        tripNotes.add("first Note");
        tripNotes.add("second Note");
        tripNotes.add("third Note");

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("addTrip","add Note");
            }
        });
        addTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("addTrip","add Trip");

                    Trip newTrip = new Trip(tripNameField.getText().toString(), startLatit, startLongit, startName, endLatit, endLongit, endName,dateAndTime, repeat,roundTrip, tripNotes);
                    Adapter myAdapter = new Adapter(getActivity());
                    myAdapter.insert_trip(newTrip);
                    myAdapter.insert_Notes(newTrip);
                }
        });
        return view;
    }

}
