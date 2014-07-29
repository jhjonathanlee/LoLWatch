package com.lee2384.jonathan.lcsfantasytracker;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;


import com.lee2384.jonathan.lcsfantasytracker.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p />
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p />
 * Activities containing this fragment MUST implement the {@link
 * interface.
 */
public class ScheduleListFragment extends android.support.v4.app.Fragment
        implements //AbsListView.OnItemClickListener,
        android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_KEY = "ScheduleListFragment";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String KEY_INTEGER = "integer";
    // parameter for loader
    private static final String KEY_DATE = "date";

    //private LcsScheduleAdapter mAdapter;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    //private AbsListView mListView;
    private ExpandableListView mListView;

    List<String> dateList;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private SimpleCursorTreeAdapter tAdapter;

    private int round;

    public static ScheduleListFragment newInstance(int i) {
        ScheduleListFragment fragment = new ScheduleListFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_INTEGER, i);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ScheduleListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.round = getArguments().getInt(KEY_INTEGER)+1;
        }

        Log.d(LOG_KEY, "top : " + round);
        dateList = new ArrayList<String>(6);

        getLoaderManager().initLoader(-1, null, this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedulelist, container, false);

        // Set the adapter  //((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
        //mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView = (ExpandableListView) view.findViewById(android.R.id.list);

        /*
        mAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, null,
                new String[] {LcsMatchTable.COLUMN_NAME}, new int[] {android.R.id.text1}, 0);
        */

        //mListView.setAdapter(mAdapter);

        tAdapter = new MyCursorTreeAdapter(getActivity(), null,
                android.R.layout.simple_expandable_list_item_1,
                new String[] {LcsMatchTable.COLUMN_DATE},
                new int[] { android.R.id.text1 },
                android.R.layout.simple_list_item_1,
                new String[] {LcsMatchTable.COLUMN_NAME },
                new int[] { android.R.id.text1});

        mListView.setAdapter(tAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
       // mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

/*
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }
*/
    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyText instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    /**
     * Loader methods
     */

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {

        LcsMatchCursorLoader cl;
        String[] projection;
        String selection;
        boolean distinct;
        if(id > -1 ) {
            // child view
            String date = "\"" + args.getString(KEY_DATE) + "\"";
            //Log.d(LOG_KEY, "DATE TEST: " + date);
            projection = new String[] { LcsMatchTable.COLUMN_ID, LcsMatchTable.COLUMN_NAME, LcsMatchTable.COLUMN_DATE };
            selection = LcsMatchTable.COLUMN_ROUND + "=" + (this.round) + " AND " + LcsMatchTable.COLUMN_DATE + "=" + date;
            //selection = LcsMatchTable.COLUMN_ROUND + "=" + this.round;
            //selection = LcsMatchTable.COLUMN_NAME + "=" + test;
            //selection = null;
            distinct = false;
            cl = new LcsMatchCursorLoader(getActivity(), projection, selection, distinct, null);
        } else {
            // group cursor gets unique dates
            projection =  new String[] {"MIN(" + LcsMatchTable.COLUMN_ID + ") AS _id", LcsMatchTable.COLUMN_DATE };
            //Log.d(LOG_KEY, "round: " + round);
            selection = LcsMatchTable.COLUMN_ROUND + "=" + (this.round);
            distinct = true;
            String groupBy = LcsMatchTable.COLUMN_DATE;
            cl = new LcsMatchCursorLoader(getActivity(), projection, selection, distinct, groupBy);

        }

        return cl;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader loader, Cursor data) {

        int id = loader.getId();

        if (id > -1) {
            // child cursor
            //testAdapter.setChildren
            tAdapter.setChildrenCursor(id, data);
        } else {
            // groupCursor
            tAdapter.setGroupCursor(data);

            Cursor groupCursor = tAdapter.getCursor();
            groupCursor.moveToFirst();
            Log.d(LOG_KEY, Integer.toString(groupCursor.getCount()));

            for(int i = 0; i < groupCursor.getCount(); i++) {
                Bundle args= new Bundle();
                int colDateIndex = groupCursor.getColumnIndex(LcsMatchTable.COLUMN_DATE);
                //Log.d(LOG_KEY, Integer.toString(colDateIndex));
                Log.d(LOG_KEY, groupCursor.getString(colDateIndex));
                args.putString(KEY_DATE,
                        groupCursor.getString(colDateIndex));
                getLoaderManager().initLoader(groupCursor.getPosition(), args, this);
                dateList.add(i, groupCursor.getString(colDateIndex));
                groupCursor.moveToNext();
            }
            Log.d(LOG_KEY, "exit loader call loop");
        }

        //Cursor data = (Cursor) o;
        //mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader loader) {
       //mAdapter.swapCursor(null);
        int id = loader.getId();
        if (id > -1) {
            // child cursor
            //Log.d(LOG_KEY, "cursor closed");
            tAdapter.setChildrenCursor(id, null);
        } else {
            // group cursor
            tAdapter.setGroupCursor(null);
        }

    }

}
