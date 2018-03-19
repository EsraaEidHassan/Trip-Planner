package com.app.egh.tripplanner.data.utilities;

/**
 * Created by toshiba on 3/17/2018.
 */

public class TripTable {

    public static final String TRIP_TABLE_NAME = "TRIP";

    public static final String TRIP_COLUMN_ID = "trip_id";
    public static final String TRIP_COLUMN_NAME = "trip_name";
    public static final String TRIP_COLUMN_START_POINT_LAT = "start_point_lat";
    public static final String TRIP_COLUMN_START_POINT_LONG = "start_point_long";
    public static final String TRIP_COLUMN_START_POINT_NAME = "start_point_name";
    public static final String TRIP_COLUMN_END_POINT_LAT = "end_point_lat";
    public static final String TRIP_COLUMN_END_POINT_LONG = "end_point_long";
    public static final String TRIP_COLUMN_END_POINT_NAME = "end_point_name";
    public static final String TRIP_COLUMN_DATE_TIME = "trip_date_time";
    public static final String TRIP_COLUMN_REPEATED = "repeated";
    public static final String TRIP_COLUMN_ROUNDTRIP = "roundtrip";

    public static String createTable(){
        String createTable_DDL = "CREATE TABLE IF NOT EXISTS "
                + TRIP_TABLE_NAME + "("
                + TRIP_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TRIP_COLUMN_NAME + " VARCHAR(50), "
                + TRIP_COLUMN_START_POINT_LAT + " TEXT, "
                + TRIP_COLUMN_START_POINT_LONG + " TEXT, "
                + TRIP_COLUMN_START_POINT_NAME + " VARCHAR(50), "
                + TRIP_COLUMN_END_POINT_LAT + " TEXT, "
                + TRIP_COLUMN_END_POINT_LONG + " TEXT, "
                + TRIP_COLUMN_END_POINT_NAME + " VARCHAR(50), "
                + TRIP_COLUMN_DATE_TIME + " DATETIME, "
                + TRIP_COLUMN_REPEATED + " BOOLEAN, "
                + TRIP_COLUMN_ROUNDTRIP + " BOOLEAN " + ")";

        return createTable_DDL;
    }

}
