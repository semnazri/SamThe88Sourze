package com.konekthing.intravest.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.konekthing.intravest.R;
import com.konekthing.intravest.model.SubIndustries;

import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 2/21/17.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class SpinnerAdapter extends BaseAdapter implements android.widget.SpinnerAdapter {
    private Context mContext;
    private List<SubIndustries> mValues;

    public void setmValues(List<SubIndustries> mValues) {
        this.mValues = mValues;
    }

    public SpinnerAdapter(Context context, List<SubIndustries>items){
        mContext = context;
        mValues = items;

    }

    @Override
    public int getCount() {
        return mValues.size();
    }

    @Override
    public Object getItem(int position) {
        return mValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);

            TextView id = (TextView) convertView.findViewById(R.id.id_data);
            TextView item_sub = (TextView) convertView.findViewById(R.id.item_sub_industries);

            id.setText(mValues.get(position).getId());
            item_sub.setText(mValues.get(position).getSubIndustries());
        }
        return convertView;
    }
}
