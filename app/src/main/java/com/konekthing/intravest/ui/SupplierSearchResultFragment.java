/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
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
import com.konekthing.intravest.model.Company;
import com.konekthing.intravest.network.Connection;
import com.konekthing.intravest.network.JacksonListRequest;
import com.konekthing.intravest.ui.adapters.FavoriteCompanyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class SupplierSearchResultFragment extends BaseFragment {

    static final String SUPPLIER_URL = "/search/supplier";
    static final String PARCEL_SUPPLIER = "parcel_supplier";
    static final String PARCEL_LIST_SUPPLIER = "parcel_list_supplier";

    private View mContentView, mLoadingView;
    private RecyclerView mRecyclerView;
    private FavoriteCompanyAdapter mAdapter;

    public SupplierSearchResultFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new FavoriteCompanyAdapter(getActivity(), null, null);
        if (savedInstanceState == null) {
            SharedPreferences sp = getSharedPreferences();
            getSupplier(getActivity(), sp.getString(ProfileFragment.PREF_EMAIL, null), sp.getString(ProfileFragment.PREF_PASSWORD, null));
        } else {
            List<Company> list = savedInstanceState.getParcelableArrayList(PARCEL_LIST_SUPPLIER);
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
        outState.putParcelableArrayList(PARCEL_LIST_SUPPLIER, (ArrayList<? extends Parcelable>) mAdapter.getList());
    }

    @Override
    public void onDestroyView() {
        mContentView = mLoadingView = null;
        mRecyclerView = null;
        super.onDestroyView();
    }

    private void getSupplier(Context context, String username, String password) {
        String query = "?query=";
        Bundle args = getArguments();
        if (args != null) {
            query += args.getString(HomeFragment.STRING_SEARCH_QUERY);
        }
        JacksonListRequest<Company> request = JacksonListRequest.<Company>method(Request.Method.GET)
                .url(App.BASE_URL + SUPPLIER_URL + query)
                .listener(new Response.Listener<List<Company>>() {
                    @Override
                    public void onResponse(List<Company> response) {
                        mAdapter.setList(response);
                        crossfade(mContentView, mLoadingView);
                    }
                })
                .errorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        crossfadeInverse(mContentView, mLoadingView);
                    }
                })
                .clazz(Company.class)
                .username(username)
                .password(password)
                .build();
        request.setTag(getTag());
        Connection.getInstance(context).addToRequestQueue(request);
    }
}
