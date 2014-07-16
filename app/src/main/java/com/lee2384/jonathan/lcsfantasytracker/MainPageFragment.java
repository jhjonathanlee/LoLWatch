package com.lee2384.jonathan.lcsfantasytracker;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.LinkedList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class MainPageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainPageFragment newInstance(String param1, String param2) {
        MainPageFragment fragment = new MainPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MainPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_main_page, container, false);

        LinkedList<String> matchupList = new LinkedList<String>();

        final LinearLayout mLinearLayout = (LinearLayout) v.findViewById(R.id.main_page_linear);

        displayNextMatches(mLinearLayout);

        // get context
        Context context = getActivity().getApplicationContext();

        // Inflate the layout for this fragment
        return v;
    }

    private class GetScheduleTask extends AsyncTask<Void, Void, JSONObject> {

        private final String uri = "http://na.lolesports.com/api/schedule.json?tournamentId=102";

        private final WeakReference<LinearLayout> linearLayoutReference;
        //JSONObject jsonObject = null;

        public GetScheduleTask(LinearLayout linearLayout) {
            linearLayoutReference = new WeakReference<LinearLayout>(linearLayout);
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            JsonRetriever jsonRetriever = new JsonRetriever();
            JSONObject jsonObject = jsonRetriever.getJsonFromUri(uri);
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {

            String matches;
            JSONObject j;
            int i = 0;
            try {
                j = jsonObject.getJSONObject("Match2402");
                i = j.getInt("matchId");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            TextView testText = new TextView(getActivity());
            testText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            testText.setText(Integer.toString(i));
            final LinearLayout l = linearLayoutReference.get();
            l.addView(testText);
        }

    }

    public void displayNextMatches(LinearLayout linearLayout) {

        ImageView teamOneImage = new ImageView(getActivity());
        ImageView teamTwoImage = new ImageView(getActivity());

        teamOneImage.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2f));
        teamTwoImage.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2f));

        teamOneImage.setImageResource(R.drawable.lmq_logo_hdpi);
        teamTwoImage.setImageResource(R.drawable.cloud9_logo_hdpi);

        linearLayout.addView(teamOneImage);
        linearLayout.addView(teamTwoImage);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        public void onFragmentInteraction(Uri uri);
    }

}
