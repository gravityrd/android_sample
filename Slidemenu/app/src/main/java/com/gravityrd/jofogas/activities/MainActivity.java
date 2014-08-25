package com.gravityrd.jofogas.activities;

import android.os.Bundle;

import com.gravityrd.jofogas.R;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        slideMenu.displayView(0);
    }

    @Override
    protected int getView() {
        return R.layout.activity_main;
    }
}