/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.konekthing.intravest.R;
import com.konekthing.intravest.model.Company;
import com.konekthing.intravest.ui.widget.NotifyingScrollView;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class DetailSupplierFragment extends BaseFragment {

    private View mHeaderView;
    private NotifyingScrollView mScrollView;
    private TextView mTextName, mTextYearSince;
    private EditText mInputDesc, mInputProduct, mInputService;
    private Company mCompany;

    public DetailSupplierFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCompany = args.getParcelable(SupplierSearchResultFragment.PARCEL_SUPPLIER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_supplier, container, false);
        mHeaderView = view.findViewById(R.id.header_view);
        mScrollView = (NotifyingScrollView) view.findViewById(R.id.scroll_view);
        mTextName = (TextView) view.findViewById(R.id.text_name);
        mTextYearSince = (TextView) view.findViewById(R.id.text_year_since);
        mInputDesc = (EditText) view.findViewById(R.id.input_desc);
        mInputProduct = (EditText) view.findViewById(R.id.input_product);
        mInputService = (EditText) view.findViewById(R.id.input_service);
        if (mCompany != null) {
            mTextName.setText(mCompany.getCompany());
            mTextYearSince.setText("Since " + mCompany.getYearEstablished());
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        toolbarScrollTrick(mHeaderView, mScrollView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getFragmentManager().popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        mHeaderView = null;
        mScrollView = null;
        mTextYearSince = mTextName = null;
        mInputDesc = mInputProduct = mInputService = null;
        super.onDestroyView();
    }
}
