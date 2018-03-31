package com.app.egh.tripplanner.fragments;



import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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

import com.app.egh.tripplanner.activities.AlarmActivity;
import com.app.egh.tripplanner.activities.HomeActivity;
import com.app.egh.tripplanner.activitiesHelpers.MyDividerItemDecoration;
import com.app.egh.tripplanner.activitiesHelpers.NoteAdapter;
import com.app.egh.tripplanner.activitiesHelpers.NotificationScheduler;
import com.app.egh.tripplanner.activitiesHelpers.SwipeController;
import com.app.egh.tripplanner.activitiesHelpers.SwipeControllerAction;
import com.app.egh.tripplanner.activitiesHelpers.TripAdapter;
import com.app.egh.tripplanner.data.model.Adapter;
import com.app.egh.tripplanner.data.model.Trip;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;


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
    boolean directions_start_validation;
    boolean directions_end_validation;

    PlaceAutocompleteFragment autocompleteFragmentStart;
    PlaceAutocompleteFragment autocompleteFragmentEnd;

    RecyclerView notesRecyclerView;
    public  static List<String> allNotes;

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
        dateField = view.findViewById(R.id.dateField);
        timeField = view.findViewById(R.id.timeField);
        notesTextView =  view.findViewById(R.id.notesTextView);
        notesRadioGroup = view.findViewById(R.id.notesRadioGroup);
        addNoteBtn = view.findViewById(R.id.addNoteBtn);
        addTripBtn = view.findViewById(R.id.addTripBtn);
        oneWayRadioBtn = view.findViewById(R.id.oneDirectionRadioButton);
        repeatCheckbox = view.findViewById(R.id.repeatCheckbox);

        notesRecyclerView = view.findViewById(R.id.notesRecyclerView);

        notesRecyclerView.setHasFixedSize(true);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        tripNotes = new ArrayList<>();
       // tripNotes.add("first Note");
        //tripNotes.add("second Note");
        //tripNotes.add("third Note");

        directions_start_validation = false;
        directions_end_validation = false;

        final NoteAdapter adapter = new NoteAdapter(getContext(),tripNotes,notesRecyclerView);

        final SwipeController swipeController = new SwipeController(200,30,15, 107, new SwipeControllerAction() {
            @Override
            public void onLeftClicked(final int position) {

                new LovelyTextInputDialog(getContext())
                        .setTopColorRes(R.color.colorPrimary)
                        .setMessage(R.string.updateNote)
                        .setIcon(R.drawable.ic_note_add_black_24dp)
                        .setInputFilter(R.string.tripError, new LovelyTextInputDialog.TextFilter() {
                            @Override
                            public boolean check(String text) {
                                return text.matches("\\w+");
                            }
                        })
                        .setConfirmButton(R.string.update, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                            @Override
                            public void onTextInputConfirmed(String text) {
                                   tripNotes.set(position,text);
                                   adapter.notifyItemChanged(position);
                            }
                        })
                        .setCancelable(true)
                        .show();
            }

            @Override
            public void onRightClicked(int position) {
                // dialog
                  AskOption( position , adapter);

            }
        });

        notesRecyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 2));
        ItemTouchHelper helper = new ItemTouchHelper(swipeController);
        helper.attachToRecyclerView(notesRecyclerView);
        notesRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

       notesRecyclerView.setAdapter(adapter);



        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LovelyTextInputDialog(getContext())
                        .setTopColorRes(R.color.colorPrimary)
                        .setMessage(R.string.noteTitle)
                        .setIcon(R.drawable.ic_note_add_black_24dp)
                        .setInputFilter(R.string.tripError, new LovelyTextInputDialog.TextFilter() {
                            @Override
                            public boolean check(String text) {
                                return text.matches("\\w+");
                            }
                        })
                        .setConfirmButton(R.string.addNote, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                            @Override
                            public void onTextInputConfirmed(String text) {
                                if(!text.isEmpty())
                                    tripNotes.add(text);

                            }
                        })
                        .setCancelable(true)
                        .show();
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

                if(validate()) {
                    Trip newTrip = new Trip(tripNameField.getText().toString(),  startLatit,  startLongit, startName,  endLatit,  endLongit, endName, dateAndTime, repeat, roundTrip, tripNotes);
                    Adapter myAdapter = new Adapter(getActivity());
                    long trip_id = myAdapter.insert_trip(newTrip);
                    newTrip.setTrip_id((int) trip_id);
                    Log.i(TAG, "trip id : " + trip_id);
                    myAdapter.insert_Notes(newTrip);

                    /////
                    // set Reminder
                    /////

                    NotificationScheduler.setReminder(getActivity(),AlarmActivity.class, newTrip.getTrip_id(), hour, min, day , month, year , newTrip);


                    gotoHomeActivity((AppCompatActivity) getActivity());
                }
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


        autocompleteFragmentStart = (PlaceAutocompleteFragment)
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

                directions_start_validation = true;

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
                Log.i("test22", "end Place: " + place.getName());
                Log.i("test22", "end Place: " + place.getLatLng().longitude);
                Log.i("test22", "end Place: " + place.getLatLng().latitude);
                endLatit =  place.getLatLng().latitude;
                endLongit = place.getLatLng().longitude;
                endName = place.getName().toString();

                directions_end_validation = true;

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

        getActivity().finish();
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
            tripNameField.setError(null);
        }
        if(!directions_start_validation)
        {
            ((EditText) autocompleteFragmentStart.getView().findViewById(R.id.place_autocomplete_search_input)).setHint("please enter start point");
            ((EditText) autocompleteFragmentStart.getView().findViewById(R.id.place_autocomplete_search_input)).setError("please enter start point");
        }else{
            counter ++;
            ((EditText) autocompleteFragmentStart.getView().findViewById(R.id.place_autocomplete_search_input)).setError(null);

        }
        if(!directions_end_validation)
        {
            ((EditText) autocompleteFragmentEnd.getView().findViewById(R.id.place_autocomplete_search_input)).setHint("please enter end point");
            ((EditText) autocompleteFragmentEnd.getView().findViewById(R.id.place_autocomplete_search_input)).setError("please enter end point");
        }else{
            counter ++;
            ((EditText) autocompleteFragmentEnd.getView().findViewById(R.id.place_autocomplete_search_input)).setError(null);
        }
        if(dateField.getText().toString().equalsIgnoreCase(""))
        {
            dateField.setHint("please enter date");
            dateField.setError("please enter date");//it gives user to info message //use any one //
        }else{
            counter ++;
            dateField.setError(null);
        }
        if(timeField.getText().toString().equalsIgnoreCase(""))
        {
            timeField.setHint("please enter time");
            timeField.setError("please enter time");//it gives user to info message //use any one //
        }else{
            counter ++;
            timeField.setError(null);
        }

        if(counter == 5)
            return true;
        else
            return false;
    }

    private AlertDialog AskOption(final int position , final NoteAdapter adapter)
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(getContext())
                //set message, title, and icon
                .setTitle("Delete trip")
                .setMessage("Are you sure? ")
                .setIcon(R.drawable.ic_delete_forever_black_24dp)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                       dialog.dismiss();
                    }

                })

                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.noteDataList.remove(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeRemoved(position, adapter.getItemCount());
                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }

}
