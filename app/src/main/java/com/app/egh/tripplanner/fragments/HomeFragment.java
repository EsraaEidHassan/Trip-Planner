package com.app.egh.tripplanner.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.egh.tripplanner.R;
import com.app.egh.tripplanner.activities.AddTripActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    // views
    FloatingActionButton fab;
    // variables


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

        // define other variables


        //add listiners to views
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoAddTripActivity((AppCompatActivity) getActivity());
            }
        });

        return view;
    }

    private void gotoAddTripActivity(AppCompatActivity activity){

        Intent intent = new Intent(activity, AddTripActivity.class);
        startActivity(intent);

    }

}
