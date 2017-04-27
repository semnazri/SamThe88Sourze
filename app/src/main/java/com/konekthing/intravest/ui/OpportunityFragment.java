/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.konekthing.intravest.App;
import com.konekthing.intravest.R;
import com.konekthing.intravest.model.Opportunity;
import com.konekthing.intravest.network.Connection;
import com.konekthing.intravest.network.JacksonListRequest;
import com.konekthing.intravest.ui.adapters.OpportunityAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link com.konekthing.intravest.ui.BaseFragment} subclass.
 */
public class OpportunityFragment extends BaseFragment {

    static final String SEARCH_OPPORTUNITY_URL = "/search/opportunity";
    static final String LIST_OPPORTUNITY_URL = "/list/opportunity";
    static final String PARCEL_OPPORTUNITY = "parcel_opportunity";
    static final String PARCEL_LIST_OPPORTUNITY = "parcel_list_opportunity";

    private View mContentView, mLoadingView;
    private RecyclerView mRecyclerView;
    private OpportunityAdapter mAdapter;
    private String mUrl;

    public OpportunityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String query = "?query=";
        Bundle args = getArguments();
        if (args != null) {
            query += args.getString(HomeFragment.STRING_SEARCH_QUERY);
            mUrl = SEARCH_OPPORTUNITY_URL + query;
        } else {
            mUrl = LIST_OPPORTUNITY_URL;
        }
        mAdapter = new OpportunityAdapter(getActivity(), null);
        if (savedInstanceState == null) {
            SharedPreferences sp = getSharedPreferences();
            getOpportunity(getActivity(), sp.getString(ProfileFragment.PREF_EMAIL, null), sp.getString(ProfileFragment.PREF_PASSWORD, null));
        } else {
            List<Opportunity> list = savedInstanceState.getParcelableArrayList(PARCEL_LIST_OPPORTUNITY);
            mAdapter.setList(list);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supplier, container, false);
        mContentView = view.findViewById(R.id.content_view);
        mLoadingView = view.findViewById(R.id.loading_view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mLoadingView.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mAdapter.getItemCount() == 0) {
            crossfadeInverse(mContentView, mLoadingView);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        changeToolbarAlpha(255);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PARCEL_LIST_OPPORTUNITY, (ArrayList<? extends Parcelable>) mAdapter.getList());
    }

    @Override
    public void onDestroyView() {
        mContentView = mLoadingView = null;
        mRecyclerView = null;
        super.onDestroyView();
    }

    private void getOpportunity(Context context, String username, String password) {
        JacksonListRequest<Opportunity> request = JacksonListRequest.<Opportunity>method(Request.Method.GET)
                .url(App.BASE_URL + mUrl)
                .listener(new Response.Listener<List<Opportunity>>() {
                    @Override
                    public void onResponse(List<Opportunity> response) {
                        mAdapter.setList(response);
                        crossfade(mContentView, mLoadingView);
                    }
                })
                .errorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        crossfade(mContentView, mLoadingView);
                    }
                })
                .clazz(Opportunity.class)
                .username(username)
                .password(password)
                .build();
        request.setTag(getTag());
        Connection.getInstance(context).addToRequestQueue(request);
    }
}
