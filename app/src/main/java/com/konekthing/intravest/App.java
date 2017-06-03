/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by renjaya on 4/7/15.
 */
public class App extends Application {

//    public static final String BASE_URL = "http://demo.konekthing.com/eproks_final/api/v1";
    public static final String BASE_URL = "http://thesourze.com/eproks/api/v1";

    private boolean mActivityInForeground;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(getApplicationContext());
    }

    public boolean isInForeground() {
        return mActivityInForeground;
    }

    public void setActivityPaused() {
        mActivityInForeground = false;
    }

    public void setActivityResumed() {
        mActivityInForeground = true;
    }

}
