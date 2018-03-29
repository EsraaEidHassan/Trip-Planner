package com.app.egh.tripplanner.fragments;


import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.egh.tripplanner.R;
import com.app.egh.tripplanner.activities.AddTripActivity;
import com.app.egh.tripplanner.activities.DetailedActivity;
import com.app.egh.tripplanner.activities.HomeActivity;
import com.app.egh.tripplanner.activitiesHelpers.MyDividerItemDecoration;
import com.app.egh.tripplanner.activitiesHelpers.SwipeController;
import com.app.egh.tripplanner.activitiesHelpers.SwipeControllerAction;
import com.app.egh.tripplanner.activitiesHelpers.TripAdapter;
import com.app.egh.tripplanner.data.model.Adapter;
import com.app.egh.tripplanner.data.model.Trip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    // views

    FloatingActionButton fab;
    RecyclerView recyclerView;
    TextView emptyLabel;
    FirebaseAuth firebaseAuth;

    // variables
    public  static List<Trip> allTrips;
    Adapter dbAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        // define views
        fab = view.findViewById(R.id.addTripBtn);
        recyclerView = view.findViewById(R.id.recyclerView);
        emptyLabel = view.findViewById(R.id.noTripsLabel);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // define variables

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        allTrips = new ArrayList<>();
        dbAdapter = new Adapter(getContext());
        allTrips = dbAdapter.getAllTrips();

        if(allTrips.size() > 0)
            emptyLabel.setVisibility(View.INVISIBLE);
        else
            emptyLabel.setVisibility(View.VISIBLE);

        final TripAdapter adapter = new TripAdapter(getContext(),allTrips,recyclerView);

        //add listiners to views
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoAddTripActivity((AppCompatActivity) getActivity());
            }
        });

        final SwipeController swipeController = new SwipeController(300,60,15,315,new SwipeControllerAction() {
            @Override
            public void onLeftClicked(int position) {
                Toast.makeText(getContext(), "Go to edit activity", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRightClicked(int position) {
                adapter.tripDataList.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeRemoved(position, adapter.getItemCount());
                if (allTrips.size() > 0)
                    emptyLabel.setVisibility(View.INVISIBLE);
                else
                    emptyLabel.setVisibility(View.VISIBLE);

                Boolean isDeleted = dbAdapter.deleteTrip(allTrips.get(position).getTrip_id());
                if(isDeleted)
                    Toast.makeText(getContext(),"Trip Delete Successfuly !",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getContext(),"Cann't delete this Trip !",Toast.LENGTH_LONG).show();
            }
        });

        recyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 12));
        ItemTouchHelper helper = new ItemTouchHelper(swipeController);
        helper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

        recyclerView.setAdapter(adapter);

        return view;
    }

    private void gotoAddTripActivity(AppCompatActivity activity){

        Intent intent = new Intent(activity, AddTripActivity.class);
        startActivity(intent);

    }

}
