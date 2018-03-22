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
import android.widget.Button;
import android.widget.Toast;

import com.app.egh.tripplanner.R;
import com.app.egh.tripplanner.activitiesHelpers.MyDividerItemDecoration;
import com.app.egh.tripplanner.activitiesHelpers.SwipeController;
import com.app.egh.tripplanner.activitiesHelpers.SwipeControllerAction;
import com.app.egh.tripplanner.activitiesHelpers.TripAdapter;
import com.app.egh.tripplanner.data.model.TripData;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    List<TripData> allTrips;
    RecyclerView recyclerView;
    Button startTrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);9
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        allTrips = new ArrayList<>();

        //=======================================================================//

        TripData trip = new TripData("ITI","Hadayek Elkobba","Smart village");
        allTrips.add(trip);

        trip = new TripData("Home", "Smart village","Hadayek Elkobba");
        allTrips.add(trip);

        trip = new TripData("Work", "Hadayek Elkobba","October");
        allTrips.add(trip);

        trip = new TripData("Dr", "Madent Nasr","Mohandssen");
        allTrips.add(trip);

        trip = new TripData("Club", "El dokki","Madent Nasr");
        allTrips.add(trip);

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
                adapter.notifyItemRangeRemoved(position,adapter.getItemCount());
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
