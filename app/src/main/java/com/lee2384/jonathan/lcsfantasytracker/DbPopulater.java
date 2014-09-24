package com.lee2384.jonathan.lcsfantasytracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.lee2384.jonathan.lcsfantasytracker.dbtables.LcsMatchTable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by Jonathan on 7/22/2014.
 */
public class DbPopulater {

    private static final String TAG = "db populator";
    //private SQLiteDatabase database;

    private String[] scheduleUri = {"http://na.lolesports.com/api/schedule.json?tournamentId=102",
            "http://na.lolesports.com/api/schedule.json?tournamentId=104" };

    // json names in Match object
    private final String NAME_TOURNEY = "tournament";
    private final String NAME_ROUND = "round";
    private final String NAME_NAME = "name";
    private final String NAME_DATETIME = "dateTime";
    private final String NAME_WINNER_ID = "winnerId";
    private final String NAME_CONTESTANTS = "contestants";

    // nested object 'contestants' in json
    private final String NAME_BLUE = "blue";
    private final String NAME_RED = "red";

    // nested names in blue/red
    private final String NAME_ID = "id";
    private final String NAME_TEAM_NAME = "name";

    //private final String KEY_DATETIME = "dateTime";
    private Context context;

    public DbPopulater(Context context) {
       this.context = context;
    }

    public void populateDb(SQLiteDatabase database) {

       // LcsMatchDatabaseHelper dbHelper = new LcsMatchDatabaseHelper(context);
        //SQLiteDatabase database = dbHelper.getWritableDatabase();

        // now open the connection
        ConnectivityManager connMngr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMngr.getActiveNetworkInfo();

        // load lcs data
        if (networkInfo != null && networkInfo.isConnected() ) {

            //JsonRetriever jsonRetriever = new JsonRetriever();

            JSONObject temp;
            Log.d(TAG, "begin transaction");

            long startTime = System.currentTimeMillis();

            database.beginTransaction();
            // pulls schedule jsons from na.lolesports.com
            for(int i=0; i<scheduleUri.length; i++) {

                SQLiteStatement statement;
                String sql = "INSERT INTO "+ LcsMatchTable.TABLE_NAME+
                        " (" +
                        LcsMatchTable.COLUMN_NAME + ", " +
                        LcsMatchTable.COLUMN_ROUND +  ", " +
                        LcsMatchTable.COLUMN_DATE + ", " +
                        LcsMatchTable.COLUMN_TIME + ", " +
                        LcsMatchTable.COLUMN_WINNER_ID + ", " +
                        LcsMatchTable.COLUMN_TEAM_ONE + ", " +
                        LcsMatchTable.COLUMN_TEAM_TWO +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?)";

                statement = database.compileStatement(sql);
                statement.clearBindings();

                long downloadStart = System.currentTimeMillis();
                temp = JsonRetriever.getJsonFromUri(scheduleUri[i]);
                long downloadTime = System.currentTimeMillis() - downloadStart;
                Log.d(TAG, Long.toString(downloadTime));

                Iterator<String> keys = temp.keys();

                while(keys.hasNext()) {
                    String matchName = keys.next();
                    JSONObject jMatch;

                    try {
                        jMatch = temp.getJSONObject(matchName);

                        /*
                        ContentValues initialValues = new ContentValues();
                        initialValues.put(LcsMatchTable.COLUMN_NAME, jMatch.getString(KEY_NAME));
                        initialValues.put(LcsMatchTable.COLUMN_ROUND, jMatch.getJSONObject(KEY_TOURNEY).getInt(KEY_ROUND));
                        */

                        // database.insert(LcsMatchTable.TABLE_MATCH, null, initialValues);
                        statement.bindString(1, jMatch.getString(NAME_NAME));
                        statement.bindLong(2, jMatch.getJSONObject(NAME_TOURNEY).getInt(NAME_ROUND));
                        // dateTime is stored on json as yyyy-mm-ddThh:mmZ
                        String date = jMatch.getString(NAME_DATETIME);
                        statement.bindString(3, date.substring(0,10));
                        statement.bindString(4, date.substring(11,16));

                        statement.bindString(5, jMatch.getString(NAME_WINNER_ID));
                        statement.bindString(6,
                                jMatch.getJSONObject(NAME_CONTESTANTS)
                                        .getJSONObject(NAME_BLUE).getString(NAME_TEAM_NAME));
                        statement.bindString(7,
                                jMatch.getJSONObject(NAME_CONTESTANTS)
                                        .getJSONObject(NAME_RED).getString(NAME_TEAM_NAME));
                        statement.executeInsert();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            database.setTransactionSuccessful();
            database.endTransaction();

            long diff = System.currentTimeMillis() - startTime;

            Log.d(TAG, "finished transaction");
            Log.d(TAG, "total time: " + diff);
            //MainActivity.setCheck();
            //dbHelper.close();
        }
    }
}
