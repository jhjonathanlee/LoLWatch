package com.lee2384.jonathan.lcsfantasytracker;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Jonathan on 7/22/2014.
 */
public class LcsMatchCursorLoader extends AsyncTaskLoader<Cursor> {

    private Cursor mCursor;

    private String[] projection;
    private String selection;
    private SQLiteDatabase database;
    private LcsMatchDatabaseHelper dbHelper;

    public LcsMatchCursorLoader(Context context) {
        super(context);
    }

    public LcsMatchCursorLoader(Context context, String[] projection, String selection) {
        super(context);
        this.projection = projection;
        this.selection = selection;
    }

    @Override
    public Cursor loadInBackground() {

        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dbHelper = new LcsMatchDatabaseHelper(getContext());
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(false, LcsMatchTable.TABLE_MATCH, projection,
                selection, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // UI thread
    @Override
    public void deliverResult(Cursor cursor) {
        if(isReset()) {
            // an async query came in while the loader is stopped
            if(cursor!=null) {
                cursor.close();
            }
            return;
        }
        Cursor oldCursor = mCursor;
        mCursor = cursor;

        if(isStarted()) {
            super.deliverResult(cursor);
        }

        if (oldCursor != null && oldCursor != cursor && !oldCursor.isClosed()) {
            oldCursor.close();
        }
    }

    @Override
    protected void onStartLoading() {
        if(mCursor != null) {
            deliverResult(mCursor);
        }
        if (takeContentChanged() || mCursor == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        // attempt to cancel current load task
        cancelLoad();
    }

    @Override
    public void onCanceled(Cursor cursor) {
        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();

        // ensure the loader is stopped
        onStopLoading();

        if(mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
    }
}
