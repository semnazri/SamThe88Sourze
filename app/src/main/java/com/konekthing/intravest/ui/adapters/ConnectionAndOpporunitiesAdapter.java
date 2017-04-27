/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.konekthing.intravest.R;
import com.konekthing.intravest.model.Company;
import com.konekthing.intravest.model.Opportunity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renjaya on 4/7/15.
 */
public class ConnectionAndOpporunitiesAdapter extends RecyclerView.Adapter<ConnectionAndOpporunitiesAdapter.ViewHolder> {

    private Context mContext;
    private List<?> mList;

    public ConnectionAndOpporunitiesAdapter(Context context, List<?> list) {
        mContext = context;
        if (list == null) {
            mList = new ArrayList<>();
        } else {
            mList = list;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_connections_and_opportunities, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (mList.get(position) instanceof Company) holder.text.setText(((Company)mList.get(position)).getRegisteredCompany());
        else if (mList.get(position) instanceof Opportunity) holder.text.setText(((Opportunity)mList.get(position)).getProjectName());
        else holder.text.setText("-");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public List<?> getList() {
        return mList;
    }

    public void setList(List<?> list) {
        mList = list;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        public ViewHolder(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.text);
        }
    }
}
