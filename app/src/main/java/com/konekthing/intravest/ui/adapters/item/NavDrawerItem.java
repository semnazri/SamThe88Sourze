/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui.adapters.item;

import android.support.v4.app.Fragment;

/**
 * Created by renjaya on 4/7/15.
 */
public class NavDrawerItem {
    public final String title;
    public final int icon;
    public final Fragment fragment;
    public final boolean primary;

    public NavDrawerItem(String title, int icon, Fragment fragment, boolean primary) {
        this.title = title;
        this.icon = icon;
        this.fragment = fragment;
        this.primary = primary;
    }
}
