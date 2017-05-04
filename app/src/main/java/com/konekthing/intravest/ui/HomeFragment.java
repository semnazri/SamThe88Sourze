/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.konekthing.intravest.App;
import com.konekthing.intravest.R;
import com.konekthing.intravest.model.Feed;
import com.konekthing.intravest.network.Connection;
import com.konekthing.intravest.network.JacksonListRequest;
import com.konekthing.intravest.ui.adapters.FeedAdapter;
import com.konekthing.intravest.ui.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link com.konekthing.intravest.ui.BaseFragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    static final String FEED_URL = "/feeds";
    static final String PARCEL_LIST_FEED = "parcel_list_feed";
    static final String STRING_SEARCH_QUERY = "string_search_query";

    private View mContentView, mLoadingView, mSearchBar, mShareBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private EditText mInputSearch;
    private RadioGroup mSelectSearch;
    private Button mBtnSearch;
    private FloatingActionButton mBtnAdd;
    private FeedAdapter mAdapter;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new FeedAdapter(getActivity(), null);
        if (savedInstanceState == null) {
            SharedPreferences sp = getSharedPreferences();
            getFeeds(getActivity(), sp.getString(ProfileFragment.PREF_EMAIL, null),
                    sp.getString(ProfileFragment.PREF_PASSWORD, null));
        } else {
            List<Feed> list = savedInstanceState.getParcelableArrayList(PARCEL_LIST_FEED);
            mAdapter.setList(list);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_Home);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View view = localInflater.inflate(R.layout.fragment_home_feeds, container, false);
        mContentView = view.findViewById(R.id.content_view);
        mLoadingView = view.findViewById(R.id.loading_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mSearchBar = view.findViewById(R.id.search_bar);
        mShareBar = view.findViewById(R.id.share_bar);
        mInputSearch = (EditText) view.findViewById(R.id.input_search);
        mSelectSearch = (RadioGroup) view.findViewById(R.id.select_search);
        mBtnSearch = (Button) view.findViewById(R.id.btn_search);
        mBtnAdd = (FloatingActionButton) view.findViewById(R.id.btn_add);
        mLoadingView.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSelectSearch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.search_opportunities) {
                    mInputSearch.setHint("Search opportunities");
                } else {
                    mInputSearch.setHint("Search suppliers");
                }
            }
        });
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectSearch.getCheckedRadioButtonId() == R.id.search_opportunities) {
                    startActivity(new Intent(getActivity(), OpportunityActivity.class)
                            .putExtra(STRING_SEARCH_QUERY, mInputSearch.getText().toString().trim()));
                } else {
                    startActivity(new Intent(getActivity(), SupplierActivity.class)
                            .putExtra(STRING_SEARCH_QUERY, mInputSearch.getText().toString().trim()));
                }
            }
        });
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                handleAdd();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container, new AddOpportunitiesFragment(), "darimenu").addToBackStack("menu");
                fragmentTransaction.commit();
            }
        });
        if (mAdapter.getItemCount() == 0) {
            crossfadeInverse(mContentView, mLoadingView);
        }
        mSwipeRefreshLayout.setColorSchemeResources(R.color.gold_dark, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SharedPreferences sp = getSharedPreferences();
                getFeeds(getActivity(), sp.getString(ProfileFragment.PREF_EMAIL, null),
                        sp.getString(ProfileFragment.PREF_PASSWORD, null));
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        changeToolbarAlpha(255);
        setHasOptionsMenu(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PARCEL_LIST_FEED, (ArrayList<? extends Parcelable>) mAdapter.getList());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (mSearchBar != null) {
            int visibility = mSearchBar.getVisibility();
            menu.findItem(R.id.action_search).setIcon(visibility == View.VISIBLE ? R.drawable.ic_silang : android.R.drawable.ic_search_category_default);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            handleSearch(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        mContentView = mLoadingView = mSearchBar = mShareBar = null;
        mSwipeRefreshLayout = null;
        mRecyclerView = null;
        mInputSearch = null;
        mSelectSearch = null;
        mBtnSearch = null;
        super.onDestroyView();
    }

    private void handleSearch(MenuItem item) {
        int visibility = mSearchBar.getVisibility();
        mSearchBar.setVisibility(visibility == View.VISIBLE ? View.GONE : View.VISIBLE);
        item.setIcon(visibility == View.VISIBLE ? android.R.drawable.ic_search_category_default : R.drawable.ic_silang);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mContentView.getLayoutParams();
        final int toolbarHeight = getResources().getDimensionPixelSize(
                R.dimen.abc_action_bar_default_height_material);
        int margin = dipsToPix(visibility == View.VISIBLE ? 0f : 240f) - toolbarHeight;
        params.setMargins(0, margin > 0 ? margin : 0, 0, 0);
        mContentView.requestLayout();
    }

    private void handleAdd() {
        int visibility = mShareBar.getVisibility();
        mShareBar.setVisibility(visibility == View.VISIBLE ? View.GONE : View.VISIBLE);
        mBtnAdd.setIcon(visibility == View.VISIBLE ? R.drawable.ic_plus : R.drawable.ic_silang);
    }

    private void getFeeds(Context context, String username, String password) {
        JacksonListRequest<Feed> request = JacksonListRequest.<Feed>method(Request.Method.GET)
                .url(App.BASE_URL + FEED_URL)
                .listener(new Response.Listener<List<Feed>>() {
                    @Override
                    public void onResponse(List<Feed> response) {
                        if (mAdapter != null) mAdapter.setList(response);
                        if (mContentView != null && mLoadingView != null) crossfade(mContentView, mLoadingView);
                        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                })
                .errorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (mContentView != null && mLoadingView != null) crossfadeInverse(mContentView, mLoadingView);
                        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                })
                .clazz(Feed.class)
                .username(username)
                .password(password)
                .build();
        request.setTag(getTag());
        Connection.getInstance(context).addToRequestQueue(request);
    }
}
