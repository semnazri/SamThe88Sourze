package com.konekthing.intravest.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.konekthing.intravest.R;
import com.konekthing.intravest.model.Industries;
import com.konekthing.intravest.ui.adapters.RegisterIndustriesAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 2/20/17.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class RegisterActivity_2 extends AppCompatActivity {

    private RecyclerView rv;
    private RegisterIndustriesAdapter adapter;
    private GridLayoutManager glm;
    private List<Industries> list_industries;

    Toolbar mToolbar;
    ImageView btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register_2);

        rv = (RecyclerView) findViewById(R.id.rv_industries);
        list_industries = getAllIndustries();
        glm = new GridLayoutManager(RegisterActivity_2.this, 3);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(glm);
        adapter = new RegisterIndustriesAdapter(RegisterActivity_2.this, list_industries);
        rv.setAdapter(adapter);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        TextView tv = (TextView) findViewById(R.id.TextToolbar);
        btn = (ImageView) findViewById(R.id.back_btn);
        tv.setText("Industries");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(mToolbar);

    }

    private List<Industries> getAllIndustries() {

        List<Industries> allIndustries = new ArrayList<Industries>();
        allIndustries.add(new Industries("Agribusiness", R.drawable.icon_2));
        allIndustries.add(new Industries("Art & Recreation", R.drawable.icon_3));
        allIndustries.add(new Industries("Building & Construction", R.drawable.icon_4));
        allIndustries.add(new Industries("Business & Other Services", R.drawable.icon_5));
        allIndustries.add(new Industries("Consumer Goods, Non-Food", R.drawable.icon_6));
        allIndustries.add(new Industries("Defence, Security &amp; Safety", R.drawable.icon_7));
        allIndustries.add(new Industries("Education & Training", R.drawable.icon_8));
        allIndustries.add(new Industries("Environment & Energy", R.drawable.icon_9));
        allIndustries.add(new Industries("Finance & Insurance", R.drawable.icon_10));
        allIndustries.add(new Industries("Food & Beverage", R.drawable.icon_11));
        allIndustries.add(new Industries("Health, Bioteachnology & Wellbeing", R.drawable.icon_12));
        allIndustries.add(new Industries("ICT", R.drawable.icon_13));
        allIndustries.add(new Industries("Manufacturing", R.drawable.icon_14));
        allIndustries.add(new Industries("Maining", R.drawable.icon_15));
        allIndustries.add(new Industries("Tourism & Hospitality", R.drawable.icon_16));
        return allIndustries;
    }


}
