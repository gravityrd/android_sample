package com.gravityrd.jofogas.activities;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class App extends Application {

    private static Context mContext;

    public static Context getContext(){
        return mContext;
    }
    @Override
    public void onCreate() {
        mContext = this;
        super.onCreate();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
        ImageLoader.getInstance().init(config);
    }
}
