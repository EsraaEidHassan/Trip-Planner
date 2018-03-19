package com.app.egh.tripplanner.data.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.app.egh.tripplanner.data.utilities.DBHelper;
import com.app.egh.tripplanner.data.utilities.NotesTable;
import com.app.egh.tripplanner.data.utilities.TripTable;

/**
 * Created by toshiba on 3/19/2018.
 */

public class Adapter {

    DBHelper dbHelper;

    public Adapter(Context context){

        dbHelper = new DBHelper(context);
    }

    public long insert_trip(Trip trip){

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TripTable.TRIP_COLUMN_NAME,trip.getTrip_name());
        contentValues.put(TripTable.TRIP_COLUMN_START_POINT_LAT,trip.getStart_lat()+"");
        contentValues.put(TripTable.TRIP_COLUMN_START_POINT_LONG,trip.getStart_long()+"");
        contentValues.put(TripTable.TRIP_COLUMN_START_POINT_NAME,trip.getStart_name());
        contentValues.put(TripTable.TRIP_COLUMN_END_POINT_LAT,trip.getEnd_lat()+"");
        contentValues.put(TripTable.TRIP_COLUMN_END_POINT_LONG,trip.getEnd_long()+"");
        contentValues.put(TripTable.TRIP_COLUMN_END_POINT_NAME,trip.getEnd_name());
        contentValues.put(TripTable.TRIP_COLUMN_DATE_TIME, String.valueOf(trip.getDate_time())); // may cause problem

        if(trip.isRepeated())
            contentValues.put(TripTable.TRIP_COLUMN_REPEATED,1);
        else
            contentValues.put(TripTable.TRIP_COLUMN_REPEATED,0);
        if(trip.isRoundtrip())
            contentValues.put(TripTable.TRIP_COLUMN_ROUNDTRIP,1);
        else
            contentValues.put(TripTable.TRIP_COLUMN_ROUNDTRIP,0);

        long rowID = sqLiteDatabase.insert(TripTable.TRIP_TABLE_NAME, null, contentValues);
        sqLiteDatabase.close(); // important
        return rowID; // trip id

    }

    public void insert_Notes(Trip trip){

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        for(int i=0 ; i<trip.getNotes().size() ; i++){

            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesTable.TRIP_COLUMN_ID,trip.getTrip_id());
            contentValues.put(NotesTable.NOTES_COLUMN_CONTENT,trip.getNotes().get(i));
            sqLiteDatabase.insert(NotesTable.NOTES_TABLE_NAME, null, contentValues);
        }
        sqLiteDatabase.close();

    }


}


