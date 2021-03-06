package com.gravityrd.jofogas.activities;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class App extends Application {

    private static Context mContext;

    public static Context getContext(){
        return mContext;
    }
    @Override
    public void onCreate() {
        mContext = this;
        super.onCreate();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).memoryCache(new WeakMemoryCache()).denyCacheImageMultipleSizesInMemory()
                .threadPoolSize(5)
                .build();
        ImageLoader.getInstance().init(config);

    }
}
