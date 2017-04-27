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
import com.konekthing.intravest.model.Opportunity;
import com.konekthing.intravest.ui.widget.NotifyingScrollView;

/**
 * A simple {@link com.konekthing.intravest.ui.BaseFragment} subclass.
 */
public class DetailOpportunityFragment extends BaseFragment {

    private View mHeaderView;
    private NotifyingScrollView mScrollView;
    private TextView mTextName, mTextCompany;
    private EditText mInputStatus, mInputDesc, mInputValue, mInputDate, mInputRegional;
    private Opportunity mOpportunity;

    public DetailOpportunityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mOpportunity = args.getParcelable(OpportunityFragment.PARCEL_OPPORTUNITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_opportunity, container, false);
        mHeaderView = view.findViewById(R.id.header_view);
        mScrollView = (NotifyingScrollView) view.findViewById(R.id.scroll_view);
        mTextName = (TextView) view.findViewById(R.id.text_name);
        mTextCompany = (TextView) view.findViewById(R.id.text_company);
        mInputStatus = (EditText) view.findViewById(R.id.input_status);
        mInputDesc = (EditText) view.findViewById(R.id.input_desc);
        mInputValue = (EditText) view.findViewById(R.id.input_value);
        mInputDate = (EditText) view.findViewById(R.id.input_date);
        mInputRegional = (EditText) view.findViewById(R.id.input_regional);
        if (mOpportunity != null) {
            mTextName.setText(mOpportunity.getProjectName());
            mTextCompany.setText(mOpportunity.getOwner());
            mInputStatus.setText(mOpportunity.getStatus());
            mInputDesc.setText(mOpportunity.getProjectDescription());
            mInputValue.setText(mOpportunity.getCurrency() + " " + mOpportunity.getProjectValue());
            mInputDate.setText(mOpportunity.getDateOpen() + " to " + mOpportunity.getDateClose());
            mInputRegional.setText(mOpportunity.getRegional());
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setDrawerToggleEnabled(false);
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
        mTextCompany = mTextName = null;
        mInputStatus = mInputDesc = mInputValue = mInputDate = mInputRegional = null;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        setDrawerToggleEnabled(true);
        super.onDestroy();
    }
}
