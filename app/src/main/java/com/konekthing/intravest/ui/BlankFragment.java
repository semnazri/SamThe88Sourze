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
 * A simple {@link BaseFragment} subclass.
 */
public class BlankFragment extends BaseFragment {


    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    protected void setTitle(CharSequence title) {
    }

}
