package com.app.egh.tripplanner.activities;

import android.content.Intent;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.egh.tripplanner.R;
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

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            //logged out
            finish();
            startActivity(new Intent(this,SignInActivity.class));
        }

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        Toast.makeText(this,"Welcome "+firebaseUser.getEmail(),Toast.LENGTH_LONG).show();
        //=======================================================================//

       /* Trip trip = new TripData("ITI","Hadayek Elkobba","Smart village");
        allTrips.add(trip);

        trip = new TripData("Home", "Smart village","Hadayek Elkobba");
        allTrips.add(trip);

        trip = new TripData("Work", "Hadayek Elkobba","October");
        allTrips.add(trip);

        trip = new TripData("Dr", "Madent Nasr","Mohandssen");
        allTrips.add(trip);

        trip = new TripData("Club", "El dokki","Madent Nasr");
        allTrips.add(trip);*/


       /*Trip trip = new Trip();
       trip.setTrip_name("ITI");
       trip.setStart_name("Hadayek Elkobba");
       trip.setEnd_name("Smart village");
       allTrips.add(trip);

        trip = new Trip();
        trip.setTrip_name("Home");
        trip.setStart_name("Smart village");
        trip.setEnd_name("Hadayek Elkobba");
        allTrips.add(trip);

        trip = new Trip();
        trip.setTrip_name("Work");
        trip.setStart_name("Hadayek Elkobba");
        trip.setEnd_name("October");
        allTrips.add(trip);

        trip = new Trip();
        trip.setTrip_name("Dr");
        trip.setStart_name("Madent Nasr");
        trip.setEnd_name("Mohandssen");
        allTrips.add(trip);

        trip = new Trip();
        trip.setTrip_name("Club");
        trip.setStart_name("Madent Nasr");
        trip.setEnd_name("El dokki");
        allTrips.add(trip);*/

        // define other variables

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_history) {
            //do action
            return true;
        }else if (id == R.id.action_sync) {
            // do action
            return true;
        }
        else if (id == R.id.action_logout) {
            // do action
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,SignInActivity.class));
            return true;
        }
        return  super.onOptionsItemSelected(item);
    }
}
