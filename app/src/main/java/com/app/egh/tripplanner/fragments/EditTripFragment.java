package com.app.egh.tripplanner.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.egh.tripplanner.R;
import com.app.egh.tripplanner.activities.DetailedActivity;
import com.app.egh.tripplanner.activities.EditTripActivity;
import com.app.egh.tripplanner.data.model.Adapter;
import com.app.egh.tripplanner.data.model.Trip;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditTripFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "EditTripFragment";

    EditText trip_name;
    EditText dateField;
    EditText timeField;
    RadioGroup directionsRadioGroup;
    RadioButton oneWayRadioBtn;
    CheckBox repeatCheckbox;
    Button editTrip;
    //LinearLayout notes;

    Trip trip;
    Date dateAndTime;
    int year,month,day,hour,min;

    public EditTripFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit_trip, container, false);

        Intent intent = getActivity().getIntent();
        trip = (Trip) intent.getSerializableExtra("trip");

        trip_name = view.findViewById(R.id.trip_name);
        dateField = view.findViewById(R.id.trip_date);
        timeField = view.findViewById(R.id.trip_time);
        directionsRadioGroup = view.findViewById(R.id.directionsRadioGroup);
        oneWayRadioBtn = view.findViewById(R.id.oneDirectionRadioButton);
        repeatCheckbox = view.findViewById(R.id.repeatCheckbox);
        editTrip = view.findViewById(R.id.editTrip);

        trip_name.setText(trip.getTrip_name());
        try {
            year = trip.getDate_time().getYear();
            month = trip.getDate_time().getMonth();
            day = trip.getDate_time().getDay();
            hour = trip.getDate_time().getHours();
            min = trip.getDate_time().getMinutes();

            dateField.setText(day + "/" + (month + 1) + "/" + year);
            timeField.setText(getTime());
        }catch (Exception e){
            Calendar c = Calendar.getInstance();
            Date date = c.getTime();

            year = date.getYear();Log.i(TAG,year+"");
            month = date.getMonth();
            day = date.getDay();
            hour = date.getHours();
            min = date.getMinutes();

            dateField.setText(day + "/" + (month + 1) + "/" + year);
            timeField.setText(getTime());

        }

        editTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTrip();
                goToDetailedActivity();
            }
        });

        dateField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        EditTripFragment.this,
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
                        EditTripFragment.this,
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
                trip.setStart_lat(place.getLatLng().latitude);
                trip.setStart_long(place.getLatLng().longitude);
                trip.setStart_name(place.getName().toString());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });


        PlaceAutocompleteFragment autocompleteFragmentEnd = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_end);

        autocompleteFragmentEnd.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                trip.setEnd_lat(place.getLatLng().latitude);
                trip.setEnd_long(place.getLatLng().longitude);
                trip.setEnd_name(place.getName().toString());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });


        return view;
    }

    private void updateTrip(){

        trip.setTrip_name(trip_name.getText().toString());

        if(oneWayRadioBtn.isChecked()){
            trip.setRoundtrip(false);
        }else{
            trip.setRoundtrip(true);
        }

        if(repeatCheckbox.isChecked()){
            trip.setRepeated(true);
        }else{
            trip.setRepeated(false);
        }

        Calendar c = Calendar.getInstance();
        c.set(year, month, day, hour, min);
        dateAndTime = c.getTime();
        trip.setDate_time(dateAndTime);

        Adapter myAdapter = new Adapter(getActivity());
        long rows_affected = myAdapter.updateTrip(trip);
        Log.i(TAG,"Updated "+rows_affected+" trip");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
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

    private String getTime(){
        int h=hour;
        String pm="am";
        if(hour>12){
            h=hour-12;
        }
        if(hour>=12)
            pm="pm";
        return h+":"+min+" "+pm;
    }

    private boolean validate(){

        int counter = 0;
        if(trip_name.getText().toString().equalsIgnoreCase(""))
        {
            trip_name.setHint("please enter trip name");//it gives user to hint
            trip_name.setError("please enter trip name");//it gives user to info message //use any one //
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

    private void goToDetailedActivity(){

        getActivity().finish();
        Intent intent = new Intent(getActivity(), DetailedActivity.class);
        intent.putExtra("trip",trip);
        startActivity(intent);

    }
}
