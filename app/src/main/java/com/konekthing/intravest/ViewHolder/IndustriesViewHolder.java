package com.konekthing.intravest.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.konekthing.intravest.R;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 2/20/17.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class IndustriesViewHolder extends RecyclerView.ViewHolder{
    public TextView industries_name;
    public ImageView img_industries;
    public LinearLayout ll;

    public IndustriesViewHolder(View item){
        super(item);
        industries_name = (TextView) item.findViewById(R.id.txt_industry_name);
        img_industries = (ImageView) item.findViewById(R.id.img_industries);
    }
}
