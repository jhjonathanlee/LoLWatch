package com.lee2384.jonathan.lcsfantasytracker;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

// random comment
public class MainActivity extends Activity
    implements LoaderManager.LoaderCallbacks<List<LcsMatch>>,
    ScheduleListFragment.OnFragmentInteractionListener {

    LcsScheduleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ConnectivityManager connMngr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMngr.getActiveNetworkInfo();

        mAdapter = new LcsScheduleAdapter(this, android.R.layout.simple_list_item_1, new java.util.ArrayList<LcsMatch>());

        if (networkInfo != null && networkInfo.isConnected() ) {
            // preparing schedule loader
            getLoaderManager().initLoader(0, null, this);

        }

        if (findViewById(R.id.fragment_container) != null) {
            if(savedInstanceState != null) {
                return;
            }
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            //LcsScheduleFragment mpFragment = new LcsScheduleFragment();
            ScheduleListFragment mpFragment = ScheduleListFragment.newInstance(mAdapter);
            fragmentTransaction.add(R.id.fragment_container, mpFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<LcsMatch>> onCreateLoader(int id, Bundle args) {
        return new ScheduleLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<LcsMatch>> loader, List<LcsMatch> data) {
        mAdapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<LcsMatch>> loader) {
        //mAdapter.setData(null);
        mAdapter.clear();
    }

    @Override
    public void onFragmentInteraction(String id) {
        // do something
    }
}
