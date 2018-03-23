package com.app.egh.tripplanner.activities;

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
import com.app.egh.tripplanner.data.model.Trip;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    public  static List<Trip> allTrips;
    RecyclerView recyclerView;
    TextView emptyLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        allTrips = new ArrayList<>();

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

       Trip trip = new Trip();
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
        allTrips.add(trip);

        emptyLabel = findViewById(R.id.noTripsLabel);
        // define other variables

        if(HomeActivity.allTrips.size() > 0)
            emptyLabel.setVisibility(View.INVISIBLE);
        else
            emptyLabel.setVisibility(View.VISIBLE);

        final TripAdapter adapter = new TripAdapter(this,allTrips,recyclerView);
        final SwipeController swipeController = new SwipeController(new SwipeControllerAction() {
            @Override
            public void onLeftClicked(int position) {
                Toast.makeText(getApplicationContext(), "Go to edit activity", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRightClicked(int position) {
                adapter.tripDataList.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeRemoved(position, adapter.getItemCount());
                if (HomeActivity.allTrips.size() > 0)
                    emptyLabel.setVisibility(View.INVISIBLE);
                else
                    emptyLabel.setVisibility(View.VISIBLE);
            }
        });

        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 12));
        ItemTouchHelper helper = new ItemTouchHelper(swipeController);
        helper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

        recyclerView.setAdapter(adapter);

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
        return  super.onOptionsItemSelected(item);
    }
}
