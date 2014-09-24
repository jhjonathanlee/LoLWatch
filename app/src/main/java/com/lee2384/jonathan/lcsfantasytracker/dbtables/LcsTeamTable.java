package com.lee2384.jonathan.lcsfantasytracker.dbtables;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Jonathan on 9/23/2014.
 */
public class LcsTeamTable {

    //table name
    public static final String TABLE_NAME = "team";
    // primary key
    public static final String PLAYER_ID = "_idPlayer";
    public static final String PLAYER_NAME = "playerName";
    public static final String TEAM_ID = "_idTeam";

    private static final String DATABASE_CREATE = "create table " +
            TABLE_NAME +
            "(" +
            PLAYER_ID + " integer primary key, " +
            PLAYER_NAME + " text not null, " +
            TEAM_ID + " integer" +
            ")";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        Log.d("LcsMatchTable", "database created");
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(LcsMatchTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }


}
