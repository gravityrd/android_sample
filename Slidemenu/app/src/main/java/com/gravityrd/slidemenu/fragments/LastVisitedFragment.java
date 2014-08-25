package com.gravityrd.slidemenu.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gravityrd.slidemenu.R;

import java.util.List;

public class LastVisitedFragment extends Fragment {
    private static List<String> elements;

    public LastVisitedFragment() {

    }

    public static void add(String s) {
        elements.add(s);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_last_visited, container, false);
        return rootView;
    }
}
