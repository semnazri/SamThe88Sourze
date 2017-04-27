/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.konekthing.intravest.App;
import com.konekthing.intravest.R;
import com.konekthing.intravest.model.Company;
import com.konekthing.intravest.model.Gallery;
import com.konekthing.intravest.network.Connection;
import com.konekthing.intravest.network.JacksonRequest;
import com.konekthing.intravest.ui.adapters.ConnectionAndOpporunitiesAdapter;
import com.konekthing.intravest.ui.adapters.GalleryAdapter;
import com.konekthing.intravest.ui.widget.DividerItemDecoration;
import com.konekthing.intravest.ui.widget.NotifyingScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link com.konekthing.intravest.ui.BaseFragment} subclass.
 */
public class CompanyFragment extends BaseFragment {

    static final String URL_COMPANY = "/company";
    static final String URL_LOVE_COMPANY = "/love_supplier";

    private View mHeaderView, mLoadingView;
    private NotifyingScrollView mScrollView;
    private TextView mTextName;
    private EditText mInputRepPhone,mInputRepPosition, mInputRepName, mInputEstimatedTotalEmployee,mInputEstimatedYearTurnover,mInputYearEstablished,mInputProductService,mInputIndustry,mInputHistory,mInputCapabilities,mInputWebsite,mInputEmail,mInputPhone, mInputAddress ,mInputRepEmail;
    private Button mBtnConnections, mBtnOpportunities;
    private RecyclerView mRecyclerViewGallery, mRecyclerViewConnections, mRecyclerViewOpportunities;
    private ConnectionAndOpporunitiesAdapter mAdapterConnections, mAdapterOpportunities;
    private GalleryAdapter mAdapterGallery;
    private Company mCompany;
    private Menu mMenu;

    public CompanyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapterConnections = new ConnectionAndOpporunitiesAdapter(getActivity(), null);
        mAdapterOpportunities = new ConnectionAndOpporunitiesAdapter(getActivity(), null);
        mAdapterGallery = new GalleryAdapter(getActivity(), null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company, container, false);
        mHeaderView = view.findViewById(R.id.header_view);
        mScrollView = (NotifyingScrollView) view.findViewById(R.id.scroll_view);
        mLoadingView = view.findViewById(R.id.loading_view);
        mTextName = (TextView) view.findViewById(R.id.text_name);
        mInputEmail = (EditText) view.findViewById(R.id.input_email);
        mInputPhone = (EditText) view.findViewById(R.id.input_phone);
        mInputAddress = (EditText) view.findViewById(R.id.input_address);
        mInputIndustry = (EditText) view.findViewById(R.id.input_industry);
        mInputProductService = (EditText) view.findViewById(R.id.input_product_service);
        mInputYearEstablished = (EditText) view.findViewById(R.id.input_year_establised);
        mInputEstimatedYearTurnover = (EditText) view.findViewById(R.id.input_estimated_year_turnover);
        mInputEstimatedTotalEmployee = (EditText) view.findViewById(R.id.input_estimated_total_employee);
        mInputWebsite = (EditText) view.findViewById(R.id.input_website);
        mInputCapabilities = (EditText) view.findViewById(R.id.input_capabilities);
        mInputHistory = (EditText) view.findViewById(R.id.input_history);
        mInputRepName = (EditText) view.findViewById(R.id.input_name_rep);
        mInputRepPosition = (EditText) view.findViewById(R.id.input_position_rep);
        mInputRepEmail = (EditText) view.findViewById(R.id.input_email_rep);
        mInputRepPhone = (EditText) view.findViewById(R.id.input_phone_rep);
        mBtnConnections = (Button) view.findViewById(R.id.btn_connections);
        mBtnOpportunities = (Button) view.findViewById(R.id.btn_opportunities);
        mRecyclerViewGallery = (RecyclerView) view.findViewById(R.id.recycler_view_gallery);
        mRecyclerViewConnections = (RecyclerView) view.findViewById(R.id.recycler_view_connection);
        mRecyclerViewOpportunities = (RecyclerView) view.findViewById(R.id.recycler_view_opportunity);

