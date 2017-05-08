package com.konekthing.intravest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.konekthing.intravest.R;
import com.konekthing.intravest.model.SubIndustries;
import com.konekthing.intravest.ui.adapters.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 2/20/17.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class RegisterActivity_3 extends Fragment {
    private Spinner spiner_industri1,spiner_industri2,spiner_industri3;
    private Button btn_explore;

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register_3,container,false);
        spiner_industri1 = (Spinner) view.findViewById(R.id.sub_industry1);
        spiner_industri2 = (Spinner) view.findViewById(R.id.sub_industry2);
        spiner_industri3 = (Spinner) view.findViewById(R.id.sub_industry3);
        btn_explore = (Button) view.findViewById(R.id.btn_next_regis3);

        SpinnerAdapter sa1 =  new SpinnerAdapter(getActivity(), getAllIndustries_1());
        SpinnerAdapter sa2 =  new SpinnerAdapter(getActivity(), getAllIndustries_2());
        SpinnerAdapter sa3 =  new SpinnerAdapter(getActivity(), getAllIndustries_3());

        spiner_industri1.setAdapter(sa1);
        spiner_industri2.setAdapter(sa2);
        spiner_industri3.setAdapter(sa3);

        RegisterActivity.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        RegisterActivity.tv.setText("Sub Industry");

        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }

    private List<SubIndustries> getAllIndustries_3() {

        List<SubIndustries> allIndustries = new ArrayList<SubIndustries>();
        allIndustries.add(new SubIndustries("1","Sub Industries 3"));
        allIndustries.add(new SubIndustries("2","Sub Industries 3"));
        allIndustries.add(new SubIndustries("3","Sub Industries 3"));
        allIndustries.add(new SubIndustries("4","Sub Industries 3"));
        allIndustries.add(new SubIndustries("5","Sub Industries 3"));
        allIndustries.add(new SubIndustries("6","Sub Industries 3"));


        return allIndustries;
    }

    private List<SubIndustries> getAllIndustries_1() {

        List<SubIndustries> allIndustries = new ArrayList<SubIndustries>();
        allIndustries.add(new SubIndustries("1","Sub Industries 1"));
        allIndustries.add(new SubIndustries("2","Sub Industries 1"));
        allIndustries.add(new SubIndustries("3","Sub Industries 1"));
        allIndustries.add(new SubIndustries("4","Sub Industries 1"));
        allIndustries.add(new SubIndustries("5","Sub Industries 1"));
        allIndustries.add(new SubIndustries("6","Sub Industries 1"));


        return allIndustries;
    }

    private List<SubIndustries> getAllIndustries_2() {

        List<SubIndustries> allIndustries = new ArrayList<SubIndustries>();
        allIndustries.add(new SubIndustries("1","Sub Industries 2"));
        allIndustries.add(new SubIndustries("2","Sub Industries 2"));
        allIndustries.add(new SubIndustries("3","Sub Industries 2"));
        allIndustries.add(new SubIndustries("4","Sub Industries 2"));
        allIndustries.add(new SubIndustries("5","Sub Industries 2"));
        allIndustries.add(new SubIndustries("6","Sub Industries 2"));


        return allIndustries;
    }
}
