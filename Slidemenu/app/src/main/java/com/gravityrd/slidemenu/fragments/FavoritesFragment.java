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
public class FavoritesFragment extends Fragment {

    public FavoritesFragment(){};


    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_favorites,container,false);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.favoritesicon:

                }
            }
        });
        return rootView;
    };
}
