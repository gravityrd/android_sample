package com.gravityrd.slidemenu.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gravityrd.slidemenu.R;

/**
 * Created by zsolt on 2014.07.21..
 */
public class LastVisitedFragment extends Fragment {
    public LastVisitedFragment() {

    }
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_last_visited,container,false);
        return rootView;
    }}