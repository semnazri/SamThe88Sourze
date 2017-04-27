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
import com.konekthing.intravest.model.Feed;

import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renjaya on 4/8/15.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private Context mContext;
    private List<Feed> mList;
    private DateTimeFormatter mDateTimeFormatter;

    public FeedAdapter(Context context, List<Feed> list) {
        mContext = context;
        if (list == null) {
            mList = new ArrayList<>();
        } else {
            mList = list;
        }
        mDateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_feed_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Feed f = mList.get(position);
        holder.name.setText(f.getSalutation() + " " + f.getFirstname() + " " + f.getLastname());
        holder.company.setText("(" + f.getCompany() + ")");
        holder.content.setText(f.getContent());
        holder.name.setSelected(true);
        holder.company.setSelected(true);
        LocalDate now = new LocalDate();
        Period period = new Period(LocalDate.parse(f.getTime(), mDateTimeFormatter), now);
        holder.time.setText(formatTime(period));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public List<Feed> getList() {
        return mList;
    }

    public void setList(List<Feed> list) {
        mList = list;
        notifyDataSetChanged();
    }

    private String formatTime(Period period) {
        int years = period.getYears();
        if (years > 0) {
            return years + " years ago";
        }
        int months = period.getMonths();
        if (months > 0) {
            return months + " months ago";
        }
        int weeks = period.getWeeks();
        if (weeks > 0) {
            return weeks + " weeks ago";
        }
        int days = period.getDays();
        if (days > 0) {
            return weeks + " days ago";
        }
        int hours = period.getHours();
        if (hours > 0) {
            return hours + " hours ago";
        }
        int minutes = period.getMinutes();
        if (minutes > 0) {
            return minutes + " minutes ago";
        }
        return "Just now";
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView company;
        TextView content;
        TextView time;
        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.text_name);
            company = (TextView) view.findViewById(R.id.text_company);
            content = (TextView) view.findViewById(R.id.text_content);
            time = (TextView) view.findViewById(R.id.text_time);
        }
    }
}
