/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konekthing.intravest.App;
import com.konekthing.intravest.R;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class OpportunitiesSearchFragment extends BaseFragment {

    private SearchView mSearchView;

    public OpportunitiesSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opportunities_search, container, false);
        mSearchView = (SearchView) view.findViewById(R.id.search);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) view.setBackgroundResource(R.color.ripple_material_light);
                else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        view.setBackground(null);
                    } else {
                        //noinspection deprecation
                        view.setBackgroundDrawable(null);
                    }
                }
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                startActivity(new Intent(getActivity(), OpportunityActivity.class));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    protected void setTitle(CharSequence title) {
    }

    @Override
    public void onDestroyView() {
        mSearchView = null;
        super.onDestroyView();
    }
}
