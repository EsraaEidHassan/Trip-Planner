package com.app.egh.tripplanner.data.utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by toshiba on 3/17/2018.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tripapp.db";



    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TripTable.createTable());
        sqLiteDatabase.execSQL(NotesTable.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TripTable.TRIP_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NotesTable.NOTES_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
