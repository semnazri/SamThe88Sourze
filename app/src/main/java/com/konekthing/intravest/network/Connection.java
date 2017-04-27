/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.konekthing.intravest.network.cache.LruBitmapCache;

public class Connection {

    private static Connection sInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private Connection(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache(context));
    }

    public static synchronized Connection getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Connection(context);
        }
        return sInstance;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        mRequestQueue.add(req);
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
