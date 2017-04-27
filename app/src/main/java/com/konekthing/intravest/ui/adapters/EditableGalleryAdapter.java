/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.konekthing.intravest.R;
import com.konekthing.intravest.model.Gallery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renjaya on 4/7/15.
 */
public class EditableGalleryAdapter extends RecyclerView.Adapter<EditableGalleryAdapter.ViewHolder> {

    private Context mContext;
    private List<Gallery> mList;
    private View.OnClickListener mBrowseClickListener;
    private View.OnClickListener mUploadClickListener;
    private View.OnClickListener mDeleteClickListener;

    public EditableGalleryAdapter(Context context, List<Gallery> list, View.OnClickListener browseClickListener,
                                  View.OnClickListener uploadClickListener, View.OnClickListener deleteClickListener) {
        mContext = context;
        mBrowseClickListener = browseClickListener;
        mUploadClickListener = uploadClickListener;
        mDeleteClickListener = deleteClickListener;
        if (list == null) {
            mList = new ArrayList<>();
        } else {
            mList = list;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_editable_gallery, parent, false);
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
        holder.imageBrowse.setOnClickListener(mBrowseClickListener);
        holder.imageBrowse.setTag(position);
        holder.imageUpload.setOnClickListener(mUploadClickListener);
        holder.imageDelete.setOnClickListener(mDeleteClickListener);
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
        ImageView imageBrowse;
        ImageView imageUpload;
        ImageView imageDelete;
        public ViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            imageBrowse = (ImageView) view.findViewById(R.id.image_browse);
            imageUpload = (ImageView) view.findViewById(R.id.image_upload);
            imageDelete = (ImageView) view.findViewById(R.id.image_delete);
            text = (TextView) view.findViewById(R.id.text);
        }
    }
}
