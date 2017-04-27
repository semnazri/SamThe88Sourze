/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.konekthing.intravest.App;
import com.konekthing.intravest.R;
import com.konekthing.intravest.model.Company;
import com.konekthing.intravest.model.Opportunity;
import com.konekthing.intravest.network.Connection;
import com.konekthing.intravest.network.JacksonListRequest;
import com.konekthing.intravest.ui.adapters.FavoriteCompanyAdapter;
import com.konekthing.intravest.ui.adapters.OpportunityAdapter;

import java.util.List;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class MyFavoritesFragment extends BaseFragment {

    static final String URL_MY_FAVORIT_COMPANIES = "/my_favorite_suppliers";
    static final String URL_MY_FAVORIT_OPPORTUNITIES = "/my_favorite_opportunities";

    private View mContentView, mLoadingView;
    private RecyclerView mRecyclerView;

    public MyFavoritesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        changeToolbarAlpha(255);
        setHasOptionsMenu(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        fetchFavoriteOpportunities();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_my_favorites, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_companies) {
            fetchFavoriteCompanies();
        } else if (item.getItemId() == R.id.action_opportunities) {
            fetchFavoriteOpportunities();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        mContentView = mLoadingView = null;
        mRecyclerView = null;
        super.onDestroyView();
    }

    private void fetchFavoriteCompanies() {
        crossfadeInverse(mContentView, mLoadingView);
        SharedPreferences sp = getSharedPreferences();
        String username = sp.getString(ProfileFragment.PREF_EMAIL, null);
        String password = sp.getString(ProfileFragment.PREF_PASSWORD, null);
        JacksonListRequest<Company> request = JacksonListRequest.<Company>method(Request.Method.GET)
                .url(App.BASE_URL + URL_MY_FAVORIT_COMPANIES)
                .listener(new Response.Listener<List<Company>>() {
                    @Override
                    public void onResponse(List<Company> companies) {
                        FavoriteCompanyAdapter adapter = new FavoriteCompanyAdapter(getActivity(), companies, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int position = mRecyclerView.getChildPosition(v);
                                Adapter adapter = mRecyclerView.getAdapter();
                                if (adapter instanceof FavoriteCompanyAdapter) {
                                    Bundle b = new Bundle(1);
                                    b.putLong("id", ((FavoriteCompanyAdapter) adapter).getList().get(position).getId());
                                    CompanyFragment fragment = new CompanyFragment();
                                    fragment.setArguments(b);
                                    getFragmentManager().beginTransaction()
                                            .replace(R.id.container, fragment, "Company Profile")
                                            .addToBackStack("Company Profile")
                                            .commit();
                                }
                            }
                        });
                        mRecyclerView.setAdapter(adapter);
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
        Connection.getInstance(getActivity()).addToRequestQueue(request);
    }

    private void fetchFavoriteOpportunities() {
        crossfadeInverse(mContentView, mLoadingView);
        SharedPreferences sp = getSharedPreferences();
        String username = sp.getString(ProfileFragment.PREF_EMAIL, null);
        String password = sp.getString(ProfileFragment.PREF_PASSWORD, null);
        JacksonListRequest<Opportunity> request = JacksonListRequest.<Opportunity>method(Request.Method.GET)
                .url(App.BASE_URL + URL_MY_FAVORIT_OPPORTUNITIES)
                .listener(new Response.Listener<List<Opportunity>>() {
                    @Override
                    public void onResponse(List<Opportunity> opportunities) {
                        if (mContentView == null) return;

                        OpportunityAdapter adapter = new OpportunityAdapter(getActivity(), opportunities);
                        mRecyclerView.setAdapter(adapter);
                        crossfade(mContentView, mLoadingView);
                    }
                })
                .errorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (mContentView == null) return;
                        crossfadeInverse(mContentView, mLoadingView);
                    }
                })
                .clazz(Opportunity.class)
                .username(username)
                .password(password)
                .build();
        Connection.getInstance(getActivity()).addToRequestQueue(request);
    }
}
