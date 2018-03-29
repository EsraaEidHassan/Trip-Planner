package com.app.egh.tripplanner.data.model;

import java.util.List;

/**
 * Created by toshiba on 3/29/2018.
 */

public class User {

    public List<Trip> trips;

    public User(){

    }

    public User(List<Trip> trips) {
        this.trips = trips;
    }

}
