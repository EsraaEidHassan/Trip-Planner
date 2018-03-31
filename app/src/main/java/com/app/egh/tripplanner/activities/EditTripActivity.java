package com.app.egh.tripplanner.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.egh.tripplanner.R;

public class EditTripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
