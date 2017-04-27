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
import com.konekthing.intravest.model.Opportunity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by renjaya on 4/8/15.
 */
public class OpportunityAdapter extends RecyclerView.Adapter<OpportunityAdapter.ViewHolder> {

    private Context mContext;
    private List<Opportunity> mList;
    private SimpleDateFormat dateFormat_yyyy_MM_dd;
    private SimpleDateFormat dateFormat_dd_MMM_yyyy;

    public OpportunityAdapter(Context context, List<Opportunity> list) {
        mContext = context;
        if (list == null) {
            mList = new ArrayList<>();
        } else {
            mList = list;
        }
        this.dateFormat_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
        this.dateFormat_dd_MMM_yyyy = new SimpleDateFormat("dd MMM yyyy");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_opportunity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Opportunity o = mList.get(position);
        holder.name.setText(o.getProjectName());
        holder.value.setText(o.getCurrency() + " " +o.getProjectValue());
        String description = o.getProjectDescription();
        holder.desc.setText(description.length() > 100 ? description.substring(0, 100) : description);
        String dateOpen = "";
        String dateClose = "";
        try {
            dateOpen = this.dateFormat_dd_MMM_yyyy.format(this.dateFormat_yyyy_MM_dd.parse(o.getDateOpen()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            dateClose = this.dateFormat_dd_MMM_yyyy.format(this.dateFormat_yyyy_MM_dd.parse(o.getDateClose()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.date.setText(dateOpen + " - " + dateClose);
        holder.status.setText(o.getStatus());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public List<Opportunity> getList() {
        return mList;
    }

    public void setList(List<Opportunity> list) {
        mList = list;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView value;
        TextView desc;
        TextView date;
        TextView status;
        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.text_name);
            value = (TextView) view.findViewById(R.id.text_value);
            desc = (TextView) view.findViewById(R.id.text_desc);
            date = (TextView) view.findViewById(R.id.text_date);
            status = (TextView) view.findViewById(R.id.text_status);
        }
    }
}
