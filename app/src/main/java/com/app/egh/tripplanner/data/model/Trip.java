package com.app.egh.tripplanner.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by toshiba on 3/17/2018.
 */

public class Trip implements Parcelable {

    private int trip_id;
    private String trip_name;
    private double start_lat;
    private double start_long;
    private String start_name;
    private double end_lat;
    private double end_long;
    private String end_name;
    private Date date_time;
    private boolean repeated; // enter zero or one in SQLite 3
    private boolean roundtrip; // enter zero or one in SQLite 3
    private boolean started; // enter zero or one in SQLite 3
    private List<String> notes;


    public Trip(String trip_name, double start_lat, double start_long, String start_name, double end_lat, double end_long, String end_name, Date date_time, boolean repeated, boolean roundtrip, List<String> notes) {
        this.trip_name = trip_name;
        this.start_lat = start_lat;
        this.start_long = start_long;
        this.start_name = start_name;
        this.end_lat = end_lat;
        this.end_long = end_long;
        this.end_name = end_name;
        this.date_time = date_time;
        this.repeated = repeated;
        this.roundtrip = roundtrip;
        this.notes = notes;
    }

    public Trip(String trip_name, double start_lat, double start_long, String start_name, double end_lat, double end_long, String end_name, Date date_time, boolean repeated, boolean roundtrip) {
        this.trip_name = trip_name;
        this.start_lat = start_lat;
        this.start_long = start_long;
        this.start_name = start_name;
        this.end_lat = end_lat;
        this.end_long = end_long;
        this.end_name = end_name;
        this.date_time = date_time;
        this.repeated = repeated;
        this.roundtrip = roundtrip;
    }

    public Trip() {

    }

    // getters
    public int getTrip_id() {
        return trip_id;
    }

    public String getTrip_name() {
        return trip_name;
    }

    public double getStart_lat() {
        return start_lat;
    }

    public double getStart_long() {
        return start_long;
    }

    public String getStart_name() {
        return start_name;
    }

    public double getEnd_lat() {
        return end_lat;
    }

    public double getEnd_long() {
        return end_long;
    }

    public String getEnd_name() {
        return end_name;
    }

    public Date getDate_time() {
        return date_time;
    }

    public boolean isRepeated() {
        return repeated;
    }

    public boolean isRoundtrip() {
        return roundtrip;
    }

    public boolean isStarted() {
        return started;
    }

    public List<String> getNotes() {
        if (notes != null)
            return notes;
        else
            return new ArrayList<>();
    }

    // setters
    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }

    public void setStart_lat(double start_lat) {
        this.start_lat = start_lat;
    }

    public void setStart_long(double start_long) {
        this.start_long = start_long;
    }

    public void setStart_name(String start_name) {
        this.start_name = start_name;
    }

    public void setEnd_lat(double end_lat) {
        this.end_lat = end_lat;
    }

    public void setEnd_long(double end_long) {
        this.end_long = end_long;
    }

    public void setEnd_name(String end_name) {
        this.end_name = end_name;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    public void setRoundtrip(boolean roundtrip) {
        this.roundtrip = roundtrip;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(trip_id);
        parcel.writeString(trip_name);
        parcel.writeDouble(start_lat);
        parcel.writeDouble(start_long);
        parcel.writeString(start_name);
        parcel.writeDouble(end_lat);
        parcel.writeDouble(end_long);
        parcel.writeString(end_name);
        //parcel.writeValue(date_time);
        parcel.writeLong(date_time != null ? date_time.getTime() : -1);

        parcel.writeInt( repeated ? 1 :0 );
        parcel.writeInt( roundtrip ? 1 :0 );
        parcel.writeInt( started ? 1 :0 );

        //parcel.writeList(notes);
        parcel.writeStringList(notes);
    }

    /** Static field used to regenerate object, individually or as arrays */
    public static final Parcelable.Creator<Trip> CREATOR = new Parcelable.Creator<Trip>() {
        public Trip createFromParcel(Parcel pc) {
            return new Trip(pc);
        }
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    /**Ctor from Parcel, reads back fields IN THE ORDER they were written */
    public Trip(Parcel pc){
        trip_id = pc.readInt();
        trip_name =  pc.readString();
        start_lat = pc.readDouble();
        start_long = pc.readDouble();
        start_name = pc.readString();

        end_lat = pc.readDouble();
        end_long = pc.readDouble();
        end_name = pc.readString();
        //date_time = pc.readValue();
        long tmpDate = pc.readLong();
        date_time = tmpDate == -1 ? null : new Date(tmpDate);

        repeated = ( pc.readInt() == 1 );
        roundtrip = ( pc.readInt() == 1 );
        started = ( pc.readInt() == 1 );

        notes = pc.createStringArrayList();
    }
}
