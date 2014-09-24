package com.lee2384.jonathan.lcsfantasytracker;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

// random comment
public class MainActivity extends FragmentActivity implements
    ScheduleListFragment.OnFragmentInteractionListener {

    //public static boolean serviceCheck = false;

    private static final String TAG = "MainActivity";

    private ViewPager viewPager=null;

    private android.support.v4.app.FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Log.d(TAG, "right before service starts");
        //Intent intent = new Intent(this, PopulateDbIntentService.class);
        //startService(intent);

        viewPager=(ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(
                new MyAdapter(
                        getSupportFragmentManager()));

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
    public void onFragmentInteraction(String id) {
        // do something
    }

    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return ScheduleListFragment.newInstance(i);
        }

        @Override
        public CharSequence getPageTitle(int i) {
            return ("Week " + (i+1));
        }

        @Override
        public int getCount() {
            return 11;
        }

    }
}
