/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.konekthing.intravest.R;
import com.konekthing.intravest.model.Company;
import com.konekthing.intravest.model.Gallery;
import com.konekthing.intravest.model.Opportunity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renjaya on 4/7/15.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private Context mContext;
    private List<Gallery> mList;

    public GalleryAdapter(Context context, List<Gallery> list) {
        mContext = context;
        if (list == null) {
            mList = new ArrayList<>();
        } else {
            mList = list;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gallery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Glide.with(mContext)
                .load(mList.get(position).getImage())
                .placeholder(R.drawable.intravest)
                .crossFade()
                .into(holder.image);
        holder.text.setText(mList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public List<Gallery> getList() {
        return mList;
    }

    public void setList(List<Gallery> list) {
        mList = list;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text;
        public ViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            text = (TextView) view.findViewById(R.id.text);
        }
    }
}