        mBtnConnections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRecyclerViewConnections.getAlpha() == 1) {
                    mRecyclerViewConnections.animate().alpha(0).setDuration(1000).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mRecyclerViewConnections.setVisibility(View.GONE);
                        }
                    });
                    mBtnConnections.setText("Show Connections");
                } else if (mRecyclerViewConnections.getAlpha() == 0) {
                    mRecyclerViewConnections.setVisibility(View.VISIBLE);
                    mRecyclerViewConnections.animate().alpha(1).setDuration(1000).setListener(null);
                    mBtnConnections.setText("Hide Connections");
                }
                if (mRecyclerViewOpportunities.getAlpha() == 1)
                    mRecyclerViewOpportunities.animate().alpha(0).setDuration(1000);
            }
        });
        mBtnOpportunities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRecyclerViewOpportunities.getAlpha() == 1) {
                    mRecyclerViewOpportunities.animate().alpha(0).setDuration(1000).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mRecyclerViewOpportunities.setVisibility(View.GONE);
                        }
                    });
                    mBtnOpportunities.setText("Show Opportunities Provided");
                } else if (mRecyclerViewOpportunities.getAlpha() == 0) {
                    mRecyclerViewOpportunities.setVisibility(View.VISIBLE);
                    mRecyclerViewOpportunities.animate().alpha(1).setDuration(1000).setListener(null);
                    mBtnOpportunities.setText("Hide Opportunities Provided");
                }
                if (mRecyclerViewConnections.getAlpha() == 1)
                    mRecyclerViewConnections.animate().alpha(0).setDuration(1000);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        toolbarScrollTrick(mHeaderView, mScrollView);
        mRecyclerViewGallery.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewGallery.setAdapter(mAdapterGallery);
        mRecyclerViewGallery.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewGallery.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
        mRecyclerViewConnections.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewConnections.setAdapter(mAdapterConnections);
        mRecyclerViewConnections.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewConnections.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerViewOpportunities.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewOpportunities.setAdapter(mAdapterOpportunities);
        mRecyclerViewOpportunities.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewOpportunities.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        getCompany();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mMenu = menu;
        inflater.inflate(R.menu.menu_company, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_love) {
            loveCompany();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        mHeaderView = mLoadingView = null;
        mScrollView = null;
        mCompany = null;
        mMenu = null;
        mTextName = null;
        mInputIndustry =mInputEstimatedTotalEmployee = mInputEstimatedYearTurnover =mInputProductService = mInputHistory = mInputEmail  = mInputCapabilities = mInputWebsite = mInputPhone =mInputAddress = null;

                 mInputYearEstablished =  null;
        super.onDestroyView();
    }

    private void loveCompany() {
        mMenu.getItem(0).setVisible(false);
        Company c = new Company();
        c.setId(mCompany.getId());

        SharedPreferences sp = getSharedPreferences();
        JacksonRequest<Company> request = JacksonRequest.<Company>method(Request.Method.POST)
                .url(App.BASE_URL + URL_LOVE_COMPANY)
                .listener(new Response.Listener<Company>() {
                    @Override
                    public void onResponse(Company company) {
                        if (mScrollView == null) return;

                        mCompany.setLoved(company.getLoved());
                        if (company.getLoved() == 1) {
                            mMenu.getItem(0).setTitle("Unlove It");
                            mMenu.getItem(0).setIcon(R.drawable.ic_love_1);
                        } else {
                            mMenu.getItem(0).setTitle("Love It");
                            mMenu.getItem(0).setIcon(R.drawable.ic_love_2);
                        }
                        mMenu.getItem(0).setVisible(true);
                    }
                })
                .errorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (mMenu != null) mMenu.getItem(0).setVisible(true);
                    }
                })
                .clazz(Company.class)
                .username(sp.getString(ProfileFragment.PREF_EMAIL, null))
                .password(sp.getString(ProfileFragment.PREF_PASSWORD, null))
                .body(c)
                .build();
        request.setTag(getTag());
        Connection.getInstance(getActivity()).addToRequestQueue(request);
    }

    private void getCompany() {
        long longId = getArguments() == null ? 0 : getArguments().getLong("id", 0);
        String id = longId == 0 ? "" : longId + "";
        SharedPreferences sp = getSharedPreferences();
        JacksonRequest<Company> request = JacksonRequest.<Company>method(Request.Method.GET)
                .url(App.BASE_URL + URL_COMPANY + "/" + id)
                .listener(new Response.Listener<Company>() {
                    @Override
                    public void onResponse(Company company) {
                        if (mScrollView == null) return;

                        mCompany = company;
                        mLoadingView.setVisibility(View.GONE);
                        mTextName.setText(company.getRegisteredCompany());
                        mInputAddress.setText(company.getAddress() + ", " + company.getCity() + ", " +
                                company.getProvince() + " " + company.getPostalCode());
                        mInputIndustry.setText(company.getIndustryLvl1() + "\n" + company.getIndustryLvl2() +
                                (company.getIndustryLvl3() == null || company.getIndustryLvl3().length() == 0 ? "" : "\n" + company.getIndustryLvl3()) +
                                (company.getIndustryLvl4() == null || company.getIndustryLvl4().length() == 0 ? "" : "\n" + company.getIndustryLvl4()));
                        mInputProductService.setText(company.getProductService());
                        mInputYearEstablished.setText(company.getYearEstablished() + "");
                        mInputEstimatedYearTurnover.setText(company.getEstimatedYearlyTurnover());
                        mInputEstimatedTotalEmployee.setText(company.getEstimatedTotalEmployee());
                        mInputEmail.setText(company.getEmail());
                        mInputPhone.setText(company.getPhone());
                        mInputWebsite.setText(company.getWebsite());
                        mInputCapabilities.setText(company.getCapabilities());
                        mInputHistory.setText(company.getHistory());
                        mInputRepName.setText(company.getSalutation() + " " + company.getFirstname() + " " + company.getLastname());
                        mInputRepPosition.setText(company.getPosition());
                        mInputRepEmail.setText(company.getUserEmail());
                        mInputRepPhone.setText(company.getUserPhone());
                        mAdapterConnections.setList(company.getConnections());
                        mAdapterOpportunities.setList(company.getOpportunities());
                        List<Gallery> gallery = new ArrayList<Gallery>(2 + company.getProductPhotos().size());
                        gallery.add(new Gallery().setImage(company.getProfilePicture()).setTitle("Profile Picture"));
                        gallery.add(new Gallery().setImage(company.getOfficePhoto()).setTitle("User Photo"));
                        for (int i = 0; i < company.getProductPhotos().size(); i++) {
                            Gallery g = company.getProductPhotos().get(i);
                            g.setTitle("Product " + (i + 1));
                            gallery.add(g);
                        }
                        mAdapterGallery.setList(gallery);

                        if (company.getLoved() == 1) {
                            mMenu.getItem(0).setTitle("Unlove It");
                            mMenu.getItem(0).setIcon(R.drawable.ic_love_1);
                        } else {
                            mMenu.getItem(0).setTitle("Love It");
                            mMenu.getItem(0).setIcon(R.drawable.ic_love_2);
                        }
                    }
                })
                .errorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
                .clazz(Company.class)
                .username(sp.getString(ProfileFragment.PREF_EMAIL, null))
                .password(sp.getString(ProfileFragment.PREF_PASSWORD, null))
                .build();
        request.setTag(getTag());
        Connection.getInstance(getActivity()).addToRequestQueue(request);
    }
}
