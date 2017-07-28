/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.konekthing.intravest.network.AppController;
import com.konekthing.intravest.network.cache.LruBitmapCache2;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by renjaya on 4/7/15.
 */
public class App extends Application {

//    public static final String BASE_URL = "http://demo.konekthing.com/eproks_final/api/v1";
    public static final String BASE_URL = "https://www.thesourze.com/api/v1/";

    private boolean mActivityInForeground;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
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

    public static final String TAG = AppController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static App mInstance;


    public static synchronized App getInstance()   {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache2());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
