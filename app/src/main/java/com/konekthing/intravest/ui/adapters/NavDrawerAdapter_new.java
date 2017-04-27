package com.konekthing.intravest.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.konekthing.intravest.R;
import com.konekthing.intravest.model.NavDrawerItem;

import java.util.Collections;
import java.util.List;


/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 1/2/17.
 *
 * @copyright 2017
 *
 */
public class NavDrawerAdapter_new extends RecyclerView.Adapter<NavDrawerAdapter_new.MyViewHolder> {
    public static List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public NavDrawerAdapter_new(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drower_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);

        if (data.get(position).isShowNotify()) {
            holder.layoutnya.setBackgroundColor(Color.rgb(161, 56, 152));
            holder.title.setTextColor(Color.WHITE);
        }

        holder.title.setText(current.getTitle());
//        holder.title.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Light.ttf"));
//        holder.home_title.setVisibility(View.GONE);
//        holder.home_title.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Light.ttf"));
            /*holder.icon.setImageDrawable(context.getResources().getDrawable(icon.getResourceId(position,-1);*/
        holder.icon.setImageDrawable(context.getResources().getDrawable(data.get(position).getIcon()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView home_title;
        ImageView icon;
        ImageView icon2;
        RelativeLayout layoutnya;


        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
//            home_title = (TextView) itemView.findViewById(R.id.header);
            icon = (ImageView) itemView.findViewById(R.id.icon_menu);
            icon2 = (ImageView) itemView.findViewById(R.id.icon_menu);
            layoutnya = (RelativeLayout) itemView.findViewById(R.id.nav_title);
        }
    }
}
