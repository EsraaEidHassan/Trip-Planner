package com.app.egh.tripplanner.data.model;

/**
 * Created by gehad on 3/19/18.
 */

public class TripData {
    private String tripName;
    private String startLocation;
    private String destination;

    public TripData(String tripName, String startLocation, String destination){
        this.tripName = tripName;
        this.startLocation = startLocation;
        this.destination = destination;
    }
    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTripName() {
        return tripName;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getDestination() {
        return destination;
    }
}
