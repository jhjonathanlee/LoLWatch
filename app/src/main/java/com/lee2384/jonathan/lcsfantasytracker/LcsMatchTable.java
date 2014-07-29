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
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_WINNER_ID = "winnerId";
    public static final String COLUMN_TEAM_ONE = "teamOne";
    public static final String COLUMN_TEAM_TWO = "teamTwo";
    public static final String[] ALL_COL = { COLUMN_ID, COLUMN_NAME, COLUMN_ROUND, COLUMN_DATE,
            COLUMN_TIME, COLUMN_WINNER_ID, COLUMN_TEAM_ONE, COLUMN_TEAM_TWO};

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_MATCH
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_ROUND + " integer, "
            + COLUMN_DATE + " text not null, "
            + COLUMN_TIME + " text not null, "
            + COLUMN_WINNER_ID + " integer, "
            + COLUMN_TEAM_ONE + " text not null, "
            + COLUMN_TEAM_TWO + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        Log.d("LcsMatchTable", "database created");
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(LcsMatchTable.class.getName(), "Upgrading database from version "
        + oldVersion + " to " + newVersion
        + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCH);
        onCreate(database);
    }
}
