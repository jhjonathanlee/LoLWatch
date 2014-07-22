package com.lee2384.jonathan.lcsfantasytracker;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.Iterator;

/**
 * Created by Jonathan on 7/22/2014.
 */
public class PopulateDbIntentService extends IntentService {


    private static final String TAG = "db service";
    //private SQLiteDatabase database;

    private String[] scheduleUri = {"http://na.lolesports.com/api/schedule.json?tournamentId=102",
            "http://na.lolesports.com/api/schedule.json?tournamentId=104" };

    private final String KEY_TOURNEY = "tournament";
    private final String KEY_ROUND = "round";
    private final String KEY_NAME = "name";
    //private final String KEY_DATETIME = "dateTime";

    public PopulateDbIntentService() {
        super("PopulateDbIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //MainActivity.setCheck();
        try {
            populateDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        stopSelf();
    }

    private void populateDb() throws SQLException {
        LcsMatchDatabaseHelper dbHelper = new LcsMatchDatabaseHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // now open the connection
        ConnectivityManager connMngr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMngr.getActiveNetworkInfo();

        // load lcs data
        if (networkInfo != null && networkInfo.isConnected() ) {
            JsonRetriever jsonRetriever = new JsonRetriever();

            JSONObject temp;
            Log.d(TAG, "begin transaction");
            database.beginTransaction();

            // pulls schedule jsons from na.lolesports.com
            for(int i=0; i<scheduleUri.length; i++) {
                temp = jsonRetriever.getJsonFromUri(scheduleUri[i]);
                Iterator<String> keys = temp.keys();
                while(keys.hasNext()) {
                    String matchName = keys.next();
                    JSONObject jMatch;

                    try {
                        jMatch = temp.getJSONObject(matchName);

                        ContentValues initialValues = new ContentValues();
                        initialValues.put(LcsMatchTable.COLUMN_NAME, jMatch.getString(KEY_NAME));
                        initialValues.put(LcsMatchTable.COLUMN_ROUND, jMatch.getJSONObject(KEY_TOURNEY).getInt(KEY_ROUND));

                        database.insert(LcsMatchTable.TABLE_MATCH, null, initialValues);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            database.endTransaction();

            Log.d(TAG, "finished transaction");
            MainActivity.setCheck();
        }
    }
}
