package com.gravityrd.jofogas.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gravityrd.jofogas.R;

import static android.provider.Settings.Secure.*;

/**
 * Created by Zsolt on 10/29/2014.
 */
public class DebugIdFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        String debugId = Settings.Secure.getString(getActivity().getContentResolver(),Settings.Secure.ANDROID_ID);
        final View rootView = inflater.inflate(R.layout.debugid_fragment, container, false);

        TextView debugText = (TextView)  rootView.findViewById(R.id.debugidtext);
        debugText.setText(debugId);
        return rootView;
    }
}
