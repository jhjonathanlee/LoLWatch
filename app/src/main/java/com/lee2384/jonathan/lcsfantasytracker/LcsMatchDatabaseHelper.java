package com.lee2384.jonathan.lcsfantasytracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jonathan on 7/20/2014.
 */
public class LcsMatchDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "lcsmatchtable.db";
    private static final int DATABASE_VERSION = 1;

    public LcsMatchDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // called during creation of database
    @Override
    public void onCreate(SQLiteDatabase database) {
        LcsMatchTable.onCreate(database);
    }

    // for upgrade i.e. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        LcsMatchTable.onUpgrade(database, oldVersion, newVersion);
    }
}
