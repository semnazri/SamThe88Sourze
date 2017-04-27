/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.konekthing.intravest.R;
import com.konekthing.intravest.ui.adapters.item.NavDrawerItem;

import java.util.List;

/**
 * Created by renjaya on 4/7/15.
 */
public class NavDrawerAdapter extends BaseAdapter {

    private Context context;
    private List<NavDrawerItem> items;
    private ViewHolder viewHolder;

    public NavDrawerAdapter(Context context, List<NavDrawerItem> items) {
        this.context = context;
        this.items = items;
    }

    public void setSelected(int position) {
        if (viewHolder != null) {
            for (int i = 0; i < getCount(); i++) {
                if (i == position) {
                    viewHolder.relativeLayout.setBackgroundColor(Color.WHITE);
                } else {
                    viewHolder.relativeLayout.setBackgroundColor(Color.BLUE);
                }
            }
        }
    }

    @Override
    public int getCount() {
        if (items == null) return 0;
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder h;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_drawer_primary, parent,
                    false);
            h = new ViewHolder();
            h.relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout);
            h.icon = (ImageView) view.findViewById(R.id.drawer_item_icon);
            h.text = (TextView) view.findViewById(R.id.drawer_item_text);
            view.setTag(h);
        }
        viewHolder = (ViewHolder) view.getTag();
        NavDrawerItem item = items.get(position);
        viewHolder.icon.setImageResource(item.icon);
        viewHolder.text.setText(item.title);

        return view;
    }

    public NavDrawerItem getDrawerItem(int position) {
        return items.get(position);
    }

    static class ViewHolder {
        RelativeLayout relativeLayout;
        ImageView icon;
        TextView text;
    }
}
