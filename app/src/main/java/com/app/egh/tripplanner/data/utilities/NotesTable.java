package com.app.egh.tripplanner.data.utilities;

/**
 * Created by toshiba on 3/19/2018.
 */

public class NotesTable {

    public static final String NOTES_TABLE_NAME = "NOTES";

    public static final String TRIP_COLUMN_ID = "trip_id"; // each trip has its own notes
    public static final String NOTES_COLUMN_CONTENT = "note_content";

    public static String createTable(){
        String createTable_DDL = "CREATE TABLE IF NOT EXISTS "
                + NOTES_TABLE_NAME + "("
                + TRIP_COLUMN_ID + " INTEGER, "
                + NOTES_COLUMN_CONTENT + " TEXT " + ")";

        return createTable_DDL;
    }
}
