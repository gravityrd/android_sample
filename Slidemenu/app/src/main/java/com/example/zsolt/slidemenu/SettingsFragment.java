package com.example.zsolt.slidemenu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zsolt on 2014.07.21..
 */
public class SettingsFragment extends Fragment {
    public SettingsFragment(){}
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_settings,container,false);
        return rootView;
    }
}
