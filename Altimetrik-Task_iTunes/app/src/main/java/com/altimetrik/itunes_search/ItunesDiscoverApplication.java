package com.altimetrik.itunes_search;

import android.app.Application;
import android.content.Context;

public class ItunesDiscoverApplication extends Application {

    /**
     * Instance of our application
     */
    private static ItunesDiscoverApplication instance;

    /**
     *
     * @return Context of our application
     */
    public static Context getContext(){
        return instance;
    }

    @Override
    public void onCreate() {

        instance = this;

        super.onCreate();

    }

}
