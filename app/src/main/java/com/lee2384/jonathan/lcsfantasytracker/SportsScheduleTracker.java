package com.lee2384.jonathan.lcsfantasytracker;

/**
 * Created by Jonathan on 7/5/2014.
 */
public interface SportsScheduleTracker {

    // return the season's schedule
    public void getSchedule();

    // returns the latest game
    public void getLatest();

    // returns the upcoming game
    public void getNext();

    // the sport this tracker is following
    public String getSport();

}
