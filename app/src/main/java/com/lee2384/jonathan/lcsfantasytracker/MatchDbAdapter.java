package com.lee2384.jonathan.lcsfantasytracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by Jonathan on 7/21/2014.
 *
 * Basic CRUD operations for the database
 */
public class MatchDbAdapter {

    private LcsMatchDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    private final Context context;

    /**
     * Constructor
     */
    public MatchDbAdapter(Context context) {
        this.context = context;
    }

    // open database
    public MatchDbAdapter open() throws SQLException {
        dbHelper = new LcsMatchDatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Create a new match entry. If successful, return the rowId, else return -1
     */
    public long createMatch(String name, int round) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(LcsMatchTable.COLUMN_NAME, name);
        initialValues.put(LcsMatchTable.COLUMN_ROUND, round);

        return db.insert(LcsMatchTable.TABLE_MATCH, null, initialValues);
    }

    /**
     * delete the match with the given row id
     */
    public boolean deleteNote(long rowId) {
        return true;
    }

    public Cursor fetchRound(long round) {
        Cursor cursor = db.query(false, LcsMatchTable.TABLE_MATCH, LcsMatchTable.allCol,
                LcsMatchTable.COLUMN_ROUND + "=" + round,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

}
