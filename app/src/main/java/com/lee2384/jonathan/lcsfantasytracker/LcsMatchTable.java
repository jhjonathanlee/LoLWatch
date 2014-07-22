package com.lee2384.jonathan.lcsfantasytracker;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Jonathan on 7/20/2014.
 */
public class LcsMatchTable {

    // Database table
    public static final String TABLE_MATCH = "match";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ROUND = "round";
    public static final String[] allCol = { COLUMN_ID, COLUMN_NAME, COLUMN_ROUND };

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_MATCH
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_ROUND + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(LcsMatchTable.class.getName(), "Upgrading database from version "
        + oldVersion + " to " + newVersion
        + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCH);
        onCreate(database);
    }
}
