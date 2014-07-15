package com.lee2384.jonathan.lcsfantasytracker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.provider.ContactsContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;

/**
 * Created by Jonathan on 7/5/2014.
 */
public class LcsScheduleTracker implements SportsScheduleTracker {

    private final String[] uri = {"http://na.lolesports.com/api/schedule.json?tournamentId=102", "http://na.lolesports.com/api/schedule.json?tournamentId=104" };
    private final String naUri = "http://na.lolesports.com/api/schedule.json?tournamentId=104";

    private JSONObject scheduleArray[] = new JSONObject[2];

    public LcsScheduleTracker() {
        setUp();
    }

    private void setUp() {

        //scheduleArray[1] = retriever.getJsonFromUri(naUri);

    }

    public String getMatchTest() {
        String matches="default";

        return matches;
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
