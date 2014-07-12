package com.lee2384.jonathan.lcsfantasytracker;

import org.json.JSONObject;

import java.sql.Date;

/**
 * Created by Jonathan on 7/5/2014.
 */
public class LcsScheduleTracker implements SportsScheduleTracker {

    public LcsScheduleTracker(Date start, Date end) {
        setUp();
    }

    private JSONObject setUp() {
        return null;
    }

    // return the season's schedule
    public void getSchedule(){

    }

    // returns the latest game
    public void getLatest(){

    }

    // returns the upcoming game
    public void getNext(){

    }

    // the sport this tracker is following
    public String getSport(){
        throw new UnsupportedOperationException();
    }

}
