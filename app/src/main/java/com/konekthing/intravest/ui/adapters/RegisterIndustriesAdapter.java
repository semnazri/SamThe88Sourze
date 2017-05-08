package com.konekthing.intravest.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.konekthing.intravest.R;
import com.konekthing.intravest.ViewHolder.IndustriesViewHolder;
import com.konekthing.intravest.model.Industries;
import com.konekthing.intravest.ui.RegisterActivity;
import com.konekthing.intravest.ui.RegisterActivity_3;

import java.util.List;

import static com.konekthing.intravest.ui.RegisterActivity.activity;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 2/20/17.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class RegisterIndustriesAdapter extends RecyclerView.Adapter<IndustriesViewHolder> {

    private Context mContext;
    private List<Industries> mValues;

    public RegisterIndustriesAdapter(Context context, List<Industries>items){
        mContext = context;
        mValues = items;
    }
    @Override
    public IndustriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_industry,parent,false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                Fragment fragment = new RegisterActivity_3();
                fragment.setArguments(bundle);

                FragmentManager fm = ((RegisterActivity) mContext).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container_bro, fragment, "berita").addToBackStack("listhalaman").commit();
            }
        });

        return new IndustriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IndustriesViewHolder holder, int position) {

        holder.industries_name.setText(mValues.get(position).getIndustries_name());
        holder.img_industries.setImageResource(mValues.get(position).getImg_Industries());


//        Glide.with(mContext).load(mValues.get(position).getImg_Industries()).into(holder.img_industries);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
