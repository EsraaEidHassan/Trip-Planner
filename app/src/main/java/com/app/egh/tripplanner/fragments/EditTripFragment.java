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
import android.widget.Toast;

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
    CheckBox doneCheckbox;
    Button editTrip;
    //LinearLayout notes;

    PlaceAutocompleteFragment autocompleteFragmentStart;
    PlaceAutocompleteFragment autocompleteFragmentEnd;

    Trip trip;
    Date dateAndTime;
    int year,month,day,hour,min;
    boolean directions_start_validation;
    boolean directions_end_validation;

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
        doneCheckbox = view.findViewById(R.id.doneCheckbox);
        editTrip = view.findViewById(R.id.editTrip);

        trip_name.setText(trip.getTrip_name());
        directions_start_validation = true;
        directions_end_validation = true;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(trip.getDate_time());
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            hour = calendar.get(Calendar.HOUR_OF_DAY);
            min = calendar.get(Calendar.MINUTE); //number of seconds

            dateField.setText(day + "/" + (month + 1) + "/" + year);
            timeField.setText(getTime());
        }catch (Exception e){

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(calendar.getTime());
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            hour = calendar.get(Calendar.HOUR_OF_DAY);
            min = calendar.get(Calendar.MINUTE); //number of seconds

            dateField.setText(day + "/" + (month + 1) + "/" + year);
            timeField.setText(getTime());
        }

        editTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTrip();
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

        autocompleteFragmentStart = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_start);

        autocompleteFragmentStart.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                trip.setStart_lat(place.getLatLng().latitude);
                trip.setStart_long(place.getLatLng().longitude);
                trip.setStart_name(place.getName().toString());

                directions_start_validation = true;
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        ((EditText) autocompleteFragmentStart.getView().findViewById(R.id.place_autocomplete_search_input)).setText(trip.getStart_name());

        autocompleteFragmentStart.getView().findViewById(R.id.place_autocomplete_clear_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // example : way to access view from PlaceAutoCompleteFragment
                         ((EditText) autocompleteFragmentStart.getView()
                        .findViewById(R.id.place_autocomplete_search_input)).setText("");
                        autocompleteFragmentStart.setText("");
                        view.setVisibility(View.GONE);
                        directions_start_validation = false;
                        //Toast.makeText(getContext(),"cancel start",Toast.LENGTH_LONG).show();
                    }
                });


        autocompleteFragmentEnd = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_end);

        autocompleteFragmentEnd.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                trip.setEnd_lat(place.getLatLng().latitude);
                trip.setEnd_long(place.getLatLng().longitude);
                trip.setEnd_name(place.getName().toString());

                directions_end_validation = true;
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }

        });

        ((EditText) autocompleteFragmentEnd.getView().findViewById(R.id.place_autocomplete_search_input)).setText(trip.getEnd_name());

        autocompleteFragmentEnd.getView().findViewById(R.id.place_autocomplete_clear_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // example : way to access view from PlaceAutoCompleteFragment
                         ((EditText) autocompleteFragmentEnd.getView()
                         .findViewById(R.id.place_autocomplete_search_input)).setText("");
                        autocompleteFragmentEnd.setText("");
                        view.setVisibility(View.GONE);
                        directions_end_validation = false;
                    }
                });
        return view;
    }

    private void updateTrip(){

        if(validate()) {

            trip.setTrip_name(trip_name.getText().toString());

            if (oneWayRadioBtn.isChecked()) {
                trip.setRoundtrip(false);
            } else {
                trip.setRoundtrip(true);
            }

            if (repeatCheckbox.isChecked()) {
                trip.setRepeated(true);
            } else {
                trip.setRepeated(false);
            }

            if (doneCheckbox.isChecked()) {
                trip.setStarted(true);
            } else {
                trip.setStarted(false);
            }

            Calendar c = Calendar.getInstance();
            c.set(year, month, day, hour, min);
            dateAndTime = c.getTime();
            trip.setDate_time(dateAndTime);

            Adapter myAdapter = new Adapter(getActivity());
            long rows_affected = myAdapter.updateTrip(trip);
            Log.i(TAG, "Updated " + rows_affected + " trip");

            goToDetailedActivity();
        }
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
        if(!directions_start_validation)
        {
            ((EditText) autocompleteFragmentStart.getView().findViewById(R.id.place_autocomplete_search_input)).setHint("please enter start point");
            ((EditText) autocompleteFragmentStart.getView().findViewById(R.id.place_autocomplete_search_input)).setError("please enter start point");
        }else{
            counter ++;
        }
        if(!directions_end_validation)
        {
            ((EditText) autocompleteFragmentEnd.getView().findViewById(R.id.place_autocomplete_search_input)).setHint("please enter end point");
            ((EditText) autocompleteFragmentEnd.getView().findViewById(R.id.place_autocomplete_search_input)).setError("please enter end point");
        }else{
            counter ++;
        }
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

        if(counter == 5)
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
