package com.konekthing.intravest.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konekthing.intravest.R;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 5/3/17.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class AddOpportunitiesFragment extends BaseFragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_opportunities,container , false);


        return view;
    }
}
