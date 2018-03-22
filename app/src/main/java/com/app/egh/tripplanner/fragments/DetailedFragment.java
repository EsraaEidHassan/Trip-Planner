package com.app.egh.tripplanner.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.egh.tripplanner.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailedFragment extends Fragment {


    public DetailedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed, container, false);
    }

}
