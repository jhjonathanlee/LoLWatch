package com.lee2384.jonathan.lcsfantasytracker;

/**
 * Created by Jonathan on 7/5/2014.
 */
public interface SportsScheduleTracker {

    // return the season's schedule
    public void returnSchedule();

    // returns the latest game
    public void returnLatest();

    // returns the upcoming game
    public void returnNext();

    // the sport this tracker is following
    public String getSport();

}
