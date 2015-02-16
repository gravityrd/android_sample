package com.gravityrd.jofogas.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gravityrd.jofogas.R;

import java.util.List;

public class LastVisitedFragment extends Fragment {
    private static List<String> elements;

    public LastVisitedFragment() {

    }

    public static void add(String s) {
        elements.add(s);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.row_grid_item, container, false);
        return rootView;
    }
}
