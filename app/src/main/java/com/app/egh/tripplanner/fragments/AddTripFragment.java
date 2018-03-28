package com.app.egh.tripplanner.fragments;



import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.egh.tripplanner.R;
import com.app.egh.tripplanner.activities.AddTripActivity;
import com.app.egh.tripplanner.activities.HomeActivity;
import com.app.egh.tripplanner.data.model.Adapter;
import com.app.egh.tripplanner.data.model.Trip;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;


import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTripFragment extends Fragment implements TimePickerDialog.OnTimeSetListener , DatePickerDialog.OnDateSetListener {

    private static final String TAG = "AddTripFragment";

    Button addNoteBtn;
    Button addTripBtn;
    EditText tripNameField;
    EditText dateField;
    EditText timeField;
    TextView notesTextView;
    RadioGroup notesRadioGroup;
    RadioButton oneWayRadioBtn;
    CheckBox repeatCheckbox;

    String tripName;
    double startLatit;
    double startLongit;
    String startName;
    double endLatit;
    double endLongit;
    String endName;
    Date dateAndTime;
    int year,month,day,hour,min;
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
      //  startPointField = view.findViewById(R.id.startPointField);
      //  endPointField = view.findViewById(R.id.endPointField);
        dateField = view.findViewById(R.id.dateField);
        timeField = view.findViewById(R.id.timeField);
        notesTextView =  view.findViewById(R.id.notesTextView);
        notesRadioGroup = view.findViewById(R.id.notesRadioGroup);
        addNoteBtn = view.findViewById(R.id.addNoteBtn);
        addTripBtn = view.findViewById(R.id.addTripBtn);
        oneWayRadioBtn = view.findViewById(R.id.oneDirectionRadioButton);
        repeatCheckbox = view.findViewById(R.id.repeatCheckbox);

        //defaults values temporarly
       // startLatit = (long) 30.1;
      //  startLongit = (long) 31.1;
       // startName = "home";
        endLatit = (long) 30.1;
        endLongit = (long) 31.1;
        endName = "school";
       // dateAndTime = Calendar.getInstance().getTime();
      //  Log.i("time"  ,""+dateAndTime);
        //repeat = false;
       // roundTrip = false;
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

                if(oneWayRadioBtn.isChecked()){
                    roundTrip = false;
                }else{
                    roundTrip = true;
                }
                Log.i("addTrip","round trip: "+roundTrip);

                if(repeatCheckbox.isChecked()){
                    repeat = true;
                }else{
                    repeat = false;
                }
                Log.i("addTrip","repeat : "+repeat);

                Calendar c = Calendar.getInstance();
                c.set(year, month, day, hour, min);
                dateAndTime = c.getTime();
                Log.i("time"  ,""+dateAndTime);

                    Trip newTrip = new Trip(tripNameField.getText().toString(), (long)startLatit, (long)startLongit, startName, (long)endLatit, (long)endLongit, endName,dateAndTime, repeat,roundTrip, tripNotes);
                    Adapter myAdapter = new Adapter(getActivity());
                    long trip_id = myAdapter.insert_trip(newTrip);
                    newTrip.setTrip_id((int) trip_id);
                    Log.i(TAG,"trip id : "+trip_id);
                    myAdapter.insert_Notes(newTrip);

                    if(validate()) // edit this to include the code of adding trip !
                        gotoHomeActivity((AppCompatActivity) getActivity());
                }
        });

        dateField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AddTripFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });

        timeField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
               TimePickerDialog tpd = TimePickerDialog.newInstance(
                    AddTripFragment.this,
                       now.get(Calendar.HOUR_OF_DAY),
                       now.get(Calendar.MINUTE),
                       false
               );
                tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
            }
        });


        PlaceAutocompleteFragment autocompleteFragmentStart = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_start);

        autocompleteFragmentStart.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("test22", "Start Place: " + place.getName());
                Log.i("test22", "Start Place: " + place.getLatLng().longitude);
                Log.i("test22", "Start Place: " + place.getLatLng().latitude);
                startLatit =  place.getLatLng().latitude;
                startLongit = place.getLatLng().longitude;
                startName = place.getName().toString();
                Log.i("test22", "Start Place: " + startName);
                Log.i("test22", "Start Place: " + startLongit);
                Log.i("test22", "Start Place: " + startLatit);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("test22", "An error occurred: " + status);
            }
        });

        PlaceAutocompleteFragment autocompleteFragmentEnd = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_end);

        autocompleteFragmentEnd.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("test22", "end Place: " + place.getName());
                Log.i("test22", "end Place: " + place.getLatLng().longitude);
                Log.i("test22", "end Place: " + place.getLatLng().latitude);
                endLatit =  place.getLatLng().latitude;
                endLongit = place.getLatLng().longitude;
                endName = place.getName().toString();
                Log.i("test22", "Start Place: " + endName);
                Log.i("test22", "Start Place: " + endLongit);
                Log.i("test22", "Start Place: " + endLatit);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("test22", "An error occurred: " + status);
            }
        });

        return view;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Log.i("test1"," year"+year);
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
        dateField.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        int h=hourOfDay; String pm="am";
            Log.i("test1"," hour"+hourOfDay);
            this.hour = hourOfDay;
            this.min = minute;
            if(hourOfDay>12){
                h=hourOfDay-12;
            }
        if(hourOfDay>=12)
            pm="pm";
            timeField.setText(h+":"+minute+" "+pm);
    }

    private void gotoHomeActivity(AppCompatActivity activity){

        Intent intent = new Intent(activity, HomeActivity.class);
        startActivity(intent);

    }

    private boolean validate(){

        int counter = 0;
        if(tripNameField.getText().toString().equalsIgnoreCase(""))
        {
            tripNameField.setHint("please enter trip name");//it gives user to hint
            tripNameField.setError("please enter trip name");//it gives user to info message //use any one //
        }else{
            counter ++;
        }
      /*  if(startPointField.getText().toString().equalsIgnoreCase(""))
        {
            startPointField.setHint("please enter start point");
            startPointField.setError("please enter start point");//it gives user to info message //use any one //
        }else{
            counter ++;
        }
        if(endPointField.getText().toString().equalsIgnoreCase(""))
        {
            endPointField.setHint("please enter end point");
            endPointField.setError("please enter end point");//it gives user to info message //use any one //
        }else{
            counter ++;
        }*/
        if(dateField.getText().toString().equalsIgnoreCase(""))
        {
            dateField.setHint("please enter date");
            dateField.setError("please enter date");//it gives user to info message //use any one //
        }else{
            counter ++;
        }
        if(timeField.getText().toString().equalsIgnoreCase(""))
        {
            timeField.setHint("please enter time");
            timeField.setError("please enter time");//it gives user to info message //use any one //
        }else{
            counter ++;
        }

        if(counter == 3)
            return true;
        else
            return false;
    }
}
