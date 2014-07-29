package com.lee2384.jonathan.lcsfantasytracker;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.SimpleCursorTreeAdapter;

/**
 * Created by Jonathan on 7/27/2014.
 */
public class MyCursorTreeAdapter extends SimpleCursorTreeAdapter {

    Context mContext;

    public MyCursorTreeAdapter(Context context, Cursor cursor, int groupLayout, String[] groupFrom, int[] groupTo, int childLayout, String[] childFrom, int[] childTo) {
        super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childFrom, childTo);

        mContext = context;
    }

    public MyCursorTreeAdapter(Context context, Cursor cursor, int collapsedGroupLayout, int expandedGroupLayout, String[] groupFrom, int[] groupTo, int childLayout, int lastChildLayout, String[] childFrom, int[] childTo) {
        super(context, cursor, collapsedGroupLayout, expandedGroupLayout, groupFrom, groupTo, childLayout, lastChildLayout, childFrom, childTo);
    }

    public MyCursorTreeAdapter(Context context, Cursor cursor, int collapsedGroupLayout, int expandedGroupLayout, String[] groupFrom, int[] groupTo, int childLayout, String[] childFrom, int[] childTo) {
        super(context, cursor, collapsedGroupLayout, expandedGroupLayout, groupFrom, groupTo, childLayout, childFrom, childTo);
    }

    @Override
    protected Cursor getChildrenCursor(Cursor cursor) {
        Log.d(getClass().getSimpleName().toString(), "getChildrenCursor");
        return null;
    }

}
