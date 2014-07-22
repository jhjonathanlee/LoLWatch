package com.lee2384.jonathan.lcsfantasytracker;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jonathan on 7/15/2014.
 */
public class ScheduleLoader extends AsyncTaskLoader<List<LcsMatch>> {

    private String[] scheduleUri = {"http://na.lolesports.com/api/schedule.json?tournamentId=102",
            "http://na.lolesports.com/api/schedule.json?tournamentId=104" };

    private final String KEY_TOURNEY = "tournament";
    private final String KEY_ROUND = "round";
    private final String KEY_NAME = "name";
    private final String KEY_DATETIME = "dateTime";

    private List<LcsMatch> scheduleList;

    public ScheduleLoader(Context context) {
        super(context);
    }

    @Override
    public List<LcsMatch> loadInBackground() {

        JsonRetriever jsonRetriever = new JsonRetriever();

        //JSONObject[] jObjTemp = new JSONObject[scheduleUri.length];

        List<LcsMatch> tempList = new ArrayList<LcsMatch>(12);
        JSONObject temp;

        // pulls schedule jsons from na.lolesports.com and turns them into objects in a list
        for(int i=0; i<scheduleUri.length; i++) {
            temp = jsonRetriever.getJsonFromUri(scheduleUri[i]);
            Iterator<String> keys = temp.keys();
            while(keys.hasNext()) {
                String matchName = keys.next();
                JSONObject jMatch;
                LcsMatch match;

                try {
                    jMatch = temp.getJSONObject(matchName);
                    match = new LcsMatch(jMatch.getString(KEY_NAME), jMatch.getString(KEY_DATETIME),
                            jMatch.getJSONObject(KEY_TOURNEY).getInt(KEY_ROUND));

                    tempList.add(match);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return tempList;
    }

    @Override
    public void deliverResult(List<LcsMatch> schedule) {
        if (isReset()) {
            // An async query came in while the loader is stopped. We don't need the results
            if(schedule != null) {
                onReleaseResources(schedule);
            }
        }

        List<LcsMatch> oldSchedule = scheduleList;
        scheduleList = schedule;

        if(isStarted()) {
            // if the loader is started, immediately deliver results
            super.deliverResult(schedule);
        }

        if(oldSchedule != null) {
            onReleaseResources(oldSchedule);
        }
    }

    @Override
    protected void onStartLoading() {
        if(scheduleList != null) {
            // if result is available, deliver immediately
            deliverResult(scheduleList);
        }

        if(takeContentChanged() || scheduleList == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(List<LcsMatch> schedule) {
        super.onCanceled(schedule);

        onReleaseResources(schedule);
    }

    @Override
    protected void onReset() {
        super.onReset();

        // ensure the loader is stopped
        onStopLoading();

        // release resources
        if(scheduleList != null) {
            onReleaseResources(scheduleList);
            scheduleList = null;
        }

    }

    protected void onReleaseResources(List<LcsMatch> schedule) {
        // do nothing since it's just a List
        // may change in the future
    }
}
