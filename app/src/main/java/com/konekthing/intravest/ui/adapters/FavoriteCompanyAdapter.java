/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.konekthing.intravest.R;
import com.konekthing.intravest.model.Company;
import com.konekthing.intravest.ui.CompanyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renjaya on 4/8/15.
 */
public class FavoriteCompanyAdapter extends RecyclerView.Adapter<FavoriteCompanyAdapter.ViewHolder> {

    private Context mContext;
    private List<Company> mList;
    private View.OnClickListener mClickListener;

    public FavoriteCompanyAdapter(Context context, List<Company> list, View.OnClickListener clickListener) {
        mContext = context;
        mClickListener = clickListener;
        if (list == null) {
            mList = new ArrayList<>();
        } else {
            mList = list;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_company, parent, false);
        view.setOnClickListener(mClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Company s = mList.get(position);
        holder.name.setText(s.getRegisteredCompany());
        holder.location.setText(s.getCity() + ", " + s.getProvince());
        holder.contact.setText("Contact:\n" + s.getPhone() + "\n" + s.getEmail() + "\n" + s.getWebsite());
        holder.industry.setText("Industry:\n" + s.getIndustryLvl1() + "\n" + s.getIndustryLvl2() +
                (s.getIndustryLvl3() == null || s.getIndustryLvl3().length() == 0 ? "" : "\n" + s.getIndustryLvl3()) +
                (s.getIndustryLvl4() == null || s.getIndustryLvl4().length() == 0 ? "" : "\n" + s.getIndustryLvl4()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public List<Company> getList() {
        return mList;
    }

    public void setList(List<Company> list) {
        mList = list;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView location;
        TextView industry;
        TextView contact;
        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.text_name);
            location = (TextView) view.findViewById(R.id.text_location);
            industry = (TextView) view.findViewById(R.id.text_industry);
            contact = (TextView) view.findViewById(R.id.text_contact);
        }
    }
}
