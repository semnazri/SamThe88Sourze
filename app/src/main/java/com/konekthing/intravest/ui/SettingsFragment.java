/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konekthing.intravest.R;

/**
 * A simple {@link com.konekthing.intravest.ui.BaseFragment} subclass.
 */
public class SettingsFragment extends BaseFragment {

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings_new, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        changeToolbarAlpha(255);
    }
}
