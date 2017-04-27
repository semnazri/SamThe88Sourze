/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.konekthing.intravest.App;
import com.konekthing.intravest.R;
import com.konekthing.intravest.model.Company;
import com.konekthing.intravest.network.Connection;
import com.konekthing.intravest.network.JacksonListRequest;
import com.konekthing.intravest.network.JacksonRequest;
import com.konekthing.intravest.ui.adapters.FavoriteCompanyAdapter;

import java.util.List;

/**
 * A simple {@link com.konekthing.intravest.ui.BaseFragment} subclass.
 */
public class ListAsSupplierFragment extends BaseFragment {

    static final String URL_LIST_AS_SUPPLIER = "/list_as_supplier";
    private View mContentView, mLoadingView;
    private Button mBtnSave;
    private View mContainerSiup, mContainerTdp, mContainerNpwp;
    private TextView mTextLegalDocsNotif, mTextSiup, mTextTdp, mTextNpwp;
    private CheckBox mCbSupplier, mCbInvestor, mCbAgreement;

    public ListAsSupplierFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_as_supplier_new, container, false);
        mContentView = view.findViewById(R.id.content_view);
        mLoadingView = view.findViewById(R.id.loading_view);
        mTextLegalDocsNotif = (TextView) view.findViewById(R.id.text_legal_docs_notif);
        mContainerSiup = view.findViewById(R.id.container_siup);
        mContainerTdp = view.findViewById(R.id.container_tdp);
        mContainerNpwp = view.findViewById(R.id.container_npwp);
        mTextSiup = (TextView) view.findViewById(R.id.text_siup);
        mTextTdp = (TextView) view.findViewById(R.id.text_tdp);
        mTextNpwp = (TextView) view.findViewById(R.id.text_npwp);
        mCbSupplier = (CheckBox) view.findViewById(R.id.cb_supplier);
        mCbInvestor = (CheckBox) view.findViewById(R.id.cb_investor);
        mCbAgreement = (CheckBox) view.findViewById(R.id.cb_agreement);
        mBtnSave = (Button) view.findViewById(R.id.btn_save);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        changeToolbarAlpha(255);
        fetchListAsSupplier();
    }

    private void fetchListAsSupplier() {
        crossfadeInverse(mContentView, mLoadingView);
        SharedPreferences sp = getSharedPreferences();
        String username = sp.getString(ProfileFragment.PREF_EMAIL, null);
        String password = sp.getString(ProfileFragment.PREF_PASSWORD, null);
        JacksonRequest<Company> request = JacksonRequest.<Company>method(Request.Method.GET)
                .url(App.BASE_URL + URL_LIST_AS_SUPPLIER)
                .listener(new Response.Listener<Company>() {
                    @Override
                    public void onResponse(Company company) {
                        crossfade(mContentView, mLoadingView);

                        boolean docsCompleted = true;
                        if (company.getBusiness_license() == null || company.getBusiness_license().length() == 0) {
                            mTextSiup.setText("No Document");
                            mContainerSiup.setOnClickListener(null);
                            docsCompleted = false;
                        } else {
                            mTextSiup.setText("Document is Available. Tap to show.");
                            mContainerSiup.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                        }

                        if (company.getCompany_register() == null || company.getCompany_register().length() == 0) {
                            mTextTdp.setText("No Document");
                            mContainerTdp.setOnClickListener(null);
                            docsCompleted = false;
                        } else {
                            mTextTdp.setText("Document is Available. Tap to show.");
                            mContainerTdp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                        }

                        if (company.getTax_number() == null || company.getTax_number().length() == 0) {
                            mTextNpwp.setText("No Document");
                            mContainerNpwp.setOnClickListener(null);
                            docsCompleted = false;
                        } else {
                            mTextNpwp.setText("Document is Available. Tap to show.");
                            mContainerNpwp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                        }

                        mCbSupplier.setChecked(company.getIs_supplier() == 1 ? true : false);
                        mCbInvestor.setChecked(company.getIs_investor() == 1 ? true : false);

                        if (docsCompleted) {
                            mTextLegalDocsNotif.setText("Your company legal documents are completed!");
                            mTextLegalDocsNotif.setTextColor(Color.parseColor("#d8ba43"));
                        } else {
                            mTextLegalDocsNotif.setText("   *Please complete your\\ncompany legal documents");
                            mTextLegalDocsNotif.setTextColor(Color.RED);
                        }
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
}
