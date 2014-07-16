package com.lee2384.jonathan.lcsfantasytracker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jonathan on 7/15/2014.
 */
public class LcsScheduleAdapter extends ArrayAdapter<LcsMatch> implements Serializable {

    private final Context context;
    private final List<LcsMatch> matches;
    private final int resId;

    public LcsScheduleAdapter(Context context, int resource, List<LcsMatch> objects) {
        super(context, resource, objects);
        this.context = context;
        this.matches = objects;
        this.resId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = new TextView(context);
            parent.addView(convertView);
        }

        ((TextView) convertView).setText(matches.get(position).getName());

        return convertView;
    }

    public void setData(List<LcsMatch> matchList) {
        clear();
        if (matchList != null) {
            addAll(matchList);
        }
    }
}
