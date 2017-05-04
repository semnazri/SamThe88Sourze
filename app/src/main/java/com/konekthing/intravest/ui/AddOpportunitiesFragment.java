package com.konekthing.intravest.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.konekthing.intravest.R;
import com.konekthing.intravest.model.SubIndustries;
import com.konekthing.intravest.ui.adapters.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 5/3/17.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class AddOpportunitiesFragment extends BaseFragment {

    private View view;
    private Spinner spiner_value,spiner_region,spiner_status;
    private SpinnerAdapter sp_val,sp_reg,sp_stat;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_opportunities,container , false);
        spiner_value = (Spinner) view.findViewById(R.id.spinner_prjct_val);
        spiner_region = (Spinner) view.findViewById(R.id.spinner_region);
        spiner_status = (Spinner) view.findViewById(R.id.spinner_status);


        sp_val = new SpinnerAdapter(getActivity(),getVal());
        sp_reg = new SpinnerAdapter(getActivity(),getReg());
        sp_stat = new SpinnerAdapter(getActivity(),getStat());

        spiner_value.setAdapter(sp_val);
        spiner_region.setAdapter(sp_reg);
        spiner_status.setAdapter(sp_stat);


        return view;
    }

    private List<SubIndustries> getStat() {
        List<SubIndustries> status = new ArrayList<SubIndustries>();
        status.add(new SubIndustries("1","Investment"));
        status.add(new SubIndustries("2","Investment"));
        status.add(new SubIndustries("3","Invesment"));
        return status;
    }

    private List<SubIndustries> getReg() {
        List<SubIndustries> region = new ArrayList<SubIndustries>();
        region.add(new SubIndustries("1","Indonesia"));
        region.add(new SubIndustries("2","Australia"));
        region.add(new SubIndustries("3","Singapore"));
        region.add(new SubIndustries("4","Malaysia"));
        region.add(new SubIndustries("5","Japan"));
        return region;
    }

    private List<SubIndustries> getVal() {
        List<SubIndustries> value = new ArrayList<SubIndustries>();
        value.add(new SubIndustries("1","IDR"));
        value.add(new SubIndustries("2","USD"));
        value.add(new SubIndustries("3","YEN"));
        return value;
    }
}
